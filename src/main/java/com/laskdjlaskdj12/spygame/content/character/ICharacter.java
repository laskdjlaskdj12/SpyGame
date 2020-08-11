package com.laskdjlaskdj12.spygame.content.character;

import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.status.LIFE_STATUS;
import org.bukkit.entity.Player;

/*
    캐릭터는 아래와 같은 속성을 갖고 있음
    1. 역활, 캐릭터의 역활을
 */
public interface ICharacter {
    void changeState(LIFE_STATUS born);

    void setRole(IRole role);

    void setPlayer(Player player);

    IRole getRole();

    Player getPlayer();
}