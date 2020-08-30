package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceCreateCharacterCommand implements CommandExecutor {

    private final CharacterContent characterContent;
    private final GameModeContent gameModeContent;

    public ForceCreateCharacterCommand(GameModeContent gameModeContent) {
        this.characterContent = gameModeContent.getCharacterContent();
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ICharacter iCharacter = characterContent.createCharacterDebug((Player) sender);
        gameModeContent.addCharacterDebug(iCharacter);

        sender.sendMessage("캐릭터가 강제로 생성이 되었습니다.");
        return true;
    }
}
