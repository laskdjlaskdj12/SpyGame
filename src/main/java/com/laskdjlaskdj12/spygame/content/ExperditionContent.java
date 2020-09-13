package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.config.ExpeditionConfig;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.domain.ExperditionInfo;
import com.laskdjlaskdj12.spygame.domain.VoteInfo;
import com.laskdjlaskdj12.spygame.exception.ExperditionNotStart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ExperditionContent {

    private ExperditionInfo experditionInfo;
    private int experditionRoundCount = 0;
    private boolean isLakeElfAvalityAvailable = true;
    private boolean isHorseSequence = false;

    public boolean isHorseSequence(){ return isHorseSequence;}
    public void setHorseSequence(boolean isHorseSequence){ this.isHorseSequence = isHorseSequence;}
    public boolean isExsist() {
        return experditionInfo != null;
    }
    public ExperditionInfo getExpedition() {
        return experditionInfo;
    }

    /**
     * 원정 관련된 초기화
     */

    public void init() {
        this.experditionRoundCount = 0;
        this.experditionInfo = null;
        this.isLakeElfAvalityAvailable = true;
        this.isHorseSequence = false;
    }

    public void start() {
        isLakeElfAvalityAvailable = true;
        this.experditionRoundCount += 1;
        this.experditionInfo = new ExperditionInfo();
        this.experditionInfo.setMaxExperditionMembersCount(ExpeditionConfig.roundByExperditionMemberCount.get(experditionRoundCount - 1));
    }

    public void stop() {
        Bukkit.broadcastMessage("제" + experditionRoundCount + " 번째 원정을 끝냅니다.");

        if (this.experditionRoundCount == 5) {

            // 6번 experdition으로 알려주고
            this.experditionRoundCount += 1;
        }

        //Todo: 원정대의 정보들을 전부 초기화
        experditionInfo = null;
    }

    /**
     * 원정인원 추가
     */

    @Nullable
    public ICharacter findExpeditionMember(ICharacter findCharacter) {

        if (experditionInfo.getApplyCharacters() == null) {
            return null;
        }

        return experditionInfo.getApplyCharacters()
                .stream()
                .filter(iCharacter -> iCharacter.getPlayer().getDisplayName().equals(findCharacter.getPlayer().getDisplayName())).findFirst()
                .orElse(null);
    }

    public boolean addExperditioner(ICharacter character) {
        if (findExpeditionMember(character) != null) {
            return false;
        }

        experditionInfo.getApplyCharacters().add(character);
        experditionInfo.setMaxExperditionMembersCount(roundByMaxMemberCount());
        return true;
    }

    public boolean removeExperditioner(ICharacter character){
        if (findExpeditionMember(character) == null) {
            return true;
        }

        experditionInfo.getApplyCharacters().remove(character);
        experditionInfo.setMaxExperditionMembersCount(roundByMaxMemberCount());

        return true;
    }

    public void addVote(ICharacter character, boolean isAi) {
        HashSet<VoteInfo> voteInfoList = experditionInfo.getVoteInfoList();
        voteInfoList.add(VoteInfo.builder()
                .isAi(isAi)
                .voteingCharacter(character)
                .build());
        experditionInfo.setVoteInfoList(voteInfoList);
    }

    @Nullable
    public ICharacter findExpertiesFromPlayer(Player player) {
        return experditionInfo.getApplyCharacters()
                .stream()
                .filter(character -> character.getPlayer().getDisplayName().equals(player.getDisplayName()))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public List<ICharacter> applyExperditioner() {
        return experditionInfo.getApplyCharacters();
    }

    public int roundCount() {
        return experditionRoundCount;
    }

    public boolean changeVote(ICharacter victimCharacter) {
        VoteInfo characterVote = experditionInfo.getVoteInfoList()
                .stream()
                .filter(voteInfo -> voteInfo.getVoteingCharacter() == victimCharacter)
                .findFirst()
                .orElse(null);

        if (characterVote == null) {
            System.out.println("투표한 캐릭터를 찾을수없습니다.");
            return false;
        }

        System.out.println("투표한 캐릭터 : " + characterVote.getVoteingCharacter().getPlayer().getDisplayName() + " 가 변됭되었습니다.");
        characterVote.setAi(!characterVote.isAi());
        return true;
    }

    public ExperditionInfo getExperditionInfo() {
        return experditionInfo;
    }

    /**
     * 원정이 시작되고 나서 반드시 체크를 할것
     *
     * @return
     */
    public boolean canAddMoreMember() throws ExperditionNotStart {

        if (experditionInfo == null) {
            throw new ExperditionNotStart("원정을 선언하지 않았습니다. 원정을 선언해주세요");
        }

        return experditionInfo.getMaxExperditionMembersCount() > experditionInfo.getApplyCharacters().size();
    }

    public int roundByMaxMemberCount() {
        return experditionInfo.getMaxExperditionMembersCount();
    }

    public int applyExperditionMemberCount() throws ExperditionNotStart {
        if (experditionInfo == null) {
            throw new ExperditionNotStart("원정을 선언하지 않았습니다. 원정을 선언해주세요");
        }

        return experditionInfo.getApplyCharacters().size();
    }

    public boolean isVoter(ICharacter compareCharacter) {
        if (experditionInfo == null) {
            return false;
        }

        for (VoteInfo voteInfo : experditionInfo.getVoteInfoList()) {
            if (voteInfo.getVoteingCharacter() == compareCharacter) {
                return true;
            }
        }

        return false;
    }

    public void collectMissingVote() {
        //누락된 투표가 있는지 체크
        if (!hasMissingVoter()) {
            return;
        }

        //누락된 투표들은 전부 반대처리
        experditionInfo.getApplyCharacters().stream()
                .filter(iCharacter -> !isVoter(iCharacter))
                .forEach(iCharacter -> addVote(iCharacter, false));
    }

    public List<VoteInfo> sortVoteList() {
        return experditionInfo.getVoteInfoList().stream()
                .sorted((o1, o2) -> Boolean.compare(!o1.isAi(), !o2.isAi()))
                .collect(Collectors.toList());
    }

    public boolean hasMissingVoter() {
        return getExperditionInfo().getVoteInfoList().size() < getExperditionInfo().getMaxExperditionMembersCount();
    }

    public int getMissingVoterCount() {
        return experditionInfo.getMaxExperditionMembersCount() - experditionInfo.getVoteInfoList().size();
    }

    public boolean isLakeElfAvalityAvailable() {
        return isLakeElfAvalityAvailable;
    }

    public void useLakeElfAvality(){
        if (!isLakeElfAvalityAvailable){
            return;
        }
        isLakeElfAvalityAvailable = false;
    }

    public void teleportExperditionMembers(Location to) {
        if(experditionInfo.getApplyCharacters() == null){
            return;
        }

        experditionInfo.getApplyCharacters().forEach(iCharacter -> CharacterContent.teleportPlayer(iCharacter, to));
    }
}
