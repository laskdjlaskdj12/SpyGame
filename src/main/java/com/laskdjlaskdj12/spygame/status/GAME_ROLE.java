package com.laskdjlaskdj12.spygame.status;

public enum GAME_ROLE {
    NONE("Cervent", "신하"),
    EXCALIBUR_OWNER("EXCALIBUR_OWNER", "엑스칼리버"),
    LAKE_ELF("LakeElf", "호수의 요정"),
    EXPEDITION_LEAD("ExpeditionLead", "원정대장");

    private String name;
    private String nameKR;

    GAME_ROLE(String name, String nameKR) {
        this.name = name;
        this.nameKR = nameKR;
    }

    public String getName(){
        return name;
    }

    public String getNameKR(){
        return nameKR;
    }
}
