package com.laskdjlaskdj12.spygame.command.cheat;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChangeToGoldBlockCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        List<Block> blockList = new ArrayList<Block>();

        blockList.add(player.getWorld().getBlockAt(611, 11, -90));
        blockList.add(player.getWorld().getBlockAt(611, 11, 4));
        blockList.add(player.getWorld().getBlockAt(611, 11, 97));

        blockList.add(player.getWorld().getBlockAt(646, 24, 195));
        blockList.add(player.getWorld().getBlockAt(646, 24, 303));
        blockList.add(player.getWorld().getBlockAt(646, 24, 411));
        blockList.add(player.getWorld().getBlockAt(646, 24, 519));

        blockList.add(player.getWorld().getBlockAt(499, 11, -184));
        blockList.add(player.getWorld().getBlockAt(499, 11, -89));
        blockList.add(player.getWorld().getBlockAt(499, 11, 5));
        blockList.add(player.getWorld().getBlockAt(499, 11, 99));

        blockList.add(player.getWorld().getBlockAt(530, 21, 226));
        blockList.add(player.getWorld().getBlockAt(530, 21, 336));
        blockList.add(player.getWorld().getBlockAt(530, 21, 446));
        blockList.add(player.getWorld().getBlockAt(530, 21, 556));
        blockList.add(player.getWorld().getBlockAt(530, 21, 666));

        blockList.add(player.getWorld().getBlockAt(379, 6, 213));
        blockList.add(player.getWorld().getBlockAt(379, 6, 320));
        blockList.add(player.getWorld().getBlockAt(379, 6, 427));
        blockList.add(player.getWorld().getBlockAt(379, 6, 534));
        blockList.add(player.getWorld().getBlockAt(379, 6, 641));

        blockList.forEach(block -> block.setType(Material.GOLD_BLOCK));
        return true;
    }
}
