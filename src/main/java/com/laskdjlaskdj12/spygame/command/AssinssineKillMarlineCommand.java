package com.laskdjlaskdj12.spygame.command;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AssinssineKillMarlineCommand implements CommandExecutor {

    private final GameModeContent gameModeContent;

    public AssinssineKillMarlineCommand(GameModeContent gameModeContent) {
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //모든 사람들에게 인벤토리에 있는 아이템 제거
        gameModeContent.characterList()
                .stream()
                .forEach(character -> character.getPlayer().getInventory().clear());

        //어쌔신에게 다이아 칼을 줌
        ICharacter assassine = gameModeContent.findCharacterByRole(ROLE_TYPE.ASSASSINE);

        if(assassine == null){
            sender.sendMessage(ChatColor.RED + "어쌔신을 찾을수없습니다!! 혹시 어쌔신분이 접속했는지 체크 해주세요");
            return true;
        }

        assassine.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));

        gameModeContent.characterList()
                .stream()
                .forEach(character -> character.getPlayer().sendTitle(ChatColor.RED + "어쌔신의 멀린 지목시간이 왔습니다.", "", 20, 40, 20));
        return true;
    }
}
