package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetExcaliburOwnerCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;

    public SetExcaliburOwnerCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            sender.sendMessage("/엑스칼리버지정 <닉네임>");
            return true;
        }

        String name = args[0];

        ICharacter kingCharacter = gameModeContent.findCharacterFromName(name);

        if(kingCharacter == null){
            sender.sendMessage("해당 플레이어를 찾지 못했습니다.");
            return true;
        }

        Bukkit.broadcastMessage("강제로 엑스칼리버 제작자를 옮깁니다.");

        ICharacter excaliverOwner = gameModeContent.findCharacterByGameRole(GAME_ROLE.EXCALIBUR_OWNER);

        if(excaliverOwner == null) {
            gameModeContent.gameRoleContent().awardExperditionLead(kingCharacter);
        } else{
            gameModeContent.gameRoleContent().transitionToExperditionLead(excaliverOwner, kingCharacter, gameModeContent.isExcaliberExsist());
        }
        return true;
    }
}
