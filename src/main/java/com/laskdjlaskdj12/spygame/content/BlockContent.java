package com.laskdjlaskdj12.spygame.content;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class BlockContent {

    public static ItemStack makeWOOLItemByColor(DyeColor color){
        System.out.println("color : " + color.name());
        Wool wool = new Wool();
        wool.setColor(color);

        return new ItemStack(Material.WOOL, 1, (short)color.getWoolData());
    }

//    public static ItemStack makeWOOLColor(Material material){
//        return new ItemStack(material);
//    }

//    public static Material makeWOOLColorToMaterial(DyeColor color){
//        ItemStack item = new ItemStack(Material.WOOL);
//        item.setData(new Wool(color));
//
//        return item.getType();
//    }

//    public static Material makeWOOLColorToMaterial(Material material){
//        return material;
//    }

    public static boolean isAiBlock(ItemStack item){
        if(item == null){
            return false;
        }

        if(item.getType().equals(Material.WOOL)){
            return ((Wool)item.getData()).getColor() == DyeColor.WHITE;
        }

        return false;
    }

    public static boolean isNayBlock(ItemStack item){
        if(item == null){
            return false;
        }

        if(item.getType().equals(Material.WOOL)){
            return ((Wool)item.getData()).getColor() == DyeColor.BLACK;
        }

        return false;
    }
}
