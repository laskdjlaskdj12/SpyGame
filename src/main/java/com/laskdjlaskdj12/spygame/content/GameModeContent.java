package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.config.Admin;
import com.laskdjlaskdj12.spygame.config.BlockConfig;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
    private final GameRoleContent gameRoleContent;
    private List<ICharacter> characterList = new ArrayList<>();
    private List<Block> voteResultBlock = new ArrayList<>();
    private final List<Block> activeVoteResultBlock = new ArrayList<>();
    private boolean excaliberExsist = true;
    private int winCount = 0;
    private int loseCount = 0;
    private boolean collectVoteBlock = false;
    private boolean isDebugMod = true;

    public void setCharacterList(List<ICharacter> characterList) {
        this.characterList = characterList;
    }

    public GameModeContent(ExperditionContent experditionContent, GameRoleContent gameRoleContent) {
        this.experditionContent = experditionContent;
        this.gameRoleContent = gameRoleContent;
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
                .findAny()
                .orElse(null);
    }

    @Nullable
    public ICharacter findCharacterFromName(String name) {
        return characterList.stream()
                .filter(character -> character.getPlayer().getDisplayName().equals(name))
                .findAny()
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

    public GameRoleContent gameRoleContent() { return gameRoleContent;}

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
            if (iCharacter.getRole().roleType().equals(roleType)) {
                return iCharacter;
            }
        }

        return null;
    }

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
        candidate.getPlayer().getInventory().setItem(1, new ItemStack(Material.DIAMOND_PICKAXE));
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

    public void setPickExperditionMemberCount(int pickMemberCount) {
        this.experditionContent.getExperditionInfo().setMaxExperditionMembersCount(pickMemberCount);
    }

    public void changeExperditionlead(ICharacter leader, ICharacter candidate){
        gameRoleContent.transitionToExperditionLead(leader, candidate, isExcaliberExsist());
    }

    public void awardExperditionLead(ICharacter candidate) {
        gameRoleContent.awardExperditionLead(candidate);
        experditionContent.addExperditioner(candidate);
    }

    public void removeExcalibur(ICharacter iCharacter){
        iCharacter.setGameRole(GAME_ROLE.NONE);
        CharacterContent.removeItem(iCharacter, Material.DIAMOND_SWORD);
    }

    public void awardExcalibur(ICharacter awardedOwner){
        ICharacter beforeOwner = findCharacterByGameRole(GAME_ROLE.EXCALIBUR_OWNER);

        if(beforeOwner == null){
            gameRoleContent.awardExcaliburOwner(awardedOwner);
            return;
        }

        gameRoleContent.transitionToExcaliburOwner(beforeOwner, awardedOwner);
    }

    public void clearGame(){

        //아이템들 모두 초기화
        characterList.forEach(iCharacter -> iCharacter.getPlayer().getInventory().clear());

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

        if(collectVoteBlock){
            collectVoteBlock = false;
        }

        characterList.clear();
        voteResultBlock.clear();
        activeVoteResultBlock.clear();
        excaliberExsist = true;
        winCount = 0;
        loseCount = 0;
        collectVoteBlock = false;
        isDebugMod = true;

        //expedition clear
        experditionContent.init();
    }

    public void startKillMarline() {
        //모든 사람들에게 인벤토리에 있는 아이템 제거
        characterList
                .stream()
                .forEach(character -> character.getPlayer().getInventory().clear());

        //어쌔신에게 다이아 칼을 줌
        ICharacter assassine = findCharacterByRole(ROLE_TYPE.ASSASSINE);

        if(assassine == null){
            Bukkit.broadcastMessage(ChatColor.RED + "어쌔신을 찾을수없습니다!! 혹시 어쌔신분이 접속했는지 체크 해주세요");
            return;
        }

        CharacterContent.addItem(assassine, Material.IRON_SWORD);

        characterList
                .stream()
                .forEach(character -> character.getPlayer().sendTitle(ChatColor.RED + "어쌔신이 멀린을 지목시간이 왔습니다.", "", 20, 40, 20));
    }

    public void declareWin(){
        characterList
                .forEach(iCharacter -> iCharacter
                        .getPlayer()
                        .sendTitle("선의 세력 " + ChatColor.GREEN + "승리",
                                "",
                                TickUtil.secondToTick(2),
                                TickUtil.secondToTick(4),
                                TickUtil.secondToTick(2)));

        characterList.forEach(iCharacter ->
                RestContent.sendPlayerWin(iCharacter.getPlayer()));

        clearGame();
    }

    public void declareLose(){
        characterList
                .forEach(iCharacter -> iCharacter
                        .getPlayer()
                        .sendTitle(ChatColor.RED + "악의 세력 " + ChatColor.WHITE + "승리",
                                "",
                                TickUtil.secondToTick(2),
                                TickUtil.secondToTick(4),
                                TickUtil.secondToTick(2)));

        characterList.forEach(iCharacter ->
                RestContent.sendPlayerLose(iCharacter.getPlayer()));

        clearGame();
    }

    public void useExcaliber(ICharacter attacker, ICharacter victim) {
        experditionContent.changeVote(victim);

        //엑스칼리버 무력화
        disableExcalibur(attacker);
    }

    private void disableExcalibur(ICharacter atatcker){
        excaliberExsist = false;
        gameRoleContent.removeExcaliburOwner(atatcker);
    }

    public void awardExperditionLeadWithoutExcalivur(ICharacter candidate) {
        gameRoleContent.awardExperditionLeadWithoutExcalivur(candidate);
    }

    public void activeReleaseMode(){
        isDebugMod = false;
    }

    public void activeDebugMode(){
        isDebugMod = true;
    }

    public boolean isDebugMod(){
        return isDebugMod;
    }

    public ICharacter findLakeElfCharacter(){
        return characterList.stream().filter(ICharacter::isLakeElf)
                .findAny()
                .orElse(null);
    }

    public void makeLakeElf(ICharacter character) {
        character.setLakeElf(true);
    }

    public void changeLakeElf(ICharacter beforeLakeElf, ICharacter currentLakeElf) {
        beforeLakeElf.setLakeElf(false);
        currentLakeElf.setLakeElf(true);
    }

    public void removeLakeElfEffect(ICharacter lakeElf) {
        lakeElf.setLakeElf(false);
    }

    public ICharacter findCharacterByGameRoleInExperditionaCharacter(GAME_ROLE gameRole) {
        if (!experditionContent.isExsist()){
            return null;
        }

        return experditionContent.applyExperditioner()
                .stream().filter(iCharacter -> iCharacter.getGameRole() == GAME_ROLE.EXCALIBUR_OWNER)
                .findAny()
                .orElse(null);
    }

    public boolean isAdmin(Player player) {
        String result = Arrays.stream(Admin.ADMIN_LIST).filter(s -> player.getDisplayName().equals(s))
                .findAny()
                .orElse(null);

        return result != null;
    }
}
