package com.laskdjlaskdj12.spygame.event;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
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

public class PlayerInteractiveEventHandler implements Listener {

    private final GameModeContent gameModeContent;
    private final ExperditionContent experditionContent;

    public PlayerInteractiveEventHandler(GameModeContent gameModeContent,
                                         ExperditionContent experditionContent) {
        this.gameModeContent = gameModeContent;
        this.experditionContent = experditionContent;
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {

        //Todo: 리팩토링할것
        //플레이어의 인터랙티브 이벤트를 더함
        if (event.getItem() == null) {
            return;
        }

        if(event.getItem().getType() == Material.WOODEN_AXE){
            showBlockData(event);
        }

        else if (event.getItem().getType() == Material.GOLDEN_SWORD &&
                event.getAction() == Action.LEFT_CLICK_BLOCK &&
                event.hasBlock() &&
                event.getClickedBlock().getType() == Material.GOLD_BLOCK) {
            voteExperditionResult(event);
        }
    }

    private void showBlockData(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if(block == null){ return;}

        double X = block.getLocation().getX();
        double Y = block.getLocation().getY();
        double Z = block.getLocation().getZ();

        System.out.println("X : " + X + " Y : " + Y + " Z : " + Z);
    }

    private void voteExperditionResult(PlayerInteractEvent event) {
        //Todo: 백엔드에 데이터 전송하는 기능 추가

        Player player = event.getPlayer();

        //0. 현재 진행중인 expedition들을 갖고옴
        if (!experditionContent.isExsist()) {
            player.sendMessage(ChatColor.RED + "원정이 시작되지 않아서 원정결과 투표가 불가능합니다.");
            return;
        }

        ICharacter iCharacter = experditionContent.findExpertiesFromPlayer(event.getPlayer());

        if(iCharacter == null){
            player.sendMessage(ChatColor.RED + "원정대원이 아니여서 투표가 불가능합니다.");
            return;
        }

        //2. 투표 정보 저장
        experditionContent.addVote(iCharacter, true);

        Server server = player.getServer();
        Block touchedBlock = event.getClickedBlock();

        //3. 블록을 다이아몬드 색으로 변경
        touchedBlock.setType(Material.DIAMOND_BLOCK);

        //플레이어의 황금칼을 뺏음
        ICharacter character = gameModeContent.findCharacterFromPlayer(player);
        CharacterContent.removeItem(character, Material.GOLDEN_SWORD);
    }
}
