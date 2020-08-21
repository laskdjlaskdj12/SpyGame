package com.laskdjlaskdj12.spygame.status;

public enum ROLE_TYPE {
    MARLINE("Marline", "멀린"),
    ASSASSINE("Assassine", "어쌔"),
    MODRED("Modred", "모드레드"),
    MORGANA("Morgana", "모르가나"),
    OVERONE("Overone", "오베론"),
    PERCIVAL("Percival", "퍼시벌"),
    CERVENT("Cervent", "신하");

    private String name;
    private String nameKR;

    ROLE_TYPE(String name, String nameKR){
        this.name = name;
        this.nameKR = nameKR;
    }
}