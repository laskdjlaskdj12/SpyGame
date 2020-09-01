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

    public static Cordinate[] VoteBlockCordinate = {
        Cordinate.builder().X(155).Y(64).Z(154).build(),
        Cordinate.builder().X(152).Y(64).Z(154).build(),
        Cordinate.builder().X(149).Y(64).Z(154).build(),
        Cordinate.builder().X(149).Y(64).Z(151).build(),
        Cordinate.builder().X(149).Y(64).Z(148).build()
    };
}
