package com.laskdjlaskdj12.spygame.sequence;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseLakeElfSequence implements CommandExecutor {

    private final GameModeContent gameModeContent;

    public ChooseLakeElfSequence(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //캐릭터들이 있는지 체크
        if (gameModeContent.characterList().isEmpty()) {
            sender.sendMessage("게임이 시작되지 않아서 호수의 요정 지정을 진행할수없습니다.");
            return true;
        }


        // 호수의 요정 선정
        chooseLakeElf();

        return false;
    }

    private void chooseLakeElf() {
        Bukkit.broadcastMessage("원정대장 제외한 모든 인원들에게 호수의 요정을 선택합니다.");
        List<ICharacter> characterList = gameModeContent.characterList()
                .stream()
                .filter(iCharacter -> iCharacter.getGameRole() != GAME_ROLE.EXPEDITION_LEAD)
                .collect(Collectors.toList());

        Collections.shuffle(characterList);
        ICharacter lakeElf = characterList.get(0);
        lakeElf.setGameRole(GAME_ROLE.LAKE_ELF);

        Bukkit.broadcastMessage(new StringBuilder().append("[")
                .append(ChatColor.YELLOW + lakeElf.getPlayer().getDisplayName())
                .append(ChatColor.WHITE + "]")
                .append(" 님이 ")
                .append(ChatColor.YELLOW + "호수의 요정 ")
                .append(ChatColor.WHITE + "이 되셨습니다.")
                .toString());
    }
}
