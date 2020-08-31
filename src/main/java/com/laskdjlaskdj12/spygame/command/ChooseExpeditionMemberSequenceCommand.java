package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.MessageContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChooseExpeditionMemberSequenceCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;
    private final MessageContent messageContent;

    public ChooseExpeditionMemberSequenceCommand(GameModeContent gameModeContent,
                                                 MessageContent messageContent) {
        this.gameModeContent = gameModeContent;
        this.messageContent = messageContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //원정대 멤버 선발
        int pickMemberCount = gameModeContent.experditionContent().roundByMemberCount();
        gameModeContent.setPickExperditionMemberCount(pickMemberCount);

        //엑스칼리버가 이미 있는지 체크
        if(gameModeContent.isExcaliberExsist()){
            ICharacter character = gameModeContent.findCharacterByGameRole(GAME_ROLE.EXCALIBUR_OWNER);
            boolean addedComplete = gameModeContent.experditionContent().addExperditioner(character);

            if(!addedComplete){
                messageContent.sendTitleToAll("[에러]", "엑스칼리버 사람을 원정대원으로 추가하는데 실패했습니다.");
            }

            messageContent.sendTitleToAll("원정대원 선발", "엑스칼리버 소유자를 제외한 " + ChatColor.GREEN + (pickMemberCount - 1) + ChatColor.WHITE + " 명의 원정대원 뽑습니다. ");
        } else{
            messageContent.sendTitleToAll("원정대원 선발", ChatColor.GREEN + String.valueOf(pickMemberCount - 1) + ChatColor.WHITE + " 명의 원정대원 뽑습니다. ");
        }

        return true;
    }
}
