package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AnnounceWinCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;

    public AnnounceWinCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        gameModeContent.declareWin();
        return true;
    }
}
