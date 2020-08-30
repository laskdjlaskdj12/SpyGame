package com.laskdjlaskdj12.spygame.command.experdition;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class StartExperditionCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;

    public StartExperditionCommand(GameModeContent gameModeContent){
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //지금 진행중인 원정이 있는지 체크

        if (gameModeContent.experditionContent().isExsist()) {
            sender.sendMessage("이미 진행중인 원정이 있습니다. 진행중인 원정을 끝내주세요");
            return false;
        }

        sender.sendMessage("원정을 시작합니다.");

        //원정이 없다면 원정을 등록함
        gameModeContent.experditionContent().start();
        return true;
    }
}
