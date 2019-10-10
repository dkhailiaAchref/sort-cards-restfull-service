package com.example.sortCartes.controller;


import com.example.sortCartes.dto.CardsGame;
import com.example.sortCartes.service.CardsGameService;
import com.example.sortCartes.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sortCartes.init.DataInit;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.sortCartes.dto.Data;

@RestController
@RequestMapping(value = "/")
@Api(tags = {Constants.TAG_CARDS_GAME})


/**
 /**
 * tester les Apis Rest sur
 * https://localhost:8443/swagger-ui.html#
 * (aprés avoir  demarré le serveur embarqué de spring-web , par spring-boot:run )
 *
 *   getAllCards () : récuperation de la liste des cartes melangées + ecriture du nouveau  dans le fichier d'infos json (cardsGame.json)
 *   retrouver cette liste de cartes triées dans   "data" : { "cards" : [ { .......}]}
 *   appeller l'url GET pour visualiser le  contenu recuperé  (https://localhost:8443/getAllCards )
 *
 *  sortCardsGame () : tri de la liste des cartes + ecriture du nouveau contenu dans le fichier d'infos json (sortedCardsGame.json)
 *  retrouver cette liste de cartes triées dans   "data" : { "cards" : [ { .......}]}
 *  appeller l'url GET pour visualiser le nouveau contenu (https://localhost:8443/sortCardsGame )
 *
 * testGame(..) : connect to url POST (https://localhost:8443/testGame), to call , https://recrutement.local-trust.com/test/{exerciceId}
 * this service will return  httpStatusCode= 200 of your  response in requestBody is correct
 * else this will return  httpStatusCode= 406  + exact solution to find about cards sort .
 **/

public class CardsGameController {

    @Autowired
    CardsGameService cardsGameService;

    //https://localhost:8443/getAllCards => ( test sur navigateur , Postman , ou , swagger )
    @RequestMapping(value = "/getAllCards", method = RequestMethod.GET)
    @ApiOperation(value = "Obtenir les cartes,(à tester sur le navigateur ou swagger")
    public @ResponseBody
    CardsGame getAllCards() {
        return cardsGameService.getCardsGameMockResponse();
    }

    //https://localhost:8443/sortCardsGame => ( test sur navigateur , Postman , ou , swagger )
    @RequestMapping(value = "/sortCardsGame", method = RequestMethod.GET)
    @ApiOperation(value = "trier les cartes,(à tester sur le navigateur ou swagger")
    public @ResponseBody
    CardsGame sortCardsGame() {
        CardsGame cardsGameLocal = DataInit.getCardsGameFromLocalFile();
        return cardsGameService.sortCards(cardsGameLocal);
    }

    // connect to url POST (https://localhost:8443/testGame/exerciceId), to call , https://recrutement.local-trust.com/test/{exerciceId}
    //tester avec requette 'POST' sur (Postman ou swagger) , avec le body json, ecrit dans le fichier (sortedCardsGame.json)
    // retrouver cette liste dans   "data" : { "cards" : [ { .......}]}
    @RequestMapping(value = "/testGame/{exerciceId}", method = RequestMethod.POST)
    @ApiOperation(value = "tester le resultat de tri des cartes ")
    public @ResponseBody
    Object testGame(@RequestBody Data data,@PathVariable String exerciceId) {
        return cardsGameService.testGame(data,exerciceId);
    }

}
