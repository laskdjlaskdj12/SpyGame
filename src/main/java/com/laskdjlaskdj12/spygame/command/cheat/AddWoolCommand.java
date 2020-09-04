package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.BlockContent;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class AddWoolCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ItemStack whiteBlock = BlockContent.makeWOOLItemByColor(DyeColor.RED);
        ItemStack blackBlock = BlockContent.makeWOOLItemByColor(DyeColor.GREEN);

        Player player = (Player) sender;
        player.getInventory().addItem(whiteBlock);
        player.getInventory().addItem(blackBlock);

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (BlockContent.isAiBlock(itemStack)) {
            sender.sendMessage("확인");
        }

        sender.sendMessage("실패");

        return true;
    }
}
