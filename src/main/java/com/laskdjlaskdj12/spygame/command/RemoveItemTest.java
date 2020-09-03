package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveItemTest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Bukkit.broadcastMessage("황금검을 삭제했습니다.");
        CharacterContent.removeItem(player, Material.GOLDEN_SWORD);
        return true;
    }
}
