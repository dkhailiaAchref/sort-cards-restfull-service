package com.example.sortCartes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    public Card(ValueOrderEnum value, CategoryOrderEnum category) {
        this.value = value;
        this.category = category;
    }

    public Card() {
    }

    ValueOrderEnum value;
    CategoryOrderEnum category;

    //getters
    public ValueOrderEnum getValue() {
        return value;
    }

    public CategoryOrderEnum getCategory() {
        return category;
    }

    //setters
    public void setValue(ValueOrderEnum value) {
        this.value = value;
    }

    public void setCategory(CategoryOrderEnum category) {
        this.category = category;
    }
}
