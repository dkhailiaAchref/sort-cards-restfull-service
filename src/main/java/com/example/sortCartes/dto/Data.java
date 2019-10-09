package com.example.sortCartes.dto;

/*import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;



/*
@Setter
@Getter
@ToString
@NoArgsConstructor*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data implements Serializable {
    private static final long serialVersionUID = 7875622941513777868L;

    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<CategoryOrderEnum> categoryOrder = new ArrayList<>();
    ArrayList<ValueOrderEnum> valueOrder = new ArrayList<>();

    // Getter Methods
    public ArrayList<CategoryOrderEnum> getCategoryOrder() {
        return categoryOrder;
    }
    public ArrayList<ValueOrderEnum> getValueOrder() {
        return valueOrder;
    }
    public ArrayList<Card> getCards() {
        return cards;
    }
    // Setter Methods

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public void setCategoryOrder(ArrayList<CategoryOrderEnum> categoryOrder) {
        this.categoryOrder = categoryOrder;
    }
    public void setValueOrder(ArrayList<ValueOrderEnum> valueOrder) {
        this.valueOrder = valueOrder;
    }
}
