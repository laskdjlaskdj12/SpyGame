package com.laskdjlaskdj12.spygame.command.cheat;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.ExperditionContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.factory.RoleFactory;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceCreatePacificCharacterCommand implements CommandExecutor {

    private final CharacterContent characterContent;
    private final GameModeContent gameModeContent;

    public ForceCreatePacificCharacterCommand(GameModeContent gameModeContent) {
        this.characterContent = gameModeContent.getCharacterContent();
        this.gameModeContent = gameModeContent;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("[테스트] 콘솔로 치트코드를 조작할수없습니다.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage("[테스트] /지정캐릭터추가 <닉네임> <캐릭터영어이름>");
            return true;
        }

        Player targetPlayer = sender.getServer().getPlayerExact(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(args[0] + "님을 찾을수가없습니다.");
            return true;
        }

        ROLE_TYPE changeType = ROLE_TYPE.findRoleByName(args[1]);
        if (changeType == null) {
            sender.sendMessage("잘못된 ROLE 입니다.");
            return true;
        }

        ICharacter iCharacter = characterContent.createCharacterDebug(targetPlayer);
        iCharacter.setRole(RoleFactory.createRole(changeType));
        gameModeContent.addCharacterDebug(iCharacter);

        sender.sendMessage("[테스트] : " + targetPlayer.getDisplayName() + "님을 " + changeType.nameKR + " 로 추가했습니다.");
        return true;
    }
}