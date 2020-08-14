package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CloseExpeditionCommand implements CommandExecutor {

    private final GameModeContent gameModeContent ;
    private final ExperditionContent experditionContent;
    private final JavaPlugin javaPlugin ;

    public CloseExpeditionCommand(GameModeContent gameModeContent, ExperditionContent experditionContent, JavaPlugin plugin) {
        this.experditionContent = experditionContent;
        this.gameModeContent = gameModeContent;
        this.javaPlugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage("원정을 끝냅니다.");
        experditionContent.stopExperdition();

        return true;
    }
}
