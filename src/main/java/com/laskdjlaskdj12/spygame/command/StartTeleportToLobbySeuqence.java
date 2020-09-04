package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.config.ExpeditionConfig;
import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


public class StartTeleportToLobbySeuqence implements CommandExecutor {

    private final GameModeContent gameModeContent;
    private final JavaPlugin plugin;
    private final ShowVoteResultCommand showVoteResultCommand;

    public StartTeleportToLobbySeuqence(GameModeContent gameModeContent,
                                        JavaPlugin plugin, ShowVoteResultCommand showVoteResultCommand) {
        this.gameModeContent = gameModeContent;
        this.plugin = plugin;
        this.showVoteResultCommand = showVoteResultCommand;
    }

    public static BukkitTask bukkitTask;
    public static BukkitTask waitTimerTask;
    public static int timerCount = TickUtil.secondToTick(10);
    public static int waitTimerCount = TickUtil.secondToTick(5);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //투표결과 안된 사람들 모두 반대로 처리
        gameModeContent.experditionContent().collectMissingVote();

        //10초 타이머를 진행함
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        gameModeContent.characterList().forEach( iCharacter ->
                CharacterContent.removeItem(iCharacter, Material.GOLD_SWORD)
        );

        bukkitTask = scheduler.runTaskTimer(plugin, () -> {
            gameModeContent.experditionContent().setHorseSequence(true);
            try {
                if (timerCount == 0) {
                    timerCount = TickUtil.secondToTick(10);
                    Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());

                    Bukkit.broadcastMessage("메인장소로 다들 티피를 시킵니다.");
                    gameModeContent.experditionContent().teleportExperditionMembers(ExpeditionConfig.counsilLocation);
                    gameModeContent.experditionContent().setHorseSequence(false);

                    //5초 동안 Stop
                    waitUntil(5, sender, command, label, args);
                    return;
                }

                Bukkit.broadcastMessage(TickUtil.tickToSecond(timerCount) + "초 남았습니다.");

                //엑스칼리버가 칼을 들고있는지 체크
                ICharacter excaliburOwner = gameModeContent.findCharacterByGameRoleInExperditionaCharacter(GAME_ROLE.EXCALIBUR_OWNER);
                if (excaliburOwner == null) {
                    if(timerCount == TickUtil.secondToTick(10)){
                        Bukkit.broadcastMessage(ChatColor.RED + "엑스칼리버 소유자 없이 게임을 해서 엑스칼리버를 사용할수없습니다.");
                    }
                } else {
                    ItemStack handItem = excaliburOwner
                            .getPlayer()
                            .getInventory()
                            .getItemInMainHand();

                    if (handItem.getType() == Material.DIAMOND_SWORD) {
                        Bukkit.broadcastMessage("[테스트] 칼을 들고있으므로 타이머를 잠시 중지합니다.");
                        return;
                    }
                }

                timerCount -= TickUtil.secondToTick(1);
            }catch(Exception e){
                Bukkit.broadcastMessage(ChatColor.RED + "에러가 발생했습니다. \n");
                e.printStackTrace();
                Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
            }
        }, 0, 20L);

        //칼을 들고있다면 타이머 10초를 제거함
        return true;
    }

    private void waitUntil(int timerCount, CommandSender sender, Command command, String label, String[] args) {
        waitTimerCount = TickUtil.secondToTick(timerCount);
        waitTimerTask = Bukkit.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if(waitTimerCount == 0){
                waitTimerCount = TickUtil.secondToTick(5);
                Bukkit.getScheduler().cancelTask(waitTimerTask.getTaskId());

                //투표결과 공개
                showVoteResultCommand.onCommand(sender, command, label, args);
                return;
            }

            Bukkit.broadcastMessage("결과 공개까지 [ " + TickUtil.tickToSecond(waitTimerCount) + " ] 초");
            waitTimerCount -= TickUtil.secondToTick(1);
        }, 0, 20L);
    }
}
