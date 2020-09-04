package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.MessageContent;
import com.laskdjlaskdj12.spygame.content.RoleContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.status.LIFE_STATUS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class StartCommand implements CommandExecutor {

    private final RoleContent roleContent;
    private final CharacterContent characterContent;
    private final MessageContent messageContent;
    private final GameModeContent gameModeContent;

    public StartCommand(RoleContent roleContent,
                        CharacterContent characterContent,
                        MessageContent messageContent,
                        GameModeContent gameModeContent) {
        this.roleContent = roleContent;
        this.characterContent = characterContent;
        this.messageContent = messageContent;
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        /*
            게임 순서
            1. 참여하는 사람들을 전부 역활을 할당 받는다.
            2. 각 역활마다 필요한 이펙트와 상황들을 넣는다.
         */
        World world = Bukkit.getServer().getWorlds().get(0);
        List<Player> players = world.getPlayers();

//        //릴리즈 모드일때 운영진들을 제외함
//        if(!gameModeContent.isDebugMod()){
//            players = players.stream()
//                    .filter(player -> !gameModeContent.isAdmin(player))
//                    .collect(Collectors.toList());
//        }

        //player가 이미 있는지 체크
        if (players.size() > 10 || players.size() < 5) {
            sender.sendMessage(ChatColor.RED + "인원수가 부족합니다.");
            sender.sendMessage(ChatColor.RED + "최소 플레이는 5명 에서 10명 까지입니다.");

            return true;
        }

        // 각 player마다 역활들을 랜덤으로 정함
        messageContent.sendWorldMessage(ChatColor.YELLOW + "플레이어에게 역활을 랜덤으로 나누는 중...");

        List<IRole> roleList = roleContent.assignmentRole(players);
        List<ICharacter> characters = characterContent.createCharacter(roleList, players);
        messageContent.sendWorldMessage(ChatColor.YELLOW + "각 캐릭터마다 역활 배정 완료!");

        // 유저정보들을 저장
        messageContent.sendWorldMessage(ChatColor.YELLOW + "유저정보 저장 완료");
        gameModeContent.saveCharacter(characters);

        //캐릭터 초기화 하기
        for (ICharacter character: characters) {
            character.changeState(LIFE_STATUS.BORN);
            character.initCharacter(gameModeContent);
        }

        messageContent.sendWorldMessage(ChatColor.YELLOW + "유저정보 저장 완료");
        messageContent.sendWorldMessage("모든 세팅이 완료되었습니다!! Dev by " +  ChatColor.YELLOW + "laskdjlaskdj12 (라스크) " + ChatColor.WHITE + "& Thanks for " + ChatColor.GREEN + "우리들의 마인크래프트 공간");
        return true;
    }
}
