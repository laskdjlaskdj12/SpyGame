package com.laskdjlaskdj12.spygame.content.character;

import com.laskdjlaskdj12.spygame.content.lifecycle.ILifecycle;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.factory.LifeCycleFactory;
import com.laskdjlaskdj12.spygame.status.LIFE_STATUS;
import org.bukkit.entity.Player;

public class BasicCharacter implements ICharacter {

    private IRole role;
    private Player player;
    private ILifecycle lifeCycle;

    @Override
    public void changeState(LIFE_STATUS born) { lifeCycle = LifeCycleFactory.createLifeCycle(born); }

    @Override
    public void setRole(IRole role) {
        this.role = role;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public IRole getRole() { return role; }

    @Override
    public Player getPlayer() { return player; }
}
