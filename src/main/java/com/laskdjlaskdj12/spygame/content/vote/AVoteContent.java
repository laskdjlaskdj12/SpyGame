package com.laskdjlaskdj12.spygame.content.vote;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public abstract class AVoteContent {

    public static BukkitTask bukkitTask = null;
    public static int VoteCountTime = 0;
    protected final JavaPlugin plugin;

    public AVoteContent(JavaPlugin plugin, int voteCountTime) {
        VoteCountTime = voteCountTime;
        this.plugin = plugin;
    }

    public abstract void startVote(List<ICharacter> voterList, Player voteStarter);

    public abstract void collectVoteResult(List<ICharacter> voterList);

    protected boolean isCharacterExist(List<ICharacter> iCharacters, List<Player> playerList) {
        //나간 사람들이 없는지 체크
        for (ICharacter character : iCharacters) {
            if (!iCharacters.contains(character.getPlayer())) return true;
        }

        return false;
    }

    protected void giveVoteItemToPlayer(ICharacter character) {
        character.getPlayer().getInventory().setItem(0, new ItemStack(Material.DIAMOND_BLOCK));
        character.getPlayer().getInventory().setItem(1, new ItemStack(Material.GOLD_BLOCK));
    }

    protected VotingResult votingTotal(List<Boolean> voteResult) {
        int aiCount = 0;
        int nayCount = 0;
        for (Boolean isAi : voteResult) {
            if (isAi) {
                aiCount += 1;
            } else {
                nayCount += 1;
            }
        }

        return VotingResult.builder()
                .aiCount(aiCount)
                .nayCount(nayCount)
                .build();
    }
}
