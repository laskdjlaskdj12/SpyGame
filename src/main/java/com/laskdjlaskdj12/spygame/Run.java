package com.laskdjlaskdj12.spygame;

import com.laskdjlaskdj12.spygame.command.*;
import com.laskdjlaskdj12.spygame.command.experdition.AddExperditionerCommand;
import com.laskdjlaskdj12.spygame.command.experdition.StartExperditionCommand;
import com.laskdjlaskdj12.spygame.command.experdition.StopExperditionCommand;
import com.laskdjlaskdj12.spygame.command.cheat.ForceAddExpeditionCommand;
import com.laskdjlaskdj12.spygame.command.cheat.ForceCreateCharacterCommand;
import com.laskdjlaskdj12.spygame.command.cheat.ShowExpeditionVote;
import com.laskdjlaskdj12.spygame.content.*;
import com.laskdjlaskdj12.spygame.event.PlayerInteractiveEventHandler;
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
        getServer().getPluginManager().registerEvents(new PlayerInteractiveEventHandler(experditionContent), this);

        //커맨드 등록
        getCommand("start").setExecutor(new StartCommand(roleContent, characterContent, messageContent, gameModeContent));
        getCommand("vote").setExecutor(new VoteCommand(gameModeContent, this));
        getCommand("pickPerson").setExecutor(new PickPersonCommand(gameModeContent, this));
        getCommand("원정").setExecutor(new StartExperditionCommand(gameModeContent, experditionContent, characterContent, this));
        getCommand("원정대원추가").setExecutor(new AddExperditionerCommand(characterContent, gameModeContent, experditionContent));
        getCommand("원정종료").setExecutor(new StopExperditionCommand(gameModeContent, experditionContent, characterContent, this));

        //Todo: 디버깅용으로 제거해야함
        getCommand("투표결과").setExecutor(new ShowExpeditionVote(experditionContent));
        getCommand("강제원정참여").setExecutor(new ForceAddExpeditionCommand(experditionContent, characterContent, gameModeContent));
        getCommand("강제캐릭터생성").setExecutor(new ForceCreateCharacterCommand(characterContent, gameModeContent));
    }

    @Override
    public void onDisable() {
    }
}
