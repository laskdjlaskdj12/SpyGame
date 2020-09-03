package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.vote.IVoteResultHandler;
import com.laskdjlaskdj12.spygame.content.vote.VoteContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class VoteCommand implements CommandExecutor, IVoteResultHandler {
    //Todo: 리팩토링 및 라이브 테스트 진행하기
    private final GameModeContent gameModeContent;
    private final JavaPlugin plugin;

    public VoteCommand(GameModeContent gameModeContent, JavaPlugin javaPlugin) {
        this.gameModeContent = gameModeContent;
        this.plugin = javaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 투표를 시작함
        List<ICharacter> characterList = gameModeContent.characterList();
        Player player = (Player) sender;

        VoteContent voteContent = new VoteContent(plugin, 10);
        voteContent.setResultHandler(this);
        voteContent.startVote(characterList, player);
        return true;
    }

    @Override
    public void result(VotingResult votingResult, Player voteStarter) {
        Bukkit.broadcastMessage(ChatColor.GREEN + "찬성 : " + ChatColor.WHITE + votingResult.getAiCount() + ChatColor.GREEN + " 반대 : " + ChatColor.WHITE + votingResult.getNayCount());
    }
}
