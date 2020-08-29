package com.laskdjlaskdj12.spygame.sequence;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.vote.IVoteResultHandler;
import com.laskdjlaskdj12.spygame.content.vote.VoteContent;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ExperditionLeadSequence implements CommandExecutor, IVoteResultHandler {

    private final GameModeContent gameModeContent;
    private final JavaPlugin javaPlugin;

    public ExperditionLeadSequence(GameModeContent gameModeContent, JavaPlugin javaPlugin) {
        this.gameModeContent = gameModeContent;
        this.javaPlugin = javaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("/원정대장선정 <플레이어닉네임>");
            return true;
        }

        ICharacter candidate = gameModeContent.findCharacterFromName(args[0]);

        if (candidate == null) {
            sender.sendMessage(new StringBuilder()
                    .append(candidate)
                    .append(ChatColor.RED + "은 게임참여자가 아닙니다. 다시 체크해주세요")
                    .toString());

            return true;
        }

        //투표
        VoteContent voteContent = new VoteContent(javaPlugin, 10);
        voteContent.setResultHandler(this);
        voteContent.startVote(gameModeContent.characterList(), candidate.getPlayer());
        return true;
    }

    @Override
    public void result(VotingResult votingResult, Player voteStarter) {
        ICharacter candidate = gameModeContent.findCharacterFromPlayer(voteStarter);

        if (votingResult.getAiCount() <= votingResult.getNayCount()) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "찬성 : " + ChatColor.WHITE + votingResult.getAiCount() + ChatColor.GREEN + " 반대 : " + ChatColor.WHITE + votingResult.getNayCount() + "로 " + voteStarter.getDisplayName() + "님이 원정대장으로 뽑히지 못했습니다.");
            return;
        }

        Bukkit.broadcastMessage(ChatColor.GREEN + "찬성 : " + ChatColor.WHITE + votingResult.getAiCount() + ChatColor.GREEN + " 반대 : " + ChatColor.WHITE + votingResult.getNayCount() + "로 " + voteStarter.getDisplayName() + "님이 원정대장으로 뽑혔습니다.");

        //기존 원정대장에게 다이아몬드 도끼 제거
        Bukkit.broadcastMessage("기존의 원정대장의 권한을 회수합니다.");
        ICharacter experditionLead = gameModeContent.findCharacterByGameRole(GAME_ROLE.EXPEDITION_LEAD);
        experditionLead.getPlayer().getInventory().clear();

        //원정대장 뽑혔으니 새로 지정
        Bukkit.broadcastMessage("새 원정대장에게 권한을 수여합니다.");
        candidate.setGameRole(GAME_ROLE.EXPEDITION_LEAD);

        //엑스칼리버가 있는지 체크
        if (!gameModeContent.isExcaliberExsist()) {
            voteStarter.sendMessage("엑스칼리버가 이미 사용됬어서 엑스칼리버를 가지는 사람을 지정할수가없습니다.");
            return;
        }

        Bukkit.broadcastMessage("엑스칼리버를 회수합니다.");

        //다이아검을 갖고있는 사람들이 있는지 체크
        ICharacter excaliburOwner = gameModeContent.findCharacterByGameRole(GAME_ROLE.EXCALIBUR_OWNER);

        //다이아검 제거
        excaliburOwner.getPlayer().getInventory().clear();

        Bukkit.broadcastMessage("엑스칼리버를 수여할수있는 권한을 생성합니다.");
        //원정대장에게 다이아 도끼를 주기
        candidate.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_AXE));
    }
}
