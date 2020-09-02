package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import org.bukkit.Material;

public class GameRoleContent {

    public void transitionToElf(ICharacter elfAttacker, ICharacter elfVictim) {
        elfVictim.setGameRole(GAME_ROLE.NONE);
        CharacterContent.removeItem(elfAttacker, Material.BOOK);

        CharacterContent.addItem(elfVictim, Material.BOOK);
        elfVictim.setGameRole(GAME_ROLE.LAKE_ELF);
    }

    public void transitionToExperditionLead(ICharacter leader, ICharacter victim) {
        leader.setGameRole(GAME_ROLE.NONE);
        CharacterContent.removeItem(leader, Material.DIAMOND_SWORD);
        CharacterContent.removeItem(leader, Material.DIAMOND_PICKAXE);

        victim.setGameRole(GAME_ROLE.EXPEDITION_LEAD);
        CharacterContent.addItem(victim, Material.DIAMOND_SWORD);
        CharacterContent.addItem(victim, Material.DIAMOND_PICKAXE);
    }

    public void awardExperditionLead(ICharacter candidate) {
        candidate.setGameRole(GAME_ROLE.EXPEDITION_LEAD);
        CharacterContent.addItem(candidate, Material.DIAMOND_SWORD);
        CharacterContent.addItem(candidate, Material.DIAMOND_PICKAXE);
    }
}
