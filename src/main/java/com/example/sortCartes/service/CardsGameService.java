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

import java.io.File;
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

    @Value("${url.cards.game.path}")
    private String urlGetAllCards;
    @Value("${url.test.cards.game.path}")
    private String urltestCardsGame;

    @Autowired
    BaseDAO baseDAO;

    //https://localhost:8443/getAllCards => ( test sur navigateur , Postman , ou , swagger )
    //to call  :  https://recrutement.local-trust.com/test/cards/57187b7c975adeb8520a283c
    public CardsGame getAllCardsGameRealAccess() {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Accept", "application/json");
        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);
        //UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://recrutement.local-trust.com").path("/test/cards/57187b7c975adeb8520a283c");
        //String url = builder.toUriString();
        String url = urlGetAllCards;

        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        try {

            log.info("send Rest GET Request URL={}", url);

            response = baseDAO.getForObject(url, CardsGame.class);

            //aprés la récuperation de la liste des cartes , on ecrit le contenu dans le fichier d'infos json (cardsGame.json)
            // retrouver cette liste dans   "data" : { "cards" : [ { .......}]}
            //.........

        } catch (Exception e) {
            log.error(" an error occured when get a cards ", e.getMessage());
            e.printStackTrace();
        }
        return (CardsGame) response.getBody();

    }

    public CardsGame getCardsGameMockResponse() {
        CardsGame cardsGame = new CardsGame();
        try {
            cardsGame = JSONUtils.convertJsonFileToPojo(fileCardsGamePath, CardsGame.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardsGame;
    }


    public ArrayList<Integer> retrieveCardsValuesByCategory(CardsGame cardsGame, CategoryOrderEnum category) {
        ArrayList<Integer> tabValuesForCategory = new ArrayList();
        cardsGame.getData().getCards().stream()
                .filter(card -> card.getCategory().equals(category))
                .forEach(card -> tabValuesForCategory.add(card.getValue().getCode()));
        return tabValuesForCategory;
    }

    public void printOrderValuesAndCategories(CardsGame cardsGame) {
        log.info("order Cards by values: ");
        cardsGame.getData().getValueOrder().stream().forEach(val -> {
            log.info(" " + val.getCode() + "\t-" + val.getLibelle());
        });
        log.info("order Cards by Category: ");
        cardsGame.getData().getCategoryOrder().stream()
                .forEach(val -> {
                    log.info("" + val.getCode() + "\t-" + val.getLibelle());
                });

    }

    public void printlistCards(ArrayList<Card> cards) {
        if (cards != null && !cards.isEmpty()) {
            cards.stream().forEach(card -> System.out.println("{" + card.getValue() + "," + card.getCategory() + "}"));
        }
    }

    /*  Sorting the cards based on the suit and then numbers.
       This sorting is using Bucket Sort to sort the cards runtime= O(n) space=O(n);*/
    public CardsGame sortCards(CardsGame cardsGame) {
        //indication sur l'ordre des valeurs et des categories  des cartes
        printOrderValuesAndCategories(cardsGame);
        //affichage liste cartes (selon l'ordre d'arrivé
        log.info("unsortedCards : \n" );
        printlistCards(cardsGame.getData().getCards());

        int nbCategories = CategoryOrderEnum.values().length;

        //liste des categories order de taille (nbCartes=4)
        ArrayList<CategoryOrderEnum> categoriesOrder = new ArrayList();
        categoriesOrder = cardsGame.getData().getCategoryOrder();

        //liste de valeurs par category , chacune de taille <=nbCartes=10 + tri des valeurs
        // la somme des trailles des 4 listes  = 10 (nbCartes)
        ArrayList<Integer> valuesForDiamont = new ArrayList();
        valuesForDiamont = retrieveCardsValuesByCategory(cardsGame, CategoryOrderEnum.DIAMOND);
        Collections.sort(valuesForDiamont);
        ArrayList<Integer> valuesForHeart = new ArrayList();
        valuesForHeart = retrieveCardsValuesByCategory(cardsGame, CategoryOrderEnum.HEART);
        Collections.sort(valuesForHeart);
        ArrayList<Integer> valuesForSpade = new ArrayList();
        valuesForSpade = retrieveCardsValuesByCategory(cardsGame, CategoryOrderEnum.SPADE);
        Collections.sort(valuesForSpade);
        ArrayList<Integer> valuesForClub = new ArrayList();
        valuesForClub = retrieveCardsValuesByCategory(cardsGame, CategoryOrderEnum.CLUB);
        Collections.sort(valuesForClub);

        ArrayList<Card> sortedCards = new ArrayList<>();
        /***  |DIAMOND|HEART|SPADE|CLUB *****/
        for (int j = 0; j < nbCategories; j++) {
            Integer curentCategory = categoriesOrder.get(j).getCode();

            switch (curentCategory) {
                case 1: //DIAMOND
                    if (valuesForDiamont != null && !valuesForDiamont.isEmpty()) {
                        valuesForDiamont.stream().forEach(valForDiamont -> {
                            sortedCards.add(new Card((ValueOrderEnum) EnumMappable.getEnumFromCode(valForDiamont, ValueOrderEnum.class), CategoryOrderEnum.DIAMOND));
                        });
                    }
                    break;
                case 2: //HEART
                    if (valuesForHeart != null && !valuesForHeart.isEmpty()) {
                        valuesForHeart.forEach(valForHeart -> {
                            sortedCards.add(new Card((ValueOrderEnum) EnumMappable.getEnumFromCode(valForHeart, ValueOrderEnum.class), CategoryOrderEnum.HEART));
                        });
                    }
                    break;
                case 3: //SPADE
                    if (valuesForSpade != null && !valuesForSpade.isEmpty()) {
                        valuesForSpade.forEach(valForSpade -> {
                            sortedCards.add(new Card((ValueOrderEnum) EnumMappable.getEnumFromCode(valForSpade, ValueOrderEnum.class), CategoryOrderEnum.SPADE));
                        });
                    }
                    break;
                case 4: //CLUB
                    if (valuesForClub != null && !valuesForClub.isEmpty()) {
                        valuesForClub.forEach(valForClub -> {
                            sortedCards.add(new Card((ValueOrderEnum) EnumMappable.getEnumFromCode(valForClub, ValueOrderEnum.class), CategoryOrderEnum.CLUB));
                        });
                    }
                    break;
                default:
                    break;
            }// switch
        }//for
        //affichage cartes triées
        log.info("sortedCards : \n" );
        printlistCards(sortedCards);
        //Màj liste des cartes triées dans la reponse (cardsGame), et le fichier local qui le définit(sortedCardsGame.json)
        updateResponseBySortedCards(cardsGame, sortedCards);

        return cardsGame;
    }//sort


    public CardsGame sortCardsGame(CardsGame cardsGame) {
        ArrayList<Card> sortedcardsGame = new ArrayList<>();
        //indication sur l'ordre des valeurs et des categories  des cartes
        printOrderValuesAndCategories(cardsGame);
        //affichage liste cartes (selon l'ordre d'arrivé
        printlistCards(cardsGame.getData().getCards());

        //list To map
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
            sortedcardsGame.add(cardToAdd);
        });
        //affichage cartes triées
        printlistCards(sortedcardsGame);
        //Màj liste des cartes triées dans la reponse (cardsGame), et le fichier local qui le définit(sortedCardsGame.json)
        updateResponseBySortedCards(cardsGame, sortedcardsGame);

        return cardsGame;

    }


    public void updateResponseBySortedCards(CardsGame cardsGame, ArrayList<Card> Sortedcards) {

        cardsGame.getData().getCards().clear(); //clear previous content of cards

        cardsGame.getData().getCards().addAll(Sortedcards); //add  new sorted list of cards

        //aprés tri de la liste des cartes , on ecrit le nouveau contenu dans le fichier d'infos json (sortedCardsGame.json)
        // retrouver cette liste dans   "data" : { "cards" : [ { .......}]}
        writeJsonFileFromPojo(fileSortedCardsGamePath, cardsGame);

    }

    // connect to url POST (https://localhost:8443/testGame/exerciceId), to call , https://recrutement.local-trust.com/test/{exerciceId}
    public Object testGame(Data data, String exerciceId) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //https://recrutement.local-trust.com/test/{exerciceId}
            String url =urltestCardsGame+exerciceId;

            HttpEntity<String> entityRequestBody = new HttpEntity<String>(convertCompositePOJOToJsonString(data), headers);

            log.info("send Rest POST Request URL={}", url);

            response = baseDAO.postForObjectWithStringResponse(entityRequestBody, url);

        } catch (Exception e) {
            log.info("Encountered an error when trying to test game", e.getMessage());

        }
        //retourner la réponse concernant le tri de la liste des cartes
        return response.getBody();
    }


}
