package com.laskdjlaskdj12.spygame.util;

import static com.laskdjlaskdj12.spygame.config.Timer.TICK;

public class TickUtil {
    public static int secondToTick(int second) {
        return TICK * second;
    }

    public static int tickToSecond(int tick){
        if (tick == 0){
            return 0;
        }

        return tick / 20;
    }
}
