package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CollectVoteResultBlock implements CommandExecutor {

    private final GameModeContent gameModeContent;

    public CollectVoteResultBlock(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        gameModeContent.activeVoteBlockSet();
        sender.sendMessage("투표 결과 블록 수집 모드를 active합니다.");
        return true;
    }
}
