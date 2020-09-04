package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.config.BlockConfig;
import com.laskdjlaskdj12.spygame.content.BlockContent;
import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.domain.ExperditionInfo;
import com.laskdjlaskdj12.spygame.domain.VoteInfo;
import com.laskdjlaskdj12.spygame.util.TickUtil;
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
    private final JavaPlugin plugin;
    public static int second = 0;
    public static int term = 3;
    public static int voteInfoListIndex = 0;
    public static List<VoteInfo> voteResult = new ArrayList<>();
    public static BukkitTask taskID;
    public static int voterCount = 0;

    public static BukkitTask waitTimerTask;
    public static int waitTimerCount = TickUtil.secondToTick(5);

    public static BukkitTask showWinGoodSideWaitTimerTask;
    public static int showWinGoodSideWaitTimerCount = TickUtil.secondToTick(5);

    public ShowVoteResultCommand(GameModeContent gameModeContent, JavaPlugin plugin) {
        this.gameModeContent = gameModeContent;
        this.plugin = plugin;
    }

    private void showWinGoodSide(int timerCount) {
        showWinGoodSideWaitTimerCount = TickUtil.secondToTick(timerCount);
        showWinGoodSideWaitTimerTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if(showWinGoodSideWaitTimerCount == 0){
                showWinGoodSideWaitTimerCount = TickUtil.secondToTick(5);
                Bukkit.getScheduler().cancelTask(showWinGoodSideWaitTimerTask.getTaskId());

                //투표결과 공개
                CharacterContent.showTitle(gameModeContent.characterList(), "선 세력이 원정에 성공.", "");

                //멀린암살 시퀸스로 바꾸기
                gameModeContent.startKillMarline();
                return;
            }

            showWinGoodSideWaitTimerCount -= TickUtil.secondToTick(1);
        }, 0, 20L);
    }

    private void nextShowTitleSleep(int timerCount) {
        waitTimerCount = TickUtil.secondToTick(timerCount);
        waitTimerTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if(waitTimerCount == 0){
                waitTimerCount = TickUtil.secondToTick(5);
                Bukkit.getScheduler().cancelTask(waitTimerTask.getTaskId());

                //만약 원정이 3승이거나 3패가 됬을경우 선의세력의 승리인지 악의세력의 승리인지 체크
                if (gameModeContent.getWinCount() == 3) {
                    //선의세력이 승리함
                    showWinGoodSide(5);
                } else if (gameModeContent.getLoseCount() == 3) {
                    //패배
                    gameModeContent.declareLose();
                } else{
                    // 원정이 끝났으므로 기존 원정 종료 및 새원정 시작
                    gameModeContent.experditionContent().stop();
                    gameModeContent.experditionContent().start();
                    CharacterContent.showTitle(gameModeContent.characterList(), "제 " + gameModeContent.experditionCount() + ChatColor.YELLOW + " 원정", "");
                }

                return;
            }

            waitTimerCount -= TickUtil.secondToTick(1);
        }, 0, 20L);
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
        taskID = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                //투표 결과 한개씩 공개
                if (second == term) {
                    plugin.getLogger().info(voteResult.size() + "개의 블록이 공개됩니다.");

                    showResultBlock(voteInfoListIndex);

                    showNext();

                    if (isEnd()) {
                        plugin.getLogger().info("투표결과 공개를 끝냅니다.");
                        end();
                    }

                    return;
                }

                plugin.getLogger().info("voteInfoListIndex : " + voteInfoListIndex + "\n" + "second : " + second);
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
        List<ItemStack> voteListBlock = Arrays.stream(BlockConfig.WOOL_COLOR_LIST).map(BlockContent::makeWOOLItemByColor).collect(Collectors.toList());

        //쇼로 보여줄 블록들을 섞어놓기
        Collections.shuffle(voteListBlock);

        if (gameModeContent.getActiveVoteResultBlock().size() == 0) {
            Bukkit.broadcastMessage(ChatColor.RED + "모두 투표를 하지 않아서 보여줄 블록드링 없습니다.");
        }

        Block block = gameModeContent.getActiveVoteResultBlock().get(showVoteInfoListIndex);
        block.setType(voteListBlock.get(second).getType());
        block.setData((byte) voteListBlock.get(second).getDurability());
    }

    private boolean isEnd() {
        plugin.getLogger().info("ActiveVoteResultBlockSize : " + gameModeContent.getActiveVoteResultBlock().size());
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
                gameModeContent.setLoseCount(gameModeContent.getLoseCount() + 1);
                CharacterContent.showTitle(gameModeContent.characterList(), "원정에 실패했습니다.", "");
            } else{
                gameModeContent.setWinCount(gameModeContent.getWinCount() + 1);
                CharacterContent.showTitle(gameModeContent.characterList(), "원정에 성공했습니다.", "");
            }
        } else {
            if (nayVoteInfo.size() >= 1) {
                gameModeContent.setLoseCount(gameModeContent.getLoseCount() + 1);
                CharacterContent.showTitle(gameModeContent.characterList(), "원정에 실패했습니다.", "");
            } else {
                gameModeContent.setWinCount(gameModeContent.getWinCount() + 1);
                CharacterContent.showTitle(gameModeContent.characterList(), "원정에 성공했습니다.", "");
            }
        }

        resetVoteTimer();

        nextShowTitleSleep(5);
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
