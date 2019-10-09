package com.example.sortCartes.controller;

import com.example.sortCartes.BaseTests;
import com.example.sortCartes.TestHelpers;
import com.example.sortCartes.dto.CardsGame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.example.sortCartes.TestHelpers.assertionCardsGame;
import static org.mockito.Mockito.when;

public class CardsGameControllerTest extends BaseTests {

    private static CardsGame cardsGameFromFile = new CardsGame();
    private static CardsGame sortedCardsGameFromFile = new CardsGame();

    @Mock
    public com.example.sortCartes.controller.CardsGameController mockCardsGameController;

    @Before
    public void setup() {
        super.setup();   // heriter les injections réelles définits   dans BaseTest

        cardsGameFromFile=TestHelpers.initAllCardsFromFile();
        sortedCardsGameFromFile=TestHelpers.getAllSortedCardsFromFile();
    }


    // ne pas acceder à l'implémentation réelle(@Mock mockCardsGameController), pour ne pas  accéder à un reel WS
    @Test
    public void shouldGetAllCards() {

        when(this.mockCardsGameController.getAllCards()).thenReturn(cardsGameFromFile);

        Object response = mockCardsGameController.getAllCards();
        CardsGame returnedCardsGame = (CardsGame) response;
        assertionCardsGame(returnedCardsGame,cardsGameFromFile);
    }


    // ne pas acceder à l'implémentation réelle (@Mock mockCardsGameController), pour ne pas  accéder à un reel WS
    @Test
    public void shouldSortAllCards() throws Exception {
        when(this.mockCardsGameController.sortCardsGame()).thenReturn(sortedCardsGameFromFile);

        Object response = mockCardsGameController.sortCardsGame();
        CardsGame returnedCardsGame = (CardsGame) response;
        assertionCardsGame(returnedCardsGame,sortedCardsGameFromFile);

    }
    // acceder à l'implémentation réelle ,(cardsGameController et cardsGameService) herités de la clase BaseTests ( qui gére l'injection des dependances dans les services)
    // de telle sorte le controlleur utilise un fichier local ( src/tes/resources/cardsGame.json)
    // envoyée au service mockCardsGameService.sortCardsGame(CardsGame ...), pour en sortir un fichier avec les cartes triées (src/tes/resources/sortedCardsGame.json)
    @Test
    public void shouldSortAllCardsRealAccess() throws Exception {

        Object response = cardsGameController.sortCardsGame();
        CardsGame returnedCardsGame = (CardsGame) response;
        assertionCardsGame(returnedCardsGame,sortedCardsGameFromFile);

    }

}
