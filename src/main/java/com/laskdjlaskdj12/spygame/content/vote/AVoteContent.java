package com.laskdjlaskdj12.spygame.content.vote;

import com.laskdjlaskdj12.spygame.content.BlockContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import com.laskdjlaskdj12.spygame.util.TickUtil;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;


/**
 * Todo: AVotecontent를 아래와 같이 리팩토링 할것
 * 1. PreVote() // 투표시작하기전에 실행
 * 2. OngoingVote() // 1초마다 한번씩 실행되는 VoteCommand
 * 3. EndVote() // 투표가 종료됬을때 실행
 */
public abstract class AVoteContent {

    public static BukkitTask bukkitTask = null;
    public static int VoteCountTime = 0;
    protected final JavaPlugin plugin;
    protected IVoteResultHandler iVoteResultHandler;

    public AVoteContent(JavaPlugin plugin, int voteCountTime) {
        VoteCountTime = TickUtil.secondToTick(voteCountTime);
        this.plugin = plugin;
    }

    public abstract void startVote(List<ICharacter> voterList, Player voteStarter);

    public abstract VotingResult collectVoteResult(List<ICharacter> voterList);

    protected boolean isCharacterExist(List<ICharacter> iCharacters, List<Player> playerList) {
        //나간 사람들이 없는지 체크
        for (ICharacter character : iCharacters) {
            if (!iCharacters.contains(character.getPlayer())) return true;
        }

        return false;
    }

    protected void giveVoteItemToPlayer(ICharacter character) {

        //기존 블록들을 아이템 슬롯 3번으로 옮김
        moveItemToUpper(character);

        character.getPlayer().getInventory().setItem(0, makeCustomItem("찬성", BlockContent.makeWOOLItemByColor(DyeColor.WHITE)));
        character.getPlayer().getInventory().setItem(1, makeCustomItem("반대", BlockContent.makeWOOLItemByColor(DyeColor.BLACK)));
    }

    protected void removeVoteItem(List<ICharacter> characterList) {
        characterList.forEach(iCharacter -> iCharacter.getPlayer().getInventory().setItem(0, new ItemStack(Material.AIR)));
        characterList.forEach(iCharacter -> iCharacter.getPlayer().getInventory().setItem(1, new ItemStack(Material.AIR)));

        characterList.forEach(this::moveItemToDown);
    }

    protected VotingResult votingTotal(List<Boolean> voteResult) {
        int aiCount = 0;
        int nayCount = 0;
        for (Boolean isAi : voteResult) {
            if (isAi) {
                aiCount += 1;
            } else {
                nayCount += 1;
            }
        }

        return VotingResult.builder()
                .aiCount(aiCount)
                .nayCount(nayCount)
                .build();
    }

    public void setResultHandler(IVoteResultHandler resultHandler) {
        this.iVoteResultHandler = resultHandler;
    }

    protected void moveItemToUpper(ICharacter iCharacter) {
        //하위 아래 아이템들을 전부 상위로 올려버림
        ItemStack zeroInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(0);
        ItemStack oneInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(1);
        ItemStack twoInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(2);
        ItemStack threeInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(3);
        ItemStack fourInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(4);
        ItemStack fiveInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(5);
        ItemStack sixInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(6);
        ItemStack sevenInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(7);
        ItemStack eightInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(8);

        iCharacter.getPlayer().getInventory().setItem(9, zeroInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(10, oneInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(11, twoInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(12, threeInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(13, fourInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(14, fiveInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(15, sixInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(16, sevenInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(17, eightInventorySlotItem);

        iCharacter.getPlayer().getInventory().setItem(0, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(1, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(2, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(3, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(4, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(5, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(6, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(7, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(8, new ItemStack(Material.AIR));
    }

    protected void moveItemToDown(ICharacter iCharacter) {
        ItemStack zeroInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(9);
        ItemStack oneInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(10);
        ItemStack twoInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(11);
        ItemStack threeInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(12);
        ItemStack fourInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(13);
        ItemStack fiveInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(14);
        ItemStack sixInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(15);
        ItemStack sevenInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(16);
        ItemStack eightInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(17);

        iCharacter.getPlayer().getInventory().setItem(0, zeroInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(1, oneInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(2, twoInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(3, threeInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(4, fourInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(5, fiveInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(6, sixInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(7, sevenInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(8, eightInventorySlotItem);

        iCharacter.getPlayer().getInventory().setItem(9, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(10, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(11, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(12, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(13, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(14, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(15, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(16, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(17, new ItemStack(Material.AIR));
    }

    private ItemStack makeCustomItem(String name, Material blockMaterial) {
        ItemStack block = new ItemStack(blockMaterial);
        ItemMeta blockMetadata = block.getItemMeta();
        blockMetadata.setDisplayName(name);
        block.setItemMeta(blockMetadata);

        return block;
    }

    private ItemStack makeCustomItem(String name, ItemStack block) {
        ItemMeta blockMetadata = block.getItemMeta();
        blockMetadata.setDisplayName(name);
        block.setItemMeta(blockMetadata);

        return block;
    }
}
