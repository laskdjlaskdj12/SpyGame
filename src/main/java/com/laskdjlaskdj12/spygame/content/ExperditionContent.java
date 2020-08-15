package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.domain.ExperditionInfo;
import com.laskdjlaskdj12.spygame.domain.VoteInfo;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashSet;

public class ExperditionContent {

    private ExperditionInfo experditionInfo;
    private int experditionCount;

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
        this.experditionCount = 0;
        this.experditionInfo = new ExperditionInfo();
    }

    public void stopExperdition() {
        //Todo: 원정대의 정보들을 전부 초기화
        experditionInfo = null;
    }

    /**
     * 원정인원 추가
     */

    public void addExperditioner(ICharacter character) {
        experditionInfo.getApplyCharacters().add(character);
        experditionInfo.setApplyPlayerCount(experditionInfo.getApplyPlayerCount() + 1);
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
}
