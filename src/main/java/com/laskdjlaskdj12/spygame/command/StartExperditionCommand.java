package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StartExperditionCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;
    private final ExperditionContent experditionContent;
    private final JavaPlugin javaPlugin;
    private final CharacterContent characterContent;

    public StartExperditionCommand(GameModeContent gameModeContent,
                                   ExperditionContent experditionContent,
                                   CharacterContent characterContent,
                                   JavaPlugin plugin) {
        this.experditionContent = experditionContent;
        this.gameModeContent = gameModeContent;
        this.javaPlugin = plugin;
        this.characterContent = characterContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //지금 진행중인 원정이 있는지 체크

        if (experditionContent.isExsist()) {
            sender.sendMessage("이미 진행중인 원정이 있습니다. 진행중인 원정을 끝내주세요");
            return false;
        }

        sender.sendMessage("원정을 시작합니다.");

        //원정이 없다면 원정을 등록함
        experditionContent.init();

        /**
         * 디버그모드로 원정 캐릭터 사람을 추가
         */

        ICharacter iCharacter = characterContent.createCharacterDebug((Player)sender);
        experditionContent.addExperditioner(iCharacter);

        //Todo: 디버깅 커맨드이므로 지워야합니다.
        sender.sendMessage("[테스트] : " + iCharacter.getPlayer().getDisplayName() + "님을 원정대원으로 추가했습니다.");

        return true;
    }
}
