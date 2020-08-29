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
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


public class StartTeleportToLobbyCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;
    private final JavaPlugin plugin;

    public StartTeleportToLobbyCommand(GameModeContent gameModeContent,
                                       JavaPlugin plugin) {
        this.gameModeContent = gameModeContent;
        this.plugin = plugin;
    }

    public static BukkitTask bukkitTask;
    public static int timerCount = 10;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //현재 사람들이 3명이 있는지 체크
        sender.sendMessage("3명이 있는지 체크");

        Player player = (Player) sender;

        //10초 타이머를 진행함
        BukkitScheduler scheduler = player.getServer().getScheduler();

        bukkitTask = scheduler.runTaskTimer(plugin, () -> {
            if (timerCount == 0) {
                timerCount = TickUtil.secondToTick(3);
                Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());

                //Todo: 참가자들을 전부 메인 장소로 TP하는 코드 추가
                return;
            }

            Bukkit.broadcastMessage(TickUtil.tickToSecond(timerCount) + "초 남았습니다.");

            //엑스칼리버가 칼을 들고있는지 체크
            ICharacter kingCharacter = gameModeContent.findCharacterByGameRole(GAME_ROLE.EXCALIBUR_OWNER);
            if(kingCharacter == null){
                Bukkit.broadcastMessage(ChatColor.RED + "왕 없이 게임을 해서 ");
                return;
            }

            ItemStack handItem = kingCharacter.getPlayer()
                    .getInventory()
                    .getItemInMainHand();

            if (handItem.getType() == Material.DIAMOND_SWORD){
                player.sendMessage("[테스트] 칼을 들고있으므로 타이머를 잠시 중지합니다.");
                return;
            }

            timerCount -= TickUtil.secondToTick(1);
        }, 0, 20L);

        //칼을 들고있다면 타이머 10초를 제거함
        return true;
    }
}
