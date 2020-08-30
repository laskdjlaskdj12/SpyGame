package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Todo 디버깅 커맨드 이므로 나중에 지워야함
 */
public class ShowExpeditionVoteCommand implements CommandExecutor {
    private final ExperditionContent experditionContent;

    public ShowExpeditionVoteCommand(GameModeContent gameModeContent) {
        this.experditionContent = gameModeContent.experditionContent();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (experditionContent == null) {
            sender.sendMessage("원정이 만들어지지 않아서 결과를 볼수가 없습니다.");
            return false;
        }

        experditionContent.getExpedition()
                .getVoteInfoList()
                .stream()
                .map(voteInfo -> voteInfo.getVoteingCharacter()
                        .getPlayer()
                        .getName() + "님의 투표 : " + (voteInfo.isAi() ? "찬성" : "반대"))
                .forEach(sender::sendMessage);

        return true;
    }
}
