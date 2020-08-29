package com.laskdjlaskdj12.spygame.sequence;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.vote.IVoteResultHandler;
import com.laskdjlaskdj12.spygame.content.vote.VoteContent;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

        if(candidate == null){
            Bukkit.broadcastMessage("게임 참여자가 아니여서 우너정대장을 할수가 없습니다.");
            return;
        }

        if (votingResult.getAiCount() <= votingResult.getNayCount()) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "찬성 : " + ChatColor.WHITE + votingResult.getAiCount() + ChatColor.GREEN + " 반대 : " + ChatColor.WHITE + votingResult.getNayCount() + "로 " + voteStarter.getDisplayName() + "님이 원정대장으로 뽑히지 못했습니다.");
            return;
        }

        //Todo: 원정대장의 권한을 수요하는 부분은 GameModeContent로 넣을것
        Bukkit.broadcastMessage(ChatColor.GREEN + "찬성 : " + ChatColor.WHITE + votingResult.getAiCount() + ChatColor.GREEN + " 반대 : " + ChatColor.WHITE + votingResult.getNayCount() + "로 " + voteStarter.getDisplayName() + "님이 원정대장으로 뽑혔습니다.");
        gameModeContent.giveExperditionLeadAuth(voteStarter, candidate);
    }
}
