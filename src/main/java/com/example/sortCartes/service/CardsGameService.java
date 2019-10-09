package com.example.sortCartes.service;

import com.example.sortCartes.dao.BaseDAO;
import com.example.sortCartes.dto.*;
import com.example.sortCartes.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.sortCartes.utils.JSONUtils.convertCompositePOJOToJsonString;
import static com.example.sortCartes.utils.JSONUtils.writeJsonFileFromPojo;


/**
 * getCardsGame () : récuperation de la liste des cartes melangées + ecriture du nouveau  dans le fichier d'infos json (cardsGame.json)
 * retrouver cette liste de cartes triées dans   "data" : { "cards" : [ { .......}]}
 * <p>
 * sortCardsGame () : tri de la liste des cartes + ecriture du nouveau contenu dans le fichier d'infos json (sortedCardsGame.json)
 * retrouver cette liste de cartes triées dans   "data" : { "cards" : [ { .......}]}
 * <p>
 * testGame(..) : connect to url POST (https://localhost:8443/testGame), to call , https://recrutement.local-trust.com/test/{exerciceId}
 * this service will return  httpStatusCode= 200 of your  response sended in requestBody is correct
 * else this will return  httpStatusCode= 406  + exact solution to find about cards sort .
 **/
@Service
public class CardsGameService {

    private final Logger log = LoggerFactory.getLogger(CardsGameService.class);

    @Value("${file.cards.game.path}")
    private String fileCardsGamePath;

    @Value("${file.sorted.cards.game.path}")
    private String fileSortedCardsGamePath;
    @Autowired
    BaseDAO baseDAO;


    public CardsGame getCardsGame() {
        CardsGame cardsGame = new CardsGame();
        try {
            cardsGame = JSONUtils.convertJsonFileToPojo(fileCardsGamePath, CardsGame.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardsGame;
    }


    public CardsGame sortCardsGame(CardsGame cardsGame) {
        ArrayList<Card> SortedcardsGame = new ArrayList<>();
        //indication sur l'ordre des valeurs et des categories  des cartes
        log.info("order Cards by values: ");
        Arrays.stream(ValueOrderEnum.values())
                .forEach(val -> {
            log.info(" " + val.getCode() + "\t-" + val.getLibelle());
        });
        log.info("order Cards by Category: ");
        Arrays.stream(CategoryOrderEnum.values())
    .forEach(val -> {
            log.info("" + val.getCode() + "\t-" + val.getLibelle());
        });

        // Stream Elements with duplicate map keys – Collectors.groupingBy()
        //keep duplicated key is possible using the mergeFunction parameter of Collectors.toMap(keyMapper, valueMapper, mergeFunction):
  /*      Map<ValueOrderEnum, CategoryOrderEnum> unsortedCards = cardsGame.getData().getCards().stream().collect(
                Collectors.toMap( Card::getValue,
                        Card::getCategory,  // "value": "FOUR", "category": "HEART"
        (oldVal, newVal) -> {
            log.info("duplicate key found!,keep old key");
            return oldVal; //keep old key
        }
                ));*/

        //  Map<ValueOrderEnum, CategoryOrderEnum> unsortedCards =  cardsGame.getData().getCards().stream().collect(Collectors.toMap(Card :: getValue, Card :: getCategory
        //         , (oldValue, newValue) -> oldValue,LinkedHashMap::new));


        //  Map<ValueOrderEnum, CategoryOrderEnum> unsortedCards = cardsGame.getData().getCards().stream().collect(Collectors.toMap(Card :: getValue, Card :: getCategory));

        Map<ValueOrderEnum, CategoryOrderEnum> unsortedCards = cardsGame.getData().getCards().stream()
                .collect(Collectors.toMap(Card::getValue, Card::getCategory, (existingValue, newValue) -> existingValue));
        log.info("unsortedCards : " + unsortedCards);

        // Map.KEY=ValueOrderEnum: "FOUR",
        LinkedHashMap<ValueOrderEnum, CategoryOrderEnum> sortedCardsByValueOrder = unsortedCards.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
        log.info("sortedCardsByValueOrder : " + sortedCardsByValueOrder);

        // Map.VALUE=CategoryOrderEnum: "HEART"
        LinkedHashMap<ValueOrderEnum, CategoryOrderEnum> sortedCardsByCategoryOrder = sortedCardsByValueOrder.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        log.info("sortedCardsByCategoryOrder : " + sortedCardsByCategoryOrder);

        //Màj liste des cartes triées dans la reponse (cardsGame)
        sortedCardsByCategoryOrder.entrySet().stream().forEach(map -> {
            Card cardToAdd = new Card();
            cardToAdd.setValue(map.getKey());
            cardToAdd.setCategory(map.getValue());
            SortedcardsGame.add(cardToAdd);
        });
        cardsGame.getData().getCards().clear(); //clear previous content of cards

        cardsGame.getData().getCards().addAll(SortedcardsGame); //add  new sorted list of cards

        //aprés tri de la liste des cartes , on ecrit le nouveau contenu dans le fichier d'infos json (sortedCardsGame.json)
        // retrouver cette liste dans   "data" : { "cards" : [ { .......}]}
        writeJsonFileFromPojo(fileSortedCardsGamePath, cardsGame);


        return cardsGame;

    }


    // connect to url POST (https://localhost:8443/testGame), to call , https://recrutement.local-trust.com/test/{exerciceId}
    public Object testGame(Data data,String exerciceId) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //https://recrutement.local-trust.com/test/{exerciceId}
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://recrutement.local-trust.com").path("/test/" + exerciceId);
            String url = builder.toUriString();
            HttpEntity<String> entityRequestBody = new HttpEntity<String>(convertCompositePOJOToJsonString(data), headers);

            log.info("send Rest POST Request URL={}", builder.toUriString());

            response = baseDAO.postForObjectWithStringResponse(entityRequestBody, url);

        } catch (Exception e) {
            log.info("Encountered an error when trying to test game", e.getMessage());

        }
        //retourner la réponse concernant le tri de la liste des cartes
        return response.getBody();
    }


}
