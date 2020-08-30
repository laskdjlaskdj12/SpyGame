package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameClearCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;

    public GameClearCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.broadcastMessage("게임이 끝났습니다!");
        gameModeContent.clearGame();
        return true;
    }
}
