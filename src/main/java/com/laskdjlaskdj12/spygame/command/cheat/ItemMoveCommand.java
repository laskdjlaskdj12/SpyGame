package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.BasicCharacter;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemMoveCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;
    private boolean isUpper = false;

    public ItemMoveCommand(GameModeContent gameModeContent1) {
        this.gameModeContent = gameModeContent1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ICharacter iCharacter = new BasicCharacter();
        iCharacter.setPlayer((Player)sender);

        if(!isUpper){
            moveItemToUpper(iCharacter);
        } else{
            moveItemToDown(iCharacter);
        }

        isUpper = !isUpper;
        return true;
    }

    public void moveItemToUpper(ICharacter iCharacter) {
        //하위 아래 아이템들을 전부 상위로 올려버림
        ItemStack zeroInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(0);
        ItemStack oneInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(1);
        ItemStack twoInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(2);
        ItemStack threeInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(3);
        ItemStack fourInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(4);
        ItemStack fiveInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(5);
        ItemStack sixInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(6);
        ItemStack sevenInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(7);
        ItemStack eightInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(8);

        iCharacter.getPlayer().getInventory().setItem(9, zeroInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(10, oneInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(11, twoInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(12, threeInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(13, fourInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(14, fiveInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(15, sixInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(16, sevenInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(17, eightInventorySlotItem);

        iCharacter.getPlayer().getInventory().setItem(0, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(1, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(2, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(3, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(4, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(5, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(6, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(7, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(8, new ItemStack(Material.AIR));
    }

    public void moveItemToDown(ICharacter iCharacter) {
        ItemStack zeroInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(9);
        ItemStack oneInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(10);
        ItemStack twoInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(11);
        ItemStack threeInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(12);
        ItemStack fourInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(13);
        ItemStack fiveInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(14);
        ItemStack sixInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(15);
        ItemStack sevenInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(16);
        ItemStack eightInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(17);

        iCharacter.getPlayer().getInventory().setItem(0, zeroInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(1, oneInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(2, twoInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(3, threeInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(4, fourInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(5, fiveInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(6, sixInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(7, sevenInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(8, eightInventorySlotItem);

        iCharacter.getPlayer().getInventory().setItem(9, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(10, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(11, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(12, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(13, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(14, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(15, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(16, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(17, new ItemStack(Material.AIR));
    }

}
