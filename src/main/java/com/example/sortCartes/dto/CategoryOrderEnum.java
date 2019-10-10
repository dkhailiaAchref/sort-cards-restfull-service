package com.example.sortCartes.dto;

public enum CategoryOrderEnum implements EnumMappable  {
    DIAMOND(1,"DIAMOND"),
    HEART(2,"HEART"),
    SPADE(3,"SPADE"),
    CLUB(4,"CLUB");

    private Integer code;
    private String libelle;
    //constructor
    private CategoryOrderEnum(Integer code, String libelle) {
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
