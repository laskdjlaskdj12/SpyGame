package com.laskdjlaskdj12.spygame.content.role.goodside;

import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;

public class Percival implements IRole {

    @Override
    public ROLE_TYPE roleType() {
        return ROLE_TYPE.PERCIVAL;
    }

    @Override
    public String name() {
        return "Percival";
    }
    @Override
    public String KRName() {
        return "퍼시벌";
    }
}
