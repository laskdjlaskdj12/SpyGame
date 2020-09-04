package com.laskdjlaskdj12.spygame.sequence;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.vote.IVoteResultHandler;
import com.laskdjlaskdj12.spygame.content.vote.VoteContent;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
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

        System.out.println("current count : " + args.length);

        for (String arg : args) {
            System.out.println("args : " + arg);
        }

        for (ICharacter iCharacter :gameModeContent.characterList()){
            System.out.println("Character Name : " + iCharacter.getPlayer().getDisplayName());
        }

        ICharacter candidate = gameModeContent.findCharacterFromName(args[0]);

        if (candidate == null) {
            sender.sendMessage("선출하려는 대표자는 게임참여자가 아닙니다. 다시 체크해주세요");
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

        if(!gameModeContent.experditionContent().isExsist()){
            Bukkit.broadcastMessage(ChatColor.RED + "/원정 을 해주세요!! 원정 시작이 안되서 뽑히지 않았습니다.");
            return;
        }

        ICharacter candidate = gameModeContent.findCharacterFromPlayer(voteStarter);

        if (candidate == null) {
            Bukkit.broadcastMessage("게임 참여자가 아니여서 원정대장을 할수가 없습니다.");
            return;
        }

        if (votingResult.getAiCount() <= votingResult.getNayCount()) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "찬성 : " + ChatColor.WHITE + votingResult.getAiCount() + ChatColor.GREEN + " 반대 : " + ChatColor.WHITE + votingResult.getNayCount() + "로 " + voteStarter.getDisplayName() + "님이 원정대장으로 뽑히지 못했습니다.");
            return;
        }

        //Todo: 원정대장의 권한을 수여하는 부분은 GameModeContent로 넣을것
        Bukkit.broadcastMessage(ChatColor.GREEN + "찬성 : " + ChatColor.WHITE + votingResult.getAiCount() + ChatColor.GREEN + " 반대 : " + ChatColor.WHITE + votingResult.getNayCount() + "로 " + voteStarter.getDisplayName() + "님이 원정대장으로 뽑혔습니다.");

        if(candidate.getGameRole() == GAME_ROLE.EXCALIBUR_OWNER){
            gameModeContent.removeExcalibur(candidate);
        }

        //기존의 원정대장 찾기
        ICharacter leader = gameModeContent.findCharacterByGameRole(GAME_ROLE.EXPEDITION_LEAD);

        if (isFirstLeader(leader)){
            System.out.println("게임 최초인 원정대장이 선출되었음.");
            gameModeContent.awardExperditionLead(candidate);
            return;
        }

        if (isLeaderAlreadyExsist(leader, candidate)) {
            System.out.println("예전 라운드의 원정대장이 존재함.. 새로운 원정대장에게 인계");
            gameModeContent.changeExperditionlead(leader, candidate);
            gameModeContent.experditionContent().addExperditioner(candidate);
            return;
        }

        if (isLeaderSame(leader, candidate)) {
            System.out.println("선출된 원정대장이 저번라운드랑 동일함");

            //원정대원 선출권한만 줌
            gameModeContent.awardExperditionLeadWithoutExcalivur(candidate);
            gameModeContent.experditionContent().addExperditioner(candidate);
            return;
        }
    }

    private boolean isLeaderAlreadyExsist(ICharacter leader, ICharacter candidate) {
        return leader != null && leader.getPlayer() != candidate.getPlayer();
    }

    private boolean isLeaderSame(ICharacter leader, ICharacter candidate) {
        return leader != null && leader.getPlayer() == candidate.getPlayer();
    }

    private boolean isFirstLeader(ICharacter candidate){
        return candidate == null;
    }
}
