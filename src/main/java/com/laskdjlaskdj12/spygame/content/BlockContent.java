package com.laskdjlaskdj12.spygame.content;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class BlockContent {

    public static ItemStack makeWOOLColor(DyeColor color){
//        ItemStack item = new ItemStack(Material.WOOL);
//        item.setData(new Wool(color));
//
//        return item;
        return null;
    }

    public static ItemStack makeWOOLColor(Material material){
        return new ItemStack(material);
    }

    public static Material makeWOOLColorToMaterial(DyeColor color){
//        ItemStack item = new ItemStack(Material.WOOL);
//        item.setData(new Wool(color));
//
//        return item.getType();
        return null;
    }

    public static Material makeWOOLColorToMaterial(Material material){
        return material;
    }
}
