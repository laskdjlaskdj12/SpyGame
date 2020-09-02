package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import org.bukkit.Material;

public class GameRoleContent {

    public void transitionElf(ICharacter elfAttacker, ICharacter elfVictim) {
        elfVictim.setGameRole(GAME_ROLE.LAKE_ELF);
        CharacterContent.removeItem(elfAttacker, Material.BOOK);

        CharacterContent.addItem(elfVictim, Material.BOOK);
        elfVictim.setGameRole(GAME_ROLE.LAKE_ELF);
    }
}
