package com.laskdjlaskdj12.spygame.content.role.badside;

import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;

public class Assassine implements IRole {
    @Override
    public ROLE_TYPE roleType() {
        return ROLE_TYPE.ASSASSINE;
    }

    @Override
    public String name() {
        return "Assassine";
    }

    @Override
    public String KRName() {
        return "어쌔신";
    }
}
