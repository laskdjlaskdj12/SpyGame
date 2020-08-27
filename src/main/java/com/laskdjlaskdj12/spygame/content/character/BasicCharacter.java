package com.laskdjlaskdj12.spygame.content.character;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.lifecycle.ILifecycle;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.factory.LifeCycleFactory;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import com.laskdjlaskdj12.spygame.status.LIFE_STATUS;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.laskdjlaskdj12.spygame.status.ROLE_TYPE.MARLINE;
import static com.laskdjlaskdj12.spygame.status.ROLE_TYPE.MORGANA;

public class BasicCharacter implements ICharacter {

    private IRole role;
    private Player player;
    private ILifecycle lifeCycle;
    private GAME_ROLE gameRole = GAME_ROLE.NONE;

    //Todo: 라이프 사이클 이 언제 필요한지 체크하고 만약 필요없으면 바로 지우기 (리팩토링 필수)
    @Override
    public void changeState(LIFE_STATUS lifeStatus) {
        lifeCycle = LifeCycleFactory.createLifeCycle(lifeStatus);
    }

    @Override
    public void setRole(IRole role) {
        this.role = role;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setGameRole(GAME_ROLE gameRole) {
        this.gameRole = gameRole;
    }

    @Override
    public IRole getRole() {
        return role;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public GAME_ROLE getGameRole() {
        return this.gameRole;
    }

    //Todo: state 패턴으로 리팩토링 할것
    public void initCharacter(GameModeContent gameModeContent) {

        if (gameModeContent.characterList().size() == 0) {
            return;
        }

        switch (role.roleType()) {
            case MODRED:
                initMordred(gameModeContent);
                break;
            case ASSASSINE:
                initAssassine(gameModeContent);
                break;
            case CERVENT:
                initCervent(gameModeContent);
                break;
            case MARLINE:
                initMarline(gameModeContent);
                break;
            case MORGANA:
                initMorgana(gameModeContent);
                break;
            case OVERONE:
                initOverone(gameModeContent);
                break;
            case PERCIVAL:
                initPercival(gameModeContent);
                break;
        }
    }

    private void showEvilMembers(GameModeContent gameModeContent) {
        List<String> evilCharacterNames = gameModeContent.evilCharacters()
                .stream()
                .map(character -> character.getPlayer().getDisplayName())
                .collect(Collectors.toList());


        StringBuilder mainTitle = new StringBuilder().append("[")
                .append(String.join(",", evilCharacterNames))
                .append("]");

        player.sendTitle(mainTitle.toString(), "은 악의 세력입니다.", TickUtil.secondToTick(2), TickUtil.secondToTick(3), TickUtil.secondToTick(2));
    }

    private void initMordred(GameModeContent gameModeContent) {
        //악의 세력들을 보여주기
        showEvilMembers(gameModeContent);
    }

    private void initAssassine(GameModeContent gameModeContent) {
        //악의 세력들을 보여주기
        showEvilMembers(gameModeContent);
    }

    private void initCervent(GameModeContent gameModeContent) {

    }

    private void initMarline(GameModeContent gameModeContent) {
        //악의 세력 보여주기
        showEvilMembers(gameModeContent);
    }

    private void initMorgana(GameModeContent gameModeContent) {
        //악의 세력 보여주기
        showEvilMembers(gameModeContent);
    }

    private void initOverone(GameModeContent gameModeContent) {

    }

    private void initPercival(GameModeContent gameModeContent) {
        if(gameModeContent.characterList().size() == 0){
            return;
        }

        //모르가나와 멀린을 두개의 멀린으로 보인다.
        String marlinePlayerName = gameModeContent.findCharacterFromRole(MARLINE).getPlayer().getDisplayName();
        String morganaPlayerName = gameModeContent.findCharacterFromRole(MORGANA).getPlayer().getDisplayName();

        //멀린이 두명이라는것을 보여줌
        player.sendTitle(String.join(",", Arrays.asList(marlinePlayerName, morganaPlayerName)), "는 멀린입니다.", TickUtil.secondToTick(2), TickUtil.secondToTick(3), TickUtil.secondToTick(2));
    }
}
