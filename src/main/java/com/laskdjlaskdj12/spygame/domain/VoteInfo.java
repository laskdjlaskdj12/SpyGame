package com.laskdjlaskdj12.spygame.domain;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteInfo {
    private ICharacter voteingCharacter;
    private boolean isAi;
}
