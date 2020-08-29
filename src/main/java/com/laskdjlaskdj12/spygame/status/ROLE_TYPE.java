package com.laskdjlaskdj12.spygame.status;

import javax.annotation.Nullable;

public enum ROLE_TYPE {
    MARLINE("Marline", "멀린"),
    ASSASSINE("Assassine", "어쌔신"),
    MODRED("Modred", "모드레드"),
    MORGANA("Morgana", "모르가나"),
    OVERONE("Overone", "오베론"),
    PERCIVAL("Percival", "퍼시벌"),
    CERVENT("Cervent", "신하");

    public String name;
    public String nameKR;

    ROLE_TYPE(String name, String nameKR){
        this.name = name;
        this.nameKR = nameKR;
    }

    @Nullable
    public static ROLE_TYPE findRoleByName(String name){
        for(ROLE_TYPE v: values()){
            if (v.name.equals(name)){
                return v;
            }
        }

        return null;
    }
}