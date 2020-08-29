package com.laskdjlaskdj12.spygame.content.role.goodside;

import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;

public class Marline implements IRole {

    @Override
    public ROLE_TYPE roleType() {
        return ROLE_TYPE.MARLINE;
    }

    @Override
    public String name() {
        return "Marline";
    }

    @Override
    public String KRName() {
        return "멀린";
    }
}
