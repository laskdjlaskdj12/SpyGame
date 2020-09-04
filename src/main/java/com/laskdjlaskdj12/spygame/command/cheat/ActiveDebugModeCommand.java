package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.Bukkit;
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
            Bukkit.broadcastMessage("릴리즈 모드로 변경!");
            Bukkit.broadcastMessage("운영진이 포함이 안되도록 변경했습니다..");
            gameModeContent.activeReleaseMode();
            return true;
        }

        gameModeContent.activeDebugMode();
        Bukkit.broadcastMessage("디버그 모드로 변경!");
        Bukkit.broadcastMessage("모든 사람들을 유저로 변경합니다.");
        return true;
    }
}
