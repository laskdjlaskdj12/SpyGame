package com.laskdjlaskdj12.spygame.content.character;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.lifecycle.ILifecycle;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.factory.LifeCycleFactory;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import com.laskdjlaskdj12.spygame.status.LIFE_STATUS;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

import static com.laskdjlaskdj12.spygame.status.ROLE_TYPE.*;

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

    private void showPosition(ROLE_TYPE roleType){
        player.sendTitle("[ " + roleType.nameKR + " ]", "이 당신의 역할입니다.", TickUtil.secondToTick(2), TickUtil.secondToTick(3), TickUtil.secondToTick(2));
    }

    private String showEvilMembers(GameModeContent gameModeContent) {
        List<String> evilCharacterNames = gameModeContent.evilCharacters()
                .stream()
                .map(character -> character.getPlayer().getDisplayName())
                .collect(Collectors.toList());


        return new StringBuilder().append("[")
                .append(String.join(" , ", evilCharacterNames))
                .append("]")
                .toString();
    }
    
    private String showEvilMemberExceptModred(GameModeContent gameModeContent) {
        List<String> evilCharacterNames = gameModeContent.evilCharacters()
                .stream()
                .filter(iCharacter -> iCharacter.getRole().roleType() != MODRED)
                .map(character -> character.getPlayer().getDisplayName())
                .collect(Collectors.toList());


        return new StringBuilder().append("[")
                .append(String.join(" , ", evilCharacterNames))
                .append("]")
                .toString();
    }

    private String showMorganaAndMarline(GameModeContent gameModeContent) {
        String marlinePlayerName = gameModeContent.findCharacterFromRole(MARLINE).getPlayer().getDisplayName();
        String morganaPlayerName = gameModeContent.findCharacterFromRole(MORGANA).getPlayer().getDisplayName();

        return new StringBuilder().append("[")
                .append(marlinePlayerName)
                .append(" , ")
                .append(morganaPlayerName)
                .append("]")
                .toString();
    }
    private void showEvilMessage(ROLE_TYPE roleType, GameModeContent gameModeContent){
        player.sendTitle("[ " + ChatColor.RED + roleType.nameKR + ChatColor.WHITE + " ]", showEvilMembers(gameModeContent) + "님이 같은 편입니다.", TickUtil.secondToTick(2), TickUtil.secondToTick(5), TickUtil.secondToTick(2));
    }

    private void showMarlineMessage(GameModeContent gameModeContent){
        player.sendTitle("[ " + ChatColor.GREEN + MARLINE.nameKR + ChatColor.WHITE + " ]", showEvilMemberExceptModred(gameModeContent) + "님이 악의세력입니다.", TickUtil.secondToTick(2), TickUtil.secondToTick(5), TickUtil.secondToTick(2));
    }
    
    private void showPercivalMessage(GameModeContent gameModeContent){
        //모르가나와 멀린을 두개의 멀린으로 보인다.
        player.sendTitle("[ " + ChatColor.GREEN + PERCIVAL.nameKR + ChatColor.WHITE + " ]", showMorganaAndMarline(gameModeContent) + "님이 멀린입니다.", TickUtil.secondToTick(2), TickUtil.secondToTick(5), TickUtil.secondToTick(2));
    }

    private void initMordred(GameModeContent gameModeContent) {
        //악의 세력들을 보여주기
        showEvilMessage(MODRED, gameModeContent);
    }

    private void initAssassine(GameModeContent gameModeContent) {
        //악의 세력들을 보여주기
        showEvilMessage(ASSASSINE, gameModeContent);

    }

    private void initCervent(GameModeContent gameModeContent) {
        showPosition(ROLE_TYPE.CERVENT);
    }

    private void initMarline(GameModeContent gameModeContent) {
        //모드레드를 제외한 악의 세력 보여주기
        showMarlineMessage(gameModeContent);
    }

    private void initMorgana(GameModeContent gameModeContent) {
        //악의 세력 보여주기
        showEvilMessage(MORGANA, gameModeContent);

    }

    private void initOverone(GameModeContent gameModeContent) {

    }

    private void initPercival(GameModeContent gameModeContent) {
        showPosition(ROLE_TYPE.PERCIVAL);
        if(gameModeContent.characterList().size() == 0){
            return;
        }

        showPercivalMessage(gameModeContent);
    }
}
