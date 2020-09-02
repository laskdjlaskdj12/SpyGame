package com.laskdjlaskdj12.spygame.command;

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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


public class StartTeleportToLobbySeuqence implements CommandExecutor {

    private final GameModeContent gameModeContent;
    private final JavaPlugin plugin;

    public StartTeleportToLobbySeuqence(GameModeContent gameModeContent,
                                        JavaPlugin plugin) {
        this.gameModeContent = gameModeContent;
        this.plugin = plugin;
    }

    public static BukkitTask bukkitTask;
    public static int timerCount = TickUtil.secondToTick(10);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        //투표결과 안된 사람들 모두 반대로 처리
        gameModeContent.experditionContent().collectMissingVote();

        //10초 타이머를 진행함
        BukkitScheduler scheduler = player.getServer().getScheduler();

        bukkitTask = scheduler.runTaskTimer(plugin, () -> {
            if (timerCount == 0) {
                timerCount = TickUtil.secondToTick(10);
                Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());

                //Todo: 참가자들을 전부 메인 장소로 TP하는 코드 추가
                Bukkit.broadcastMessage("메인장소로 다들 티피를 시킵니다.");
                return;
            }

            Bukkit.broadcastMessage(TickUtil.tickToSecond(timerCount) + "초 남았습니다.");

            //엑스칼리버가 칼을 들고있는지 체크
            ICharacter excaliburOwner = gameModeContent.findCharacterByGameRole(GAME_ROLE.EXCALIBUR_OWNER);
            if(excaliburOwner == null){
                Bukkit.broadcastMessage(ChatColor.RED + "엑스칼리버 소유자 없이 게임을 해서 엑스칼리버를 사용할수없습니다.");
                Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());

                //Todo: 참가자들을 전부 메인 장소로 TP하는 코드 추가
                Bukkit.broadcastMessage("메인장소로 다들 티피를 시킵니다.");
                return;
            }

            ItemStack handItem = excaliburOwner
                    .getPlayer()
                    .getInventory()
                    .getItemInMainHand();

            if (handItem.getType() == Material.DIAMOND_SWORD){
                Bukkit.broadcastMessage("[테스트] 칼을 들고있으므로 타이머를 잠시 중지합니다.");
                return;
            }

            timerCount -= TickUtil.secondToTick(1);
        }, 0, 20L);

        //칼을 들고있다면 타이머 10초를 제거함
        return true;
    }
}
