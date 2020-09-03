package com.laskdjlaskdj12.spygame.command.experdition;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

        Bukkit.broadcastMessage("제" + String.valueOf(gameModeContent.experditionCount() + 1) + "원정을 시작합니다.");

        //원정이 없다면 원정을 등록함
        experditionContent.start();
        return true;
    }
}
