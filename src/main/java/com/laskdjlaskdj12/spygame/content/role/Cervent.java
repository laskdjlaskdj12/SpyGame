package com.laskdjlaskdj12.spygame.content.role;

import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;

public class Cervent implements IRole {

    @Override
    public ROLE_TYPE roleType() {
        return ROLE_TYPE.CERVENT;
    }

    @Override
    public String name() {
        return "Cervent";
    }

    @Override
    public String KRName() {
        return "신하";
    }
}
