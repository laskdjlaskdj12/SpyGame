package com.laskdjlaskdj12.spygame.factory;

import com.laskdjlaskdj12.spygame.content.role.Cervent;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.content.role.badside.Assassine;
import com.laskdjlaskdj12.spygame.content.role.badside.Modred;
import com.laskdjlaskdj12.spygame.content.role.badside.Morgana;
import com.laskdjlaskdj12.spygame.content.role.goodside.Marline;
import com.laskdjlaskdj12.spygame.content.role.goodside.Percival;
import com.laskdjlaskdj12.spygame.exception.NoRoleFoundException;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;

import java.util.Arrays;
import java.util.List;

public class RoleFactory {

    public static List<IRole> createMustPickRole(){
        return Arrays.asList(new Marline(), new Assassine());
    }

    public static List<IRole> createMinerPickRole(){
        return Arrays.asList(new Modred(), new Morgana(), new Percival(), new Cervent());
    }

    public static IRole createRole(ROLE_TYPE roleType) {
        switch(roleType){
            case MARLINE:
                return new Marline();
            case ASSASSINE:
                return new Assassine();
            case MODRED:
                return new Modred();
            case MORGANA:
                return new Morgana();
            case PERCIVAL:
                return new Percival();
            case CERVENT:
                return new Cervent();
            default:
                throw new NoRoleFoundException("Wrong RoleType that can't make instance");
        }
    }
}
