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

    public void transitionToExperditionLead(ICharacter leader, ICharacter victim, boolean isExcaliverExsist) {
        leader.setGameRole(GAME_ROLE.NONE);
        CharacterContent.removeItem(leader, Material.DIAMOND_AXE);
        CharacterContent.removeItem(leader, Material.DIAMOND_PICKAXE);

        victim.setGameRole(GAME_ROLE.EXPEDITION_LEAD);
        if (isExcaliverExsist) {
            CharacterContent.addItem(victim, Material.DIAMOND_AXE);
        } else {
            CharacterContent.addItem(victim, Material.AIR);
        }
        CharacterContent.addItem(victim, Material.DIAMOND_PICKAXE);
    }

    public void transitionToExcaliburOwner(ICharacter owner, ICharacter victim) {
        removeExcaliburOwner(owner);
        setExcaliburOwner(victim);
    }

    public void setExcaliburOwner(ICharacter character){
        character.setGameRole(GAME_ROLE.EXCALIBUR_OWNER);
        CharacterContent.addItem(character, Material.DIAMOND_SWORD);
    }

    public void removeExcaliburOwner(ICharacter character){
        character.setGameRole(GAME_ROLE.NONE);
        CharacterContent.removeItem(character, Material.DIAMOND_SWORD);
    }

    public void awardExperditionLead(ICharacter candidate) {
        candidate.setGameRole(GAME_ROLE.EXPEDITION_LEAD);
        CharacterContent.addItem(candidate, Material.DIAMOND_AXE);
        CharacterContent.addItem(candidate, Material.DIAMOND_PICKAXE);
    }

    public void awardExcaliburOwner(ICharacter owner) {
        owner.setGameRole(GAME_ROLE.EXCALIBUR_OWNER);
        CharacterContent.addItem(owner, Material.DIAMOND_SWORD);
    }

    public void awardExperditionLeadWithoutExcalivur(ICharacter candidate) {
        candidate.setGameRole(GAME_ROLE.EXPEDITION_LEAD);
        CharacterContent.addItem(candidate, Material.DIAMOND_PICKAXE);
    }
}
