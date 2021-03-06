package com.laskdjlaskdj12.spygame.status;

import javax.annotation.Nullable;

public enum GAME_ROLE {
    NONE("None", "원정대원"),
    EXCALIBUR_OWNER("Excalibur", "엑스칼리버 소유자"),
    EXPEDITION_LEAD("ExpeditionLead", "원정대장");

    private String name;
    private String nameKR;

    GAME_ROLE(String name, String nameKR) {
        this.name = name;
        this.nameKR = nameKR;
    }

    @Nullable
    public static GAME_ROLE findRoleByName(String arg) {
        for (GAME_ROLE v: values()){
            if(v.getName().equals(arg)){
                return v;
            }
        }

        return null;
    }

    public String getName(){
        return name;
    }

    public String getNameKR(){
        return nameKR;
    }
}
