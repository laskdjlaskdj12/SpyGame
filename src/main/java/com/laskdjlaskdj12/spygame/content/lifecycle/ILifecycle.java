package com.laskdjlaskdj12.spygame.content.lifecycle;

import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import org.bukkit.entity.Player;

public interface ILifecycle {
    void action(Player player, IRole role, GAME_ROLE gameRole);
}
