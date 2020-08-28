package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ShowGameRoleCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;

    public ShowGameRoleCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<ICharacter> iCharacterList = gameModeContent.characterList();

        if(iCharacterList.isEmpty()){
            sender.sendMessage("게임 시작한 캐릭터들이 없습니다.");
            return true;
        }

        for(ICharacter iCharacter : iCharacterList){
            if(iCharacter.getGameRole() == null){
                continue;
            }

            sender.sendMessage(iCharacter.getPlayer().getDisplayName() + " : "  + iCharacter.getGameRole().getNameKR());
        }
        return true;
    }
}
