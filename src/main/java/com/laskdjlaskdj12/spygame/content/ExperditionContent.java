package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.config.ExpeditionConfig;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.domain.ExperditionInfo;
import com.laskdjlaskdj12.spygame.domain.VoteInfo;
import com.laskdjlaskdj12.spygame.exception.ExperditionNotStart;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;

public class ExperditionContent {

    private ExperditionInfo experditionInfo;
    private int experditionRoundCount = 0;

    public boolean isExsist() {
        return experditionInfo != null;
    }

    public ExperditionInfo getExpedition() {
        return experditionInfo;
    }

    /**
     * 원정 관련된 초기화
     */

    public void init(){
        this.experditionRoundCount = 0;
        this.experditionInfo = new ExperditionInfo();
    }

    public void start() {
        this.experditionRoundCount += 1;
        this.experditionInfo = new ExperditionInfo();
        this.experditionInfo.setMaxExperditionMembersCount(ExpeditionConfig.roundByExperditionMemberCount.get(experditionRoundCount - 1));
    }

    public void stop() {
        if(this.experditionRoundCount == 5){

            // 6번 experdition으로 알려주고
            this.experditionRoundCount += 1;
        }

        //Todo: 원정대의 정보들을 전부 초기화
        experditionInfo = null;
    }

    /**
     * 원정인원 추가
     */

    public void addExperditioner(ICharacter character) {
        experditionInfo.getApplyCharacters().add(character);
        experditionInfo.setMaxExperditionMembersCount(experditionInfo.getMaxExperditionMembersCount() + 1);
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
                .filter(character -> character.getPlayer().getUniqueId() == player.getUniqueId())
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public List<ICharacter> applyExperditioner(){ return experditionInfo.getApplyCharacters(); }

    public int roundCount(){
        return experditionRoundCount;
    }

    public boolean changeVote(ICharacter victimCharacter) {
        VoteInfo characterVote = experditionInfo.getVoteInfoList()
                .stream()
                .filter(voteInfo -> voteInfo.getVoteingCharacter() == victimCharacter)
                .findFirst()
                .orElse(null);

        if(characterVote == null){
            return false;
        }

        characterVote.setAi(!characterVote.isAi());
        return true;
    }

    public ExperditionInfo getExperditionInfo(){ return experditionInfo;}

    /**
     * 원정이 시작되고 나서 반드시 체크를 할것
     * @return
     */
    public boolean canAddMoreMember() throws ExperditionNotStart {

        if(experditionInfo == null){
            throw new ExperditionNotStart("원정을 선언하지 않았습니다. 원정을 선언해주세요");
        }

        return experditionInfo.getMaxExperditionMembersCount() > experditionInfo.getApplyCharacters().size();
    }

    public int roundByMemberCount() {
        return experditionInfo.getMaxExperditionMembersCount();
    }

    public int applyExperditionMemberCount() throws ExperditionNotStart{
        if(experditionInfo == null){
            throw new ExperditionNotStart("원정을 선언하지 않았습니다. 원정을 선언해주세요");
        }

        return experditionInfo.getApplyCharacters().size();
    }
}
