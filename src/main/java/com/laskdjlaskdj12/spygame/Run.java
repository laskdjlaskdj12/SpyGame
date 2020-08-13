package com.laskdjlaskdj12.spygame;

import com.laskdjlaskdj12.spygame.command.StartCommand;
import com.laskdjlaskdj12.spygame.command.VoteCommand;
import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.MessageContent;
import com.laskdjlaskdj12.spygame.content.RoleContent;
import com.laskdjlaskdj12.spygame.event.PlayerJoinEventHandler;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Run extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("===========================================================");
        getLogger().info("Avalon By Minecraft");
        getLogger().info("Version: 0.1");
        getLogger().info("개발자: 라스크");
        getLogger().info("===========================================================");

        World world = getServer().getWorlds().get(0);

        RoleContent roleContent = new RoleContent();
        CharacterContent characterContent = new CharacterContent();
        MessageContent messageContent = new MessageContent(world);
        GameModeContent gameModeContent = new GameModeContent();

        //이벤트 등록
        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
        //커맨드 등록
        getCommand("start").setExecutor(new StartCommand(roleContent, characterContent, messageContent, gameModeContent));
        getCommand("vote").setExecutor(new VoteCommand(gameModeContent, this));
//        getCommand("pickPerson").setExecutor(new PickPersonCommand(gameModeContent, this));
    }

    @Override
    public void onDisable() {
    }
}
