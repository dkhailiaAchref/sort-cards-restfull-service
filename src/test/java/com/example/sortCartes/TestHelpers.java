package com.example.sortCartes;

import com.example.sortCartes.dto.Card;
import com.example.sortCartes.dto.CardsGame;
import com.example.sortCartes.utils.JSONUtils;
import org.junit.Assert;

import java.io.File;
import java.util.Optional;

public class TestHelpers {

    static String TARGET_FILE_CARDS_GAME = "src/test/resources" + File.separator + "cardsGame.json";
    static String TARGET_FILE_SORTED_CARDS_GAME = "src/test/resources" + File.separator + "sortedCardsGame.json";

    public static CardsGame initAllCardsFromFile() {
        CardsGame cardsGame = new CardsGame();
        try {
            cardsGame = JSONUtils.convertJsonFileToPojo(TARGET_FILE_CARDS_GAME, CardsGame.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardsGame;
    }

    public static CardsGame getAllSortedCardsFromFile() {
        CardsGame cardsGame = new CardsGame();
        try {
            cardsGame = JSONUtils.convertJsonFileToPojo(TARGET_FILE_SORTED_CARDS_GAME, CardsGame.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardsGame;
    }

    public static Card getFirstCardFromCardsGame(CardsGame cardsGame) {
        Card cardFound = new Card();
        if (cardsGame != null) {
            Optional<Card> cardFoundOpt = cardsGame.getData().getCards().stream().findFirst();
            if (cardFoundOpt.isPresent()) {
                cardFound = cardFoundOpt.get();
            }
        }
        return cardFound;
    }

    public static void assertionCardsGame(CardsGame cardsGameResponse, CardsGame cardsGameExpected) {
        Card firstCardActual = TestHelpers.getFirstCardFromCardsGame(cardsGameResponse); //actual(returned)
        Card firstCardExpected = TestHelpers.getFirstCardFromCardsGame(cardsGameExpected); //expected (attendue)

        if (cardsGameResponse != null) {
            Assert.assertEquals(firstCardActual.getValue(), firstCardExpected.getValue());
            Assert.assertEquals(firstCardActual.getCategory(), firstCardExpected.getCategory());
        }
    }
}
