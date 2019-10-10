package com.example.sortCartes.init;


import org.springframework.stereotype.Component;
import   com.example.sortCartes.dto.CardsGame;
import   com.example.sortCartes.utils.JSONUtils;

import java.io.File;


@Component
public class DataInit  {


    static String TARGET_FILE_CARDS_GAME= "src/main/resources" + File.separator + "cardsGame.json";


    public static CardsGame getCardsGameFromLocalFile() {
        CardsGame cardsGame = new CardsGame();
        try {
            cardsGame = JSONUtils.convertJsonFileToPojo(TARGET_FILE_CARDS_GAME, CardsGame.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardsGame;
    }
}
