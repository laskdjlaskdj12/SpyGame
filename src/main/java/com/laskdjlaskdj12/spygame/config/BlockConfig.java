package com.laskdjlaskdj12.spygame.config;

import com.laskdjlaskdj12.spygame.domain.Cordinate;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

public class BlockConfig {
    public static DyeColor[] WOOL_COLOR_LIST = {DyeColor.GREEN,
            DyeColor.WHITE,
            DyeColor.BLACK,
            DyeColor.BLUE,
            DyeColor.BROWN,
            DyeColor.CYAN,
            DyeColor.LIGHT_BLUE,
            DyeColor.YELLOW,
            DyeColor.RED};

    public static Material[] MATERIAL_WOOL_COLOR_LIST = {
            Material.WHITE_WOOL,
            Material.BLACK_WOOL,
            Material.BLUE_WOOL,
            Material.BROWN_WOOL,
            Material.CYAN_WOOL,
            Material.LIGHT_BLUE_WOOL,
            Material.YELLOW_WOOL,
            Material.RED_WOOL};

    public static Cordinate[] VoteBlockCordinate = {
//        Cordinate.builder().X(-11).Y(82).Z(38).build(),
//        Cordinate.builder().X(-13).Y(82).Z(38).build(),
//        Cordinate.builder().X(-15).Y(82).Z(38).build(),
//        Cordinate.builder().X(-17).Y(82).Z(38).build(),
//        Cordinate.builder().X(-19).Y(82).Z(38).build()
            Cordinate.builder().X(155).Y(64).Z(154).build(),
            Cordinate.builder().X(152).Y(64).Z(154).build(),
            Cordinate.builder().X(149).Y(64).Z(154).build(),
            Cordinate.builder().X(149).Y(64).Z(151).build(),
            Cordinate.builder().X(149).Y(64).Z(148).build()
    };
}
