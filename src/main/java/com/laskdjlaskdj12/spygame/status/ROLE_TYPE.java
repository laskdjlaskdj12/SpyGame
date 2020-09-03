package com.laskdjlaskdj12.spygame.status;

import javax.annotation.Nullable;

public enum ROLE_TYPE {
    MARLINE("Marline", "멀린", false, "선의세력"),
    ASSASSINE("Assassine", "어쌔신", true, "악의세력"),
    MODRED("Modred", "모드레드", true, "악의세력"),
    MORGANA("Morgana", "모르가나", true, "악의세력"),
    OVERONE("Overone", "오베론", true, "악의세력"),
    PERCIVAL("Percival", "퍼시벌", false, "선의세력"),
    CERVENT("Cervent", "신하", false, "선의세력");

    public String name;
    public String nameKR;
    public boolean isEvil;
    public String factionName;

    ROLE_TYPE(String name,
              String nameKR,
              boolean isEvil,
              String factionName){
        this.name = name;
        this.nameKR = nameKR;
        this.isEvil = isEvil;
        this.factionName = factionName;
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