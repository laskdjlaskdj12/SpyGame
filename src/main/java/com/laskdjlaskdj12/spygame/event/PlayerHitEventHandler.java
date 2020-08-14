package com.laskdjlaskdj12.spygame.event;

import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerHitEventHandler implements Listener {

    private final ExperditionContent experditionContent;

    public PlayerHitEventHandler(ExperditionContent experditionContent) {
        this.experditionContent = experditionContent;
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {

        //Todo: 리팩토링할것
        //플레이어의 인터랙티브 이벤트를 더함
        if (event.getItem() == null || event.getItem().getType() != Material.GOLDEN_SWORD) {
            return;
        }

        if (event.getAction() != Action.LEFT_CLICK_BLOCK || !event.hasBlock()) {
            return;
        }

        if (event.getClickedBlock().getType() != Material.GOLD_BLOCK) {
            return;
        }

        //Todo: 백엔드 데이터를 만듬

        Player player = event.getPlayer();
        World world = player.getWorld();

        world.strikeLightning(player.getLocation());

        //0. 현재 진행중인 expedition들을 갖고옴
        if (!experditionContent.isExsist()) {
            player.sendMessage(ChatColor.RED + "원정이 시작되지 않아서 원정결과 투표가 불가능합니다.");
            return;
        }

        ICharacter iCharacter = experditionContent.findExpertiesFromPlayer(event.getPlayer());

        //2. 투표 정보 저장
        experditionContent.addVote(iCharacter, true);

        Server server = player.getServer();
        Block touchedBlock = event.getClickedBlock();

        //3. 블록을 다이아몬드 색으로 변경
        touchedBlock.setType(Material.DIAMOND_BLOCK);

        //4. 유저에게 찬성했다는 투표 알림 메세지 보냄
        String displayName = event.getPlayer().getDisplayName();
        String ai = ChatColor.GREEN + "찬성" + ChatColor.WHITE;
        server.broadcastMessage(ChatColor.YELLOW + displayName + ChatColor.WHITE + "님이 " + ai + "을 하셨습니다.");
    }
}
