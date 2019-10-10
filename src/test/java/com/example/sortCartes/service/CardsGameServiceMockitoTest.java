package com.example.sortCartes.service;

import com.example.sortCartes.BaseTests;
import com.example.sortCartes.TestHelpers;
import com.example.sortCartes.dto.CardsGame;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.example.sortCartes.TestHelpers.assertionCardsGame;
import static org.mockito.Mockito.when;


public class CardsGameServiceMockitoTest extends BaseTests {

    private static CardsGame cardsGameFromFile = new CardsGame();
    private static CardsGame sortedCardsGameFromFile = new CardsGame();

    @Mock
    CardsGameService mockCardsGameService;


    @Before
    public void setup() {
        super.setup();   // heriter les injections réelles définits   dans BaseTest
        cardsGameFromFile = TestHelpers.initAllCardsFromFile();
        sortedCardsGameFromFile = TestHelpers.getAllSortedCardsFromFile();
    }

    // ne pas acceder à l'implémentation réelle(@Mock mockCardsGameService),, pour ne pas  accéder à un reel WS
    @Test
    public void shouldGetAllCards() throws Exception {
        when(this.mockCardsGameService.getAllCardsGameRealAccess()).thenReturn(cardsGameFromFile);

        Object response = mockCardsGameService.getAllCardsGameRealAccess();
        CardsGame returnedCardsGame = (CardsGame) response;
        assertionCardsGame(returnedCardsGame, cardsGameFromFile);
    }

    // ne pas acceder à l'implémentation réelle (@Mock mockCardsGameService)
    @Test
    public void shouldSortAllCards() throws Exception {
        when(this.mockCardsGameService.sortCards(cardsGameFromFile)).thenReturn(sortedCardsGameFromFile);

        Object response = mockCardsGameService.sortCardsGame(cardsGameFromFile);
        CardsGame returnedCardsGame = (CardsGame) response;
        assertionCardsGame(returnedCardsGame, sortedCardsGameFromFile);

    }

    // acceder à l'implémentation réelle ,(cardsGameService) herité de la clase BaseTests ( qui gére l'injection des dependances dans les services)
    // de telle sorte le service utilise un fichier local ( src/tes/resources/cardsGame.json) pour en sortir un fichier avec les cartes triées (src/tes/resources/sortedCardsGame.json)
    @Test
    public void shouldSortAllCardsRealAccess() throws Exception {

        Object response = cardsGameService.sortCards(cardsGameFromFile);
        CardsGame returnedCardsGame = (CardsGame) response;
        assertionCardsGame(returnedCardsGame, sortedCardsGameFromFile);

    }

}
