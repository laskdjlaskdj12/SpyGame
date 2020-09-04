package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.config.BlockConfig;
import com.laskdjlaskdj12.spygame.content.BlockContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.domain.ExperditionInfo;
import com.laskdjlaskdj12.spygame.domain.VoteInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
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
    public static List<VoteInfo> voteResult = new ArrayList<>();
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

        World world = Bukkit.getServer().getWorlds().get(0);
        voterCount = experditionInfo.getVoteInfoList().size();
        gameModeContent.loadVoteResultBlock(world, voterCount);

        if (experditionInfo == null) {
            sender.sendMessage("투표가 완료되지 않아서 개표 진행이 불가능합니다.");
            return true;
        }

        voteResult = gameModeContent.experditionContent().sortVoteList();
        taskID = Bukkit.getScheduler().runTaskTimer(javaPlugin, () -> {
                //투표 결과 한개씩 공개
                if (second == term) {
                    javaPlugin.getLogger().info(voteResult.size() + "개의 블록이 공개됩니다.");

                    showResultBlock(voteInfoListIndex);

                    showNext();

                    if (isEnd()) {
                        javaPlugin.getLogger().info("투표결과 공개를 끝냅니다.");
                        end();
                    }

                    return;
                }

                javaPlugin.getLogger().info("voteInfoListIndex : " + voteInfoListIndex + "\n" + "second : " + second);
                showRandomBlock(voteInfoListIndex, second);
                second += 1;
        }, 0, 20);


        //애니메이션으로 투표결과를 보여줌
        return true;
    }

    private void showResultBlock(int showVoteInfoListIndex) {
        VoteInfo voteInfo = voteResult.get(showVoteInfoListIndex);
        Block block = gameModeContent.getActiveVoteResultBlock().get(showVoteInfoListIndex);

        if (voteInfo.isAi()) {
            block.setType(Material.SEA_LANTERN);
        } else {
            block.setType(Material.BEACON);
        }
    }

    private void showRandomBlock(int showVoteInfoListIndex, int second) {
        //보여주는 블록 인덱스
        List<ItemStack> voteListBlock = Arrays.stream(BlockConfig.MATERIAL_WOOL_COLOR_LIST).map(BlockContent::makeWOOLColor).collect(Collectors.toList());

        //쇼로 보여줄 블록들을 섞어놓기
        Collections.shuffle(voteListBlock);

        if (gameModeContent.getActiveVoteResultBlock().size() == 0) {
            Bukkit.broadcastMessage(ChatColor.RED + "모두 투표를 하지 않아서 보여줄 블록드링 없습니다.");
        }

        Block block = gameModeContent.getActiveVoteResultBlock().get(showVoteInfoListIndex);
        Material material = voteListBlock.get(second).getType();

        //블록들을 변경하기
        block.setType(material);
    }

    private boolean isEnd() {
        javaPlugin.getLogger().info("ActiveVoteResultBlockSize : " + gameModeContent.getActiveVoteResultBlock().size());
        return gameModeContent.getActiveVoteResultBlock().size() == voteInfoListIndex;
    }

    private void end() {
        Bukkit.getScheduler().cancelTask(taskID.getTaskId());
        voteResult.forEach(voteInfo -> System.out.println("isVoteAi : " + voteInfo.isAi()));

        //투표결과에 따라 원정결과 업데이트
        List<VoteInfo> nayVoteInfo = voteResult.stream()
                .filter(voteInfo -> !voteInfo.isAi())
                .collect(Collectors.toList());

        //4회차인지 체크
        if (gameModeContent.experditionCount() >= 4) {
            if (nayVoteInfo.size() >= 2) {
                Bukkit.broadcastMessage("원정이 실패했습니다.");
                gameModeContent.setLoseCount(gameModeContent.getLoseCount() + 1);
            } else{
                Bukkit.broadcastMessage("원정이 승리했습니다.");
                gameModeContent.setWinCount(gameModeContent.getWinCount() + 1);
            }
        } else {
            if (nayVoteInfo.size() >= 1) {
                Bukkit.broadcastMessage("원정이 실패했습니다.");
                gameModeContent.setLoseCount(gameModeContent.getLoseCount() + 1);
            } else {
                Bukkit.broadcastMessage("원정이 승리했습니다.");
                gameModeContent.setWinCount(gameModeContent.getWinCount() + 1);
            }
        }

        resetVoteTimer();

        //만약 원정이 3승이거나 3패가 됬을경우 선의세력의 승리인지 악의세력의 승리인지 체크
        if (gameModeContent.getWinCount() == 3) {
            //선의세력이 승리함
            Bukkit.broadcastMessage("선의세력이 원정에 성공했습니다!");

            //멀린암살
            gameModeContent.startKillMarline();
        } else if (gameModeContent.getLoseCount() == 3) {
            //패배
            gameModeContent.declareLose();
        } else{
            // 원정이 끝났으므로 기존 원정 종료 및 새원정 시작
            gameModeContent.experditionContent().stop();
            gameModeContent.experditionContent().start();
        }
    }

    private void resetVoteTimer() {
        second = 0;
        voteInfoListIndex = 0;
        taskID = null;
    }

    private void showNext() {
        second = 0;
        voteInfoListIndex += 1;
    }
}
