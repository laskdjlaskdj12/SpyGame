package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.config.BlockConfig;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.domain.ExperditionInfo;
import com.laskdjlaskdj12.spygame.domain.VoteInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShowVoteResultCommand implements CommandExecutor {

    //Todo: 리팩토링 할것
    private final GameModeContent gameModeContent;
    private final JavaPlugin javaPlugin;
    public static int second = 0;
    public static int term = 3;
    public static int voteInfoListIndex = 0;
    public static List<VoteInfo> voteInfoList = new ArrayList<>();
    public static BukkitTask taskID;
    public static int voterCount = 0;
    public ShowVoteResultCommand(GameModeContent gameModeContent, JavaPlugin javaPlugin) {
        this.gameModeContent = gameModeContent;
        this.javaPlugin = javaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 투표결과 블록들을 3초마다 한번씩 보여주기
        ExperditionContent experditionContent = gameModeContent.experditionContent();
        ExperditionInfo experditionInfo = experditionContent.getExperditionInfo();

        voterCount = experditionInfo.getVoteInfoList().size();
        Player player = (Player)sender;
        gameModeContent.loadVoteResultBlock(player.getWorld(), voterCount);

        if (experditionInfo == null) {
            sender.sendMessage("투표가 완료되지 않아서 개표 진행이 불가능합니다.");
            return true;
        }

        voteInfoList = experditionInfo.getVoteInfoList().stream()
                .sorted((o1, o2) -> Boolean.compare(o1.isAi(), o2.isAi()))
                .collect(Collectors.toList());

        taskID = Bukkit.getScheduler().runTaskTimer(javaPlugin, () -> {

            //투표 결과 한개씩 공개
            if (second == term) {
                javaPlugin.getLogger().info( voteInfoList + "개의 블록이 공개됩니다.");

                showResultBlock(voteInfoListIndex);

                showNext();

                if(isEnd()){
                    javaPlugin.getLogger().info("투표결과를 끝냅니다.");
                    end();
                }

                return;
            }

            javaPlugin.getLogger().info("voteInfoListIndex : " + voteInfoListIndex  + "\n" + "second : " + second);
            showRandomBlock(voteInfoListIndex, second);
            second += 1;

        }, 0, 20);


        //애니메이션으로 투표결과를 보여줌
        return true;
    }

    private void showResultBlock(int showVoteInfoListIndex) {
        VoteInfo voteInfo = voteInfoList.get(showVoteInfoListIndex);
        Block block = gameModeContent.getActiveVoteResultBlock().get(showVoteInfoListIndex);

        if (voteInfo.isAi()) {
            block.setType(Material.WHITE_WOOL);
        } else {
            block.setType(Material.BLACK_WOOL);
        }
    }

    private void showRandomBlock(int showVoteInfoListIndex, int second) {
        //보여주는 블록 인덱스
        List<Material> voteListBlock = Arrays.asList(BlockConfig.AnimatedBlockMaterialList);

        //쇼로 보여줄 블록들을 섞어놓기
        Collections.shuffle(voteListBlock);

        Block block = gameModeContent.getActiveVoteResultBlock().get(showVoteInfoListIndex);
        Material material = voteListBlock.get(second);

        //블록들을 변경하기
        block.setType(material);
    }

    private boolean isEnd(){
        javaPlugin.getLogger().info("ActiveVoteResultBlockSize : " + gameModeContent.getActiveVoteResultBlock().size());
        return gameModeContent.getActiveVoteResultBlock().size() == voteInfoListIndex;
    }

    private void end() {
        Bukkit.getScheduler().cancelTask(taskID.getTaskId());

        second = 0;
        voteInfoListIndex = 0;
        taskID = null;
        voteInfoList.clear();
    }

    private void showNext() {
        //만약 모든 블록이 보여줬다면 스케줄링을 취소
        second = 0;
        voteInfoListIndex += 1;
    }
}
