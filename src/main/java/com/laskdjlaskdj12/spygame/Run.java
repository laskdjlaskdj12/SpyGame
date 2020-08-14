package com.laskdjlaskdj12.spygame;

import com.laskdjlaskdj12.spygame.command.*;
import com.laskdjlaskdj12.spygame.command.test.ShowExpeditionVote;
import com.laskdjlaskdj12.spygame.content.*;
import com.laskdjlaskdj12.spygame.event.PlayerHitEventHandler;
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
        ExperditionContent experditionContent = new ExperditionContent();

        //Todo: 이벤트 핸들러 construct들에 컨텐츠 핸들러 factory를 dependency로 리팩토링
        //이벤트 등록
        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerHitEventHandler(experditionContent), this);

        //커맨드 등록
        getCommand("start").setExecutor(new StartCommand(roleContent, characterContent, messageContent, gameModeContent));
        getCommand("vote").setExecutor(new VoteCommand(gameModeContent, this));
        getCommand("pickPerson").setExecutor(new PickPersonCommand(gameModeContent, this));
        getCommand("원정").setExecutor(new StartExperditionCommand(gameModeContent, experditionContent, characterContent, this));
        getCommand("원정끝").setExecutor(new CloseExpeditionCommand(gameModeContent, experditionContent, this));

        //Todo: 디버깅용으로 제거해야함
        getCommand("투표결과").setExecutor(new ShowExpeditionVote(experditionContent));
    }

    @Override
    public void onDisable() {
    }
}
