package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import com.laskdjlaskdj12.spygame.status.LIFE_STATUS;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetKingCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;

    public SetKingCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            sender.sendMessage("/왕지정 <닉네임>");
            return true;
        }

        String name = args[1];

        ICharacter kingCharacter = gameModeContent.findCharacterFromName(name);

        if(kingCharacter == null){
            sender.sendMessage("해당 플레이어를 찾지 못했습니다.");
            return true;
        }

        kingCharacter.setGameRole(GAME_ROLE.KING);
        return true;
    }
}
