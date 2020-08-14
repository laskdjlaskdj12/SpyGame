package com.laskdjlaskdj12.spygame.domain;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
public class ExperditionInfo {

    public ExperditionInfo(){
        applyCharacters = new ArrayList<>();
        applyPlayerCount = 0;
        voteInfoList = new HashSet<>();
    }

    /**
     * 원정에 참여한 사람
     * **/
    private List<ICharacter> applyCharacters;

    /**
     * 이번 원정의 최대 인원 명수
     */
    private int applyPlayerCount;

    /**
     *  원정 투표 결과
     */
    private HashSet<VoteInfo> voteInfoList;
}
