package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AnnounceLoseCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;

    public AnnounceLoseCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        gameModeContent.characterList()
                .forEach(iCharacter -> iCharacter
                        .getPlayer()
                        .sendTitle(ChatColor.RED + "악의 세력 " + ChatColor.WHITE + "승리",
                                "",
                                TickUtil.secondToTick(2),
                                TickUtil.secondToTick(4),
                                TickUtil.secondToTick(2)));
        gameModeContent.clearGame();
        return false;
    }
}
