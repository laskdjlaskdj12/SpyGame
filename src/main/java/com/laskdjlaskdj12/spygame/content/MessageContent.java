package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageContent {
    private final World world;

    public MessageContent(World world) {
        this.world = world;
    }

    public void sendWorldMessage(String s) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(s);
        }
    }

    public void sendTitleToAll(String titleMessage, String subTitleMessage) {
        List<Player> players = world.getPlayers();

        for (Player player : players) {
            player.sendTitle(titleMessage,
                    subTitleMessage,
                    TickUtil.secondToTick(2),
                    TickUtil.secondToTick(5),
                    TickUtil.secondToTick(2));
        }
    }
}
