package com.laskdjlaskdj12.spygame.content.vote;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.domain.VotingResult;
import com.laskdjlaskdj12.spygame.util.TickUtil;
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
        moveItemToRightSlot(character);

        character.getPlayer().getInventory().setItem(0, makeCustomItem("찬성", Material.WHITE_WOOL));
        character.getPlayer().getInventory().setItem(1, makeCustomItem("반대", Material.BLACK_WOOL));
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

    protected void moveItemToRightSlot(ICharacter iCharacter) {
        ItemStack zeroInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(0);
        ItemStack oneInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(1);

        iCharacter.getPlayer().getInventory().setItem(2, zeroInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(3, oneInventorySlotItem);
    }

    protected void moveItemToLeftSlot(ICharacter iCharacter) {
        ItemStack zeroInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(2);
        ItemStack oneInventorySlotItem = iCharacter.getPlayer().getInventory().getItem(3);

        iCharacter.getPlayer().getInventory().setItem(0, zeroInventorySlotItem);
        iCharacter.getPlayer().getInventory().setItem(1, oneInventorySlotItem);

        iCharacter.getPlayer().getInventory().setItem(2, new ItemStack(Material.AIR));
        iCharacter.getPlayer().getInventory().setItem(3, new ItemStack(Material.AIR));
    }

    private ItemStack makeCustomItem(String name, Material blockMaterial) {
        ItemStack block = new ItemStack(blockMaterial);
        ItemMeta blockMetadata = block.getItemMeta();
        blockMetadata.setDisplayName(name);
        block.setItemMeta(blockMetadata);

        return block;
    }
}
