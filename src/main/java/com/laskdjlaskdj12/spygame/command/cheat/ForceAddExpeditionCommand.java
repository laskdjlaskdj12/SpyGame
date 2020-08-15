package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceAddExpeditionCommand implements CommandExecutor {

    private final ExperditionContent experditionContent;
    private final CharacterContent characterContent;
    private final GameModeContent gameModeContent;

    public ForceAddExpeditionCommand(ExperditionContent experditionContent,
                                     CharacterContent characterContent,
                                     GameModeContent gameModeContent) {
        this.experditionContent = experditionContent;
        this.characterContent = characterContent;
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!experditionContent.isExsist()){
            sender.sendMessage("[테스트] 진행중인 원정이 없습니다. 원정을 만들어주세요");
            return false;
        }

        Player player = (Player)sender;
        ICharacter iCharacter = gameModeContent.findCharacterFromPlayer(player);

        if(iCharacter == null){
            sender.sendMessage("[테스트] 아발론 플레이어가 아닙니다. 아발론 플레이어로 등록해주세요");
            return false;
        }

        experditionContent.addExperditioner(iCharacter);

        sender.sendMessage("[테스트] : " + player.getDisplayName() + "님을 원정대원으로 추가했습니다.");

        return true;
    }
}
