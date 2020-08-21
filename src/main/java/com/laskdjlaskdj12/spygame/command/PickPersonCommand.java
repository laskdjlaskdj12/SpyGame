package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.vote.PickPlayerVoteContent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public class PickPersonCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;
    private final JavaPlugin plugin;

    public PickPersonCommand(GameModeContent gameModeContent,
                             JavaPlugin plugin) {
        this.gameModeContent = gameModeContent;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Todo: 리팩토링 및 라이브 테스트 준비할것

        List<ICharacter> characters = gameModeContent.characterList();

        if (characters.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "게임이 시작되지 않아서 지목 투표를 할수가 없습니다. 다시 시도해주세요");
            return false;
        }

        String pickedName = args[1];
        String reason = args[2];
        Bukkit.broadcastMessage(ChatColor.YELLOW + pickedName + " 님을 " + reason + " 하는것에 대해 투표를 시작합니다.");

        List<ICharacter> pickedCharacters = characters.stream()
                .filter(character -> character.getPlayer().getDisplayName().equals(pickedName))
                .collect(Collectors.toList());

        if (pickedCharacters.size() == 0) {
            sender.sendMessage(ChatColor.RED + "지목한 " + pickedName + " 을 찾을수가없습니다.");
            return false;
        }

        ICharacter pickedCharacter = pickedCharacters.get(0);

        //플레이어가 지목한 사람에 대해 투표 절차 들어감
        new PickPlayerVoteContent(plugin, 10)
                .startVote(characters, pickedCharacter.getPlayer());

        return true;
    }
}
