package com.laskdjlaskdj12.spygame.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class VotingResult {
    private int aiCount;
    private int nayCount;
}
