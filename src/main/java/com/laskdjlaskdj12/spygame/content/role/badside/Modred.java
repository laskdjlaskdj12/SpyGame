package com.laskdjlaskdj12.spygame.content.role.badside;

import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;

public class Modred implements IRole {
    @Override
    public ROLE_TYPE roleType() {
        return ROLE_TYPE.MODRED;
    }

    @Override
    public String name() {
        return "Modred";
    }

    @Override
    public String KRName() {
        return "모드레드";
    }
}
