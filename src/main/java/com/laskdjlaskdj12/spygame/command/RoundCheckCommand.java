package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class RoundCheckCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;

    public RoundCheckCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        World world = ((Player) sender).getWorld();

        world.getPlayers().stream()
                .forEach(this::showTitle);

        return false;
    }

    private void showTitle(Player player) {
        if (isGameOver()) {
            player.sendTitle(ChatColor.GREEN + "아발론 모든 원정이 끝났습니다.", ChatColor.RED + " 어쌔신" + "이 멀린을 살해할 시간입니다.", 20, 60, 20);
            return;
        }

        player.sendTitle(ChatColor.GREEN + "[" + gameModeContent.experditionContent().roundCount() + " / 5" + "]", "라운드", 20, 60, 20);
    }

    private boolean isGameOver() {
        if (gameModeContent.experditionCount() > 3) {
            if (gameModeContent.getLoseCount() == 3 || gameModeContent.getWinCount() == 3) {
                return true;
            }
        }

        return gameModeContent.experditionContent().roundCount() > 5;
    }
}
