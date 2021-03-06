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
import com.laskdjlaskdj12.spygame.sequence.ChooseLakeElfSequence;
import com.laskdjlaskdj12.spygame.sequence.ExperditionLeadSequence;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Run extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("===========================================================");
        getLogger().info("Avalon By Minecraft");
        getLogger().info("Version: 0.1");
        getLogger().info("개발자: 라스크");
        getLogger().info("With: 도리, 푸탱, 겜닭, 체리, 실피아, 시윤, 트리비, 판다, 개인지도, 맥남, 김기랑");
        getLogger().info("Message : 가이코 나빠요 이사람아 이 코드만 5천줄이야");
        getLogger().info("===========================================================");

        World world = getServer().getWorlds().get(0);

        RoleContent roleContent = new RoleContent();
        CharacterContent characterContent = new CharacterContent();
        MessageContent messageContent = new MessageContent(world);
        ExperditionContent experditionContent = new ExperditionContent();
        GameRoleContent gameRoleContent = new GameRoleContent();
        GameModeContent gameModeContent = new GameModeContent(experditionContent,
                gameRoleContent);

        ShowVoteResultCommand showVoteResultCommand = new ShowVoteResultCommand(gameModeContent, this);

        //Todo: 이벤트 핸들러 construct들에 컨텐츠 핸들러 factory를 dependency로 리팩토링
        //이벤트 등록
        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractiveEventHandler(gameModeContent, experditionContent), this);
        getServer().getPluginManager().registerEvents(new PlayerHitEventHandler(gameModeContent, messageContent), this);

        //커맨드 등록
        getCommand("start").setExecutor(new StartCommand(roleContent, characterContent, messageContent, gameModeContent));
        getCommand("vote").setExecutor(new VoteCommand(gameModeContent, this));
        getCommand("pickPerson").setExecutor(new PickPersonCommand(gameModeContent, this));
        getCommand("원정").setExecutor(new StartExperditionCommand(gameModeContent, experditionContent, characterContent, this));
        getCommand("원정종료").setExecutor(new StopExperditionCommand(gameModeContent, experditionContent, characterContent, this));
        getCommand("원정대원추가").setExecutor(new AddExperditionerCommand(characterContent, gameModeContent, experditionContent));
        getCommand("투표결과공개").setExecutor(showVoteResultCommand);
        getCommand("멀린암살").setExecutor(new AssinssineKillMarlineCommand(gameModeContent));
        getCommand("라운드체크").setExecutor(new RoundCheckCommand(gameModeContent));
        getCommand("멀린암살").setExecutor(new AssinssineKillMarlineCommand(gameModeContent));
        getCommand("게임끝").setExecutor(new GameClearCommand(gameModeContent));
        getCommand("승리선언").setExecutor(new AnnounceWinCommand(gameModeContent));
        getCommand("패배선언").setExecutor(new AnnounceLoseCommand(gameModeContent));
        getCommand("라운드별원정대원선발").setExecutor(new ChooseExpeditionMemberSequenceCommand(gameModeContent, messageContent));

        //원정 커맨드
        getCommand("엑스칼리버지정").setExecutor(new SetExcaliburOwnerCommand(gameModeContent));
        getCommand("호수의요정지정").setExecutor(new SetLakeElfCommand(gameModeContent));
        getCommand("원정대장지정").setExecutor(new SetExperditionLeadCommand(gameModeContent));

        //시퀸스커맨드
        getCommand("원정대장선정").setExecutor(new ExperditionLeadSequence(gameModeContent, this));
        getCommand("호수의요정선정").setExecutor(new ChooseLakeElfSequence(gameModeContent));
        getCommand("마차시퀸스").setExecutor(new StartTeleportToLobbySeuqence(gameModeContent, this, showVoteResultCommand));

        //Todo: 디버깅용으로 제거해야함
        getCommand("캐릭터정보").setExecutor(new ShowCharacterCommand(gameModeContent));
        getCommand("투표결과").setExecutor(new ShowExpeditionVoteCommand(experditionContent));
        getCommand("강제원정참여").setExecutor(new ForceAddExpeditionCommand(experditionContent, characterContent, gameModeContent));
        getCommand("강제캐릭터생성").setExecutor(new ForceCreateCharacterCommand(characterContent, gameModeContent));
        getCommand("투표결과블록수집").setExecutor(new CollectVoteResultBlock(gameModeContent));
        getCommand("원정역할보기").setExecutor(new ShowGameRoleCommand(gameModeContent));
        getCommand("지정캐릭터추가").setExecutor(new ForceCreatePacificCharacterCommand(experditionContent, characterContent, gameModeContent));
        getCommand("지정캐릭터게임롤추가").setExecutor(new ForceCreateExpeditionRoleCommand(gameModeContent));
        getCommand("삭제").setExecutor(new RemoveItemTest());
        getCommand("디버그모드").setExecutor(new ActiveDebugModeCommand(gameModeContent));
        getCommand("템이동").setExecutor(new ItemMoveCommand(gameModeContent));
        getCommand("addwool").setExecutor(new AddWoolCommand());
        getCommand("showcharacter").setExecutor(new ShowCharacterCommand(gameModeContent));
        getCommand("goldblock").setExecutor(new ChangeToGoldBlockCommand());
    }

    @Override
    public void onDisable() {
    }
}
