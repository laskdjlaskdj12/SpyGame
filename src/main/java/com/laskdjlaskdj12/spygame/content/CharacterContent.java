package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.content.character.BasicCharacter;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CharacterContent {
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

    public static void removeItem(ICharacter character, Material itemMaterial){
        for (int i = 0; i < 10; i++){
            ItemStack itemStack = character.getPlayer().getInventory().getItem(i);

            if(itemStack.getType() == itemMaterial){
                itemStack.setType(Material.AIR);
            }
        }
    }

    public static void addItem(ICharacter character, Material book) {
        for (int i = 0; i < 10; i++){
            ItemStack itemStack = character.getPlayer().getInventory().getItem(i);

            if(itemStack.getType() == Material.AIR){
                itemStack.setType(book);
            }
        }
    }

    /**
     * Todo: 디버그용이므로 반드시 제거를 해야할것
     * @param sender
     */
    public ICharacter createCharacterDebug(Player sender) {
        ICharacter iCharacter = new BasicCharacter();
        iCharacter.setPlayer(sender);

        return iCharacter;
    }
}
