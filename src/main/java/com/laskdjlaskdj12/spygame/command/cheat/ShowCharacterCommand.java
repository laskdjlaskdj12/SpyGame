package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class ShowCharacterCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;

    public ShowCharacterCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("커맨드를 시작합니다.");
        List<ICharacter> characters = gameModeContent.characterList();
        if (characters.isEmpty()) {
            sender.sendMessage("게임이 시작되지 않아서 캐릭터가 없습니다.");
            return true;
        }
        sender.sendMessage("정보들을 수집합니다.");
        List<String> allCharacterInfoStringList = characters.stream()
                .map(character -> new StringBuilder().append("[" + character.getPlayer().getDisplayName() + "]")
                        .append(" -> ")
                        .append(character.getRole().KRName())
                        .toString())
                .collect(Collectors.toList());

        sender.sendMessage(String.join("\n", allCharacterInfoStringList));
        return true;
    }
}
