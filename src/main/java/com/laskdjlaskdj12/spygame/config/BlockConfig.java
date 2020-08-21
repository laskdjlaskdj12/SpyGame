package com.laskdjlaskdj12.spygame.config;

import com.laskdjlaskdj12.spygame.domain.Cordinate;
import org.bukkit.Material;

public class BlockConfig {
    public static Material[] AnimatedBlockMaterialList = {Material.GREEN_WOOL,
            Material.WHITE_WOOL,
            Material.BLACK_WOOL,
            Material.BLUE_WOOL,
            Material.BROWN_WOOL,
            Material.CYAN_WOOL,
            Material.LIGHT_BLUE_WOOL,
            Material.YELLOW_WOOL,
            Material.RED_WOOL};

    public static Cordinate[] VoteBlockCordinate = {
        Cordinate.builder().X(155).Y(64).Z(154).build(),
        Cordinate.builder().X(152).Y(64).Z(154).build(),
        Cordinate.builder().X(149).Y(64).Z(154).build(),
        Cordinate.builder().X(149).Y(64).Z(151).build(),
        Cordinate.builder().X(149).Y(64).Z(148).build()
    };
}
