package com.laskdjlaskdj12.spygame.event;

import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitEventHandler implements Listener {

    private final GameModeContent gameModeContent;
    public PlayerHitEventHandler(GameModeContent gameModeContent){
        this.gameModeContent = gameModeContent;
    }

    @EventHandler
    public void playerHitEventHandler(EntityDamageByEntityEvent event){

        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)){
            return;
        }

        Player attacker = (Player)event.getDamager();
        Player victim = (Player)event.getEntity();


        if(attacker.getInventory().getItemInMainHand().getType() == Material.BOOK){
            activeLakeElf(attacker, victim);
        }
        else if(attacker.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD){
            activeExcalibur(attacker, victim);
        }
    }

    private void activeExcalibur(Player attacker, Player victim) {
        //공격당한 사람들의 투표결과를 변경
        ICharacter victimCharacter = gameModeContent.findCharacterFromPlayer(victim);

        if(victimCharacter == null){
            Bukkit.broadcastMessage(ChatColor.RED + "[에러] 엑스칼리버를 가질수없는 사람이 가지고 있습니다.");
            return;
        }

        ExperditionContent experditionContent = gameModeContent.experditionContent();

        experditionContent.changeVote(victimCharacter);

        //공격자의 엑스칼리버를 제거
        attacker.getInventory().getItemInMainHand().setType(Material.AIR);

        Bukkit.broadcastMessage(attacker.getDisplayName() + "님이 " + victim.getDisplayName() + "님에게 엑스칼리버를 사용했습니다.");
    }

    private void activeLakeElf(Player attacker, Player victim){
        if(attacker.getInventory().getItemInMainHand().getType() != Material.BOOK){
            return;
        }

        //3라운드 이후에 능력이 발동되도록 세팅
        if (gameModeContent.experditionCount() < 3){
            attacker.sendMessage("호수의 여신 능력은 3라운드 이후에 사용이 가능합니다.");
            return;
        }

        ICharacter iCharacter = gameModeContent.findCharacterFromPlayer(victim);
        if(iCharacter == null){
            attacker.sendMessage("호수의 여신 능력을 사용할수가 없습니다. [이유] : 게임에 참여하지 않는 사람입니다.");
            return;
        }

        GAME_ROLE gameRole = iCharacter.getGameRole();
        attacker.sendMessage(iCharacter.getPlayer().getDisplayName() + "님은 " + gameRole.getNameKR() + "입니다.");
    }
}
