package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ActiveDebugModeCommand implements CommandExecutor {
    private final GameModeContent gameModeContent;

    public ActiveDebugModeCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(gameModeContent.isDebugMod()){
            gameModeContent.activeReleaseMode();
            return true;
        }

        gameModeContent.activeDebugMode();
        return true;
    }
}
