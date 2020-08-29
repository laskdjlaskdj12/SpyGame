package com.laskdjlaskdj12.spygame.command.experdition;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class StopExperditionCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;
    private final ExperditionContent experditionContent;
    private final JavaPlugin javaPlugin;
    private final CharacterContent characterContent;

    public StopExperditionCommand(GameModeContent gameModeContent,
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

        if (!experditionContent.isExsist()) {
            sender.sendMessage("원정이 이미 종료가 되었습니다.");
            return false;
        }

        experditionContent.stop();
        sender.sendMessage("원정이 종료되었습니다");

        return true;
    }
}
