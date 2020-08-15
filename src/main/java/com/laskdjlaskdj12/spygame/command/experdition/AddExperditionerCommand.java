package com.laskdjlaskdj12.spygame.command.experdition;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddExperditionerCommand implements CommandExecutor {

    private final ExperditionContent experditionContent;
    private final CharacterContent characterContent;
    private final GameModeContent gameModeContent;

    public AddExperditionerCommand(CharacterContent characterContent,
                                   GameModeContent gameModeContent,
                                   ExperditionContent experditionContent) {
        this.characterContent = characterContent;
        this.gameModeContent = gameModeContent;
        this.experditionContent = experditionContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length != 1){
            sender.sendMessage("사용법: /원정대원추가 <아이디>");
            return false;
        }

        String experditionPlayerName= args[0];

        if(experditionPlayerName == null){
            sender.sendMessage(ChatColor.RED + "원정에 나갈 플레이어를 탐지를 못했습니다.");
            return false;
        }

        ICharacter experditioner = gameModeContent.findCharacterFromName(experditionPlayerName);

        if(experditioner == null){
            sender.sendMessage(ChatColor.RED + "아발론에 등록된 플레이어가 아닙니다.");
            return false;
        }

        //원정 정보 시작함
        if(!experditionContent.isExsist()){
            sender.sendMessage(ChatColor.RED + "진행중인 원정을 발견하지 못했습니다. 원정을 만들고 난다음에 시도해주세요");
            return false;
        }

        experditionContent.addExperditioner(experditioner);

        sender.sendMessage(experditioner.getPlayer().getDisplayName() + "님을 원정대원으로 추가했습니다.");

        return true;
    }
}
