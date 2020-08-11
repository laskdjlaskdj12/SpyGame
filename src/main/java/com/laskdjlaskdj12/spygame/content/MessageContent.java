package com.laskdjlaskdj12.spygame.content;

import org.bukkit.World;
import org.bukkit.entity.Player;

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
}
