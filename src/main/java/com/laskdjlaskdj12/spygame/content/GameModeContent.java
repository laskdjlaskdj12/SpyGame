package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.command.cheat.CollectVoteResultBlock;
import com.laskdjlaskdj12.spygame.config.BlockConfig;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.vote.VoteContent;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameModeContent {

    //Todo: 리팩토링 할것
    private final ExperditionContent experditionContent;
    private List<ICharacter> characterList = new ArrayList<>();
    private List<Block> voteResultBlock = new ArrayList<>();
    private List<Block> activeVoteResultBlock = new ArrayList<>();
    private boolean excaliberExsist = true;
    private int winCount = 0;
    private int loseCount = 0;

    private boolean collectVoteBlock = false;

    public GameModeContent(ExperditionContent experditionContent) {
        this.experditionContent = experditionContent;
    }

    public void saveCharacter(List<ICharacter> characters) {
        characterList = characters;
    }

    public List<ICharacter> characterList() {
        return characterList;
    }

    public void loadVoteResultBlock(World world, int voterCount) {
        this.voteResultBlock = Arrays.asList(BlockConfig.VoteBlockCordinate).stream()
                .map(cordinate -> world.getBlockAt(cordinate.getX(),
                        cordinate.getY(),
                        cordinate.getZ()))
                .collect(Collectors.toList());

        activeVoteResultBlock.clear();

        for (int i = 0; i < voterCount; i++) {
            activeVoteResultBlock.add(this.voteResultBlock.get(i));
        }
    }

    @Nullable
    public ICharacter findCharacterFromPlayer(Player player) {
        return characterList.stream().filter(character -> character.getPlayer() == player)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public ICharacter findCharacterFromName(String name) {
        return characterList.stream()
                .filter(character -> character.getPlayer().getDisplayName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public int experditionCount() {
        return experditionContent.roundCount();
    }

    /**
     * Todo: 디버그용으로 나중에 삭제
     */
    public void addCharacterDebug(ICharacter character) {
        characterList.add(character);
    }

    public ExperditionContent experditionContent() {
        return experditionContent;
    }

    @Nullable
    public ICharacter findCharacterByGameRole(GAME_ROLE gameRole) {
        for (ICharacter iCharacter : characterList) {
            if (iCharacter.getGameRole() == gameRole) {
                return iCharacter;
            }
        }

        return null;
    }

    @Nullable
    public ICharacter findCharacterByRole(ROLE_TYPE roleType){
        for (ICharacter iCharacter : characterList) {
            if (iCharacter.getRole().name() == roleType.name()) {
                return iCharacter;
            }
        }

        return null;    }

    public void deActiveVoteBlockSet() {
        collectVoteBlock = false;
    }

    public void activeVoteBlockSet() {
        collectVoteBlock = true;
    }

    public boolean getCollectVoteBlock() {
        return collectVoteBlock;
    }

    public List<Block> getActiveVoteResultBlock() {
        return activeVoteResultBlock;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public int getWinCount(){
        return winCount;
    }

    public void setWinCount(int count){
        winCount = count;
    }

    public void setLoseCount(int count){
        loseCount = count;
    }

    public boolean isExcaliberExsist() {
        return excaliberExsist;
    }

    public void setExcaliberExsist(boolean exsist){
        excaliberExsist = exsist;
    }

    public void giveExperditionLeadAuth(Player voteStarter, ICharacter candidate) {
        //기존 원정대장에게 다이아몬드 도끼 제거
        Bukkit.broadcastMessage("기존의 원정대장의 권한을 회수합니다.");
        ICharacter beforeExperditionLead = findCharacterByGameRole(GAME_ROLE.EXPEDITION_LEAD);
        if(beforeExperditionLead != null) {
            beforeExperditionLead.getPlayer().getInventory().clear();
        }

        //원정대장 뽑혔으니 새로 지정
        Bukkit.broadcastMessage("새 원정대장에게 권한을 수여하였습니다.");
        candidate.setGameRole(GAME_ROLE.EXPEDITION_LEAD);

        //엑스칼리버가 있는지 체크
        if (!isExcaliberExsist()) {
            voteStarter.sendMessage("엑스칼리버가 이미 사용됬어서 엑스칼리버를 가지는 사람을 지정할수가없습니다.");
            return;
        }


        //다이아검을 갖고있는 사람들이 있는지 체크
        ICharacter excaliburOwner = findCharacterByGameRole(GAME_ROLE.EXCALIBUR_OWNER);

        if(excaliburOwner != null) {
            Bukkit.broadcastMessage("엑스칼리버를 회수합니다.");

            //다이아검 제거
            excaliburOwner.getPlayer().getInventory().clear();
        }

        Bukkit.broadcastMessage("엑스칼리버를 수여할수있는 권한을 생성합니다.");

        //원정대장에게 다이아 도끼를 주기
        candidate.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
    }

    public List<ICharacter> evilCharacters() {
        return characterList.stream().filter(character -> character.getRole().roleType() == ROLE_TYPE.ASSASSINE ||
                character.getRole().roleType() == ROLE_TYPE.MORGANA ||
                character.getRole().roleType() == ROLE_TYPE.MODRED).collect(Collectors.toList());
    }

    @Nullable
    public ICharacter findCharacterFromRole(ROLE_TYPE roleType) {
        if (characterList.size() == 0) {
            return null;
        }

        return characterList.stream()
                .filter(character -> character.getRole().roleType() == roleType)
                .findFirst()
                .orElse(null);
    }

    public void clearGame(){

        //투표블록 전부 데이터 초기화
        if(!this.voteResultBlock.isEmpty()){
            this.voteResultBlock.forEach(block -> block.setType(Material.AIR));
        }

        //active블록결과 초기화
        if(!activeVoteResultBlock.isEmpty()){
            this.activeVoteResultBlock.clear();
        }

        //active 블록 결과 초기화
        if(!excaliberExsist){
            excaliberExsist = true;
        }

        //승리카운트 초기화
        if(winCount != 0){
            winCount = 0;
        }

        //승리카운트 초기화
        if(loseCount != 0){
            loseCount = 0;
        }

        //expedition clear
        experditionContent.init();
    }
}
