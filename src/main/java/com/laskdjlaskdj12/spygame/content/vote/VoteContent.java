package com.laskdjlaskdj12.spygame.content.vote;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.stream.Collectors;

public class VoteContent extends AVoteContent {

    public VoteContent(JavaPlugin plugin, int voteCountTime){
        super(plugin, voteCountTime);
    }

    @Override
    public void startVote(List<ICharacter> voterList, Player voteStarter){
        if (voterList == null) {
            voteStarter.sendMessage(ChatColor.RED + "게임이 시작되지 않았습니다. 게임을 먼저 시작해주세요");
            return;
        }

        //1. 현재 서버에 있는 사람들중에 나간 사람들 있는지 체크
        if (isCharacterExist(voterList, voteStarter.getWorld().getPlayers())) {
            voteStarter.sendMessage(ChatColor.RED + "게임도중에 퇴장한 참가자가 있습니다. 투표 진행이 불가능합니다.");
            return;
        }

        //2. 모든 사람에게 파란색 블록 및 빨간색 블록을 줌
        voterList.forEach(this::giveVoteItemToPlayer);

        //3. 서버에 있는 사람들에게 10초의 시간을 줌
        BukkitScheduler scheduler = voteStarter.getServer().getScheduler();

        bukkitTask = scheduler.runTaskTimer(plugin, () -> {
            if (VoteCountTime == 0) {
                VoteCountTime = TickUtil.secondToTick(3);
                Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
                collectVoteResult(voterList);
                return;
            }

            Bukkit.broadcastMessage(TickUtil.tickToSecond(VoteCountTime) + "초 남았습니다.");

            VoteCountTime -= TickUtil.secondToTick(1);;
        }, 0, 20L);
    }

    @Override
    public void collectVoteResult(List<ICharacter> characterList) {
        //4. 손에 블록으로 집계
        List<Boolean> voteResult = characterList.stream()
                .map(character -> character.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_BLOCK)
                .collect(Collectors.toList());

        VotingResult votingResult = votingTotal(voteResult);

        //5. 집계 내용을 모든 플레이어에게 표시함
        Bukkit.broadcastMessage(ChatColor.GREEN + "찬성 : " + ChatColor.WHITE + votingResult.getAiCount() + ChatColor.GREEN + " 반대 : " + ChatColor.WHITE + votingResult.getNayCount());
    }
}