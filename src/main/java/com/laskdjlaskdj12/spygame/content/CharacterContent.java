package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.content.character.BasicCharacter;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CharacterContent {
    public static void teleportPlayer(ICharacter iCharacter, Location to) {
        to.setWorld(iCharacter.getPlayer().getWorld());
        iCharacter.getPlayer().teleport(to);
    }

    public static void showTitle(List<ICharacter> sender, String title, String subTitle) {
        for(ICharacter iCharacter : sender){
            iCharacter.getPlayer().sendTitle(title, subTitle, TickUtil.secondToTick(2), TickUtil.secondToTick(5), TickUtil.secondToTick(2));
        }
    }

    public static void shortShowTime(List<ICharacter> sender, String title, String subTitle) {
        for(ICharacter iCharacter : sender){
            iCharacter.getPlayer().sendTitle(title, subTitle, TickUtil.secondToTick(1), TickUtil.secondToTick(2), TickUtil.secondToTick(1));
        }
    }

    /*
        램덤으로 만든 Role와 유저와 플레이어에게 접목 시킴
     */

    public List<ICharacter> createCharacter(List<IRole> roleList, List<Player> players) {
        Collections.shuffle(players);

        return IntStream.range(0, roleList.size())
                .mapToObj(operand -> {
                    BasicCharacter basicCharacter = new BasicCharacter();
                    basicCharacter.setRole(roleList.get(operand));
                    basicCharacter.setPlayer(players.get(operand));
                    return basicCharacter;
                })
                .collect(Collectors.toList());
    }

    public static void removeItem(Player player, Material itemMaterial) {
        for (int i = 0; i < 35; i++) {
            ItemStack itemStack = player.getInventory().getItem(i);

            if (itemStack == null) {
                continue;
            }

            Bukkit.broadcastMessage("찾은 아이템 : " + itemStack.getType().name());
            if (itemStack.getType() == itemMaterial) {
                player.getInventory().removeItem(itemStack);
            }
        }
    }

    public static void removeItem(ICharacter character, Material itemMaterial) {
        for (int i = 0; i < 35; i++) {
            Player player = character.getPlayer();
            ItemStack itemStack = player.getInventory().getItem(i);

            if (itemStack == null) {
                continue;
            }

            if (itemStack.getType() == itemMaterial) {
                player.getInventory().removeItem(itemStack);
            }
        }
    }

    public static void addItem(ICharacter character, Material item) {
        character.getPlayer().getInventory().addItem(new ItemStack(item));
    }

    /**
     * Todo: 디버그용이므로 반드시 제거를 해야할것
     *
     * @param sender
     */
    public ICharacter createCharacterDebug(Player sender) {
        ICharacter iCharacter = new BasicCharacter();
        iCharacter.setPlayer(sender);

        return iCharacter;
    }
}
