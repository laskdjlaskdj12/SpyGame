package com.laskdjlaskdj12.spygame.content.role.badside;

import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;

public class Morgana implements IRole {

    @Override
    public ROLE_TYPE roleType() {
        return ROLE_TYPE.MORGANA;
    }

    @Override
    public String name() {
        return "Morgana";
    }

    @Override
    public String KRName() {
        return "모르가나";
    }
}
