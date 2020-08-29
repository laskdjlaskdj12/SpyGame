package com.laskdjlaskdj12.spygame.content.vote;

import com.laskdjlaskdj12.spygame.domain.VoteInfo;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import org.bukkit.entity.Player;

import java.util.List;

public interface IVoteResultHandler {
    void result(VotingResult votingResult, Player voteStarter);
}
