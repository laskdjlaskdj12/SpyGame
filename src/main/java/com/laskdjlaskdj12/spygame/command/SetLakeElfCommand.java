package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetLakeElfCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;

    public SetLakeElfCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            sender.sendMessage("/호수의요정지정 <닉네임>");
            return true;
        }

        String name = args[0];

        ICharacter elfCharacter = gameModeContent.findCharacterFromName(name);

        if(elfCharacter == null){
            sender.sendMessage(elfCharacter + "플레이어를 찾지 못했습니다.");
            return true;
        }

        //기존에 호수의 요정이 이미 있는지 체크
        ICharacter beforeLakeElf = gameModeContent.findLakeElfCharacter();

        if(beforeLakeElf != null) {
            gameModeContent.changeLakeElf(beforeLakeElf, elfCharacter);
            return true;
        }

        gameModeContent.makeLakeElf(elfCharacter);
        return true;
    }
}
