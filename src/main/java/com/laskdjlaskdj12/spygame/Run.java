package com.laskdjlaskdj12.spygame;

import com.laskdjlaskdj12.spygame.command.*;
import com.laskdjlaskdj12.spygame.command.cheat.*;
import com.laskdjlaskdj12.spygame.command.experdition.AddExperditionerCommand;
import com.laskdjlaskdj12.spygame.command.experdition.StartExperditionCommand;
import com.laskdjlaskdj12.spygame.command.experdition.StopExperditionCommand;
import com.laskdjlaskdj12.spygame.content.*;
import com.laskdjlaskdj12.spygame.event.PlayerHitEventHandler;
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
        ExperditionContent experditionContent = new ExperditionContent();
        GameModeContent gameModeContent = new GameModeContent(experditionContent);

        //Todo: 이벤트 핸들러 construct들에 컨텐츠 핸들러 factory를 dependency로 리팩토링
        //이벤트 등록
        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractiveEventHandler(experditionContent), this);
        getServer().getPluginManager().registerEvents(new PlayerHitEventHandler(gameModeContent), this);

        //커맨드 등록
        getCommand("start").setExecutor(new StartCommand(roleContent, characterContent, messageContent, gameModeContent));
        getCommand("vote").setExecutor(new VoteCommand(gameModeContent, this));
        getCommand("pickPerson").setExecutor(new PickPersonCommand(gameModeContent, this));
        getCommand("원정").setExecutor(new StartExperditionCommand(gameModeContent, experditionContent, characterContent, this));
        getCommand("원정대원추가").setExecutor(new AddExperditionerCommand(characterContent, gameModeContent, experditionContent));
        getCommand("원정종료").setExecutor(new StopExperditionCommand(gameModeContent, experditionContent, characterContent, this));
        getCommand("투표결과공개").setExecutor(new ShowVoteResultCommand(gameModeContent, this));
        getCommand("마차시퀸스").setExecutor(new StartTeleportToLobbyCommand(gameModeContent, this));
        getCommand("왕지정").setExecutor(new SetKingCommand(gameModeContent));
        getCommand("호수의요정지정").setExecutor(new SetLakeElfCommand(gameModeContent));
        getCommand("원정대장지정").setExecutor(new SetExperditionLeadCommand(gameModeContent));

        //Todo: 디버깅용으로 제거해야함
        getCommand("투표결과").setExecutor(new ShowExpeditionVoteCommand(experditionContent));
        getCommand("강제원정참여").setExecutor(new ForceAddExpeditionCommand(experditionContent, characterContent, gameModeContent));
        getCommand("강제캐릭터생성").setExecutor(new ForceCreateCharacterCommand(characterContent, gameModeContent));
        getCommand("투표결과블록수집").setExecutor(new CollectVoteResultBlock(gameModeContent));
        getCommand("원정역활보기").setExecutor(new ShowGameRoleCommand(gameModeContent));
    }

    @Override
    public void onDisable() {
    }
}
