package com.laskdjlaskdj12.spygame.event;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
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

        Player hitter = (Player)event.getDamager();
        Player victim = (Player)event.getEntity();

        if(hitter.getInventory().getItemInMainHand().getType() != Material.BOOK){
            return;
        }

        //3라운드 이후에 능력이 발동되도록 세팅
        if (gameModeContent.experditionCount() < 3){
            hitter.sendMessage("호수의 여신 능력은 3라운드 이후에 사용이 가능합니다.");
            return;
        }

        ICharacter iCharacter = gameModeContent.findCharacterFromPlayer(victim);
        if(iCharacter == null){
            hitter.sendMessage("호수의 여신 능력을 사용할수가 없습니다. [이유] : 게임에 참여하지 않는 사람입니다.");
            return;
        }

        GAME_ROLE gameRole = iCharacter.getGameRole();
        hitter.sendMessage(iCharacter.getPlayer().getDisplayName() + "님은 " + gameRole.getNameKR() + "입니다.");
    }
}
