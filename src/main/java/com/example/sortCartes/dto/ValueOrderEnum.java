package com.example.sortCartes.dto;

public enum ValueOrderEnum {
    ACE(1,"ACE"),
    TWO(2,"TWO"),
    THREE(3,"THREE"),
    FOUR(4,"FOUR"),
    FIVE(5,"FIVE"),
    SIX(6,"SIX"),
    SEVEN(7,"SEVEN"),
    EIGHT(8,"EIGHT"),
    NINE(9,"NINE"),
    TEN(10,"TEN"),
    JACK(11,"JACK"),
    QUEEN(12,"QUEEN"),
    KING(13,"KING");

    private Integer code;
    private String libelle;
    //constructor
    private ValueOrderEnum(Integer code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
    public Integer getCode() {
        return code;
    }
    public String getLibelle() {
        return libelle;
    }
}
