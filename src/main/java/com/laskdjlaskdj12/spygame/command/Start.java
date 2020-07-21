package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.MessageContent;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.content.RoleContent;
import com.laskdjlaskdj12.spygame.status.LIFE_STATUS;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Start implements CommandExecutor {

    private RoleContent roleContent;
    private CharacterContent characterContent;
    private MessageContent messageContent;

    public Start(RoleContent roleContent,
                 CharacterContent characterContent,
                 MessageContent messageContent) {
        this.roleContent = roleContent;
        this.characterContent = characterContent;
        this.messageContent = messageContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /*
            게임 순서
            1. 참여하는 사람들을 전부 역활을 할당 받는다.
            2. 각 역활마다 필요한 이펙트와 상황들을 넣는다.
         */

        if (!(sender instanceof Player)) {
            sender.sendMessage("플레이어만 커맨드를 활성화할수있습니다.");
            return true;
        }

        Player player = (Player)sender;
        World world = player.getWorld();

        List<Player> players = world.getPlayers();

        //player가 이미 있는지 체크
        if (players.size() > 8 || players.size() < 4) {
            sender.sendMessage(ChatColor.RED + "인원수가 부족합니다.");
            sender.sendMessage(ChatColor.RED + "최소 플레이는 4명 에서 8명 까지입니다.");

            return true;
        }

        // 각 player마다 역활들을 랜덤으로 정함
        messageContent.sendWorldMessage(Color.YELLOW + "플레이어에게 역활을 랜덤으로 나누는 중...");

        ArrayList<IRole> roleList = roleContent.createRole(players);
        List<ICharacter> characters = characterContent.createCharacter(roleList, players);
        messageContent.sendWorldMessage(Color.YELLOW + "각 캐릭터마다 역활 배정 완료!");
        messageContent.sendWorldMessage(Color.YELLOW + "각 역활 이펙트들 및 OBS 효과 세팅중... .");

        // 각 character 마다 상황들을 보여줌.
        for (ICharacter character : characters) {
            character.changeState(LIFE_STATUS.BORN);
        }

        messageContent.sendWorldMessage(Color.GREEN + "모든 세팅이 완료되었습니다!! 해피 아발론");
        return true;
    }
}
