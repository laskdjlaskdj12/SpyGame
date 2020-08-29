package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.command.cheat.CollectVoteResultBlock;
import com.laskdjlaskdj12.spygame.config.BlockConfig;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.vote.VoteContent;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameModeContent {

    //Todo: 리팩토링 할것
    private final ExperditionContent experditionContent;
    private List<ICharacter> characterList = new ArrayList<>();
    private List<Block> voteResultBlock = new ArrayList<>();
    private List<Block> activeVoteResultBlock = new ArrayList<>();
    private boolean excaliberExsist = true;
    private int winCount = 0;
    private int loseCount = 0;

    private boolean collectVoteBlock = false;

    public GameModeContent(ExperditionContent experditionContent) {
        this.experditionContent = experditionContent;
    }

    public void saveCharacter(List<ICharacter> characters) {
        characterList = characters;
    }

    public List<ICharacter> characterList() {
        return characterList;
    }

    public void loadVoteResultBlock(World world, int voterCount) {
        this.voteResultBlock = Arrays.asList(BlockConfig.VoteBlockCordinate).stream()
                .map(cordinate -> world.getBlockAt(cordinate.getX(),
                        cordinate.getY(),
                        cordinate.getZ()))
                .collect(Collectors.toList());

        activeVoteResultBlock.clear();

        for(int i = 0; i < voterCount; i++){
            activeVoteResultBlock.add(this.voteResultBlock.get(i));
        }
    }

    @Nullable
    public ICharacter findCharacterFromPlayer(Player player) {
        return characterList.stream().filter(character -> character.getPlayer() == player)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public ICharacter findCharacterFromName(String name) {
        return characterList.stream()
                .filter(character -> character.getPlayer().getDisplayName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public int experditionCount() {
        return experditionContent.roundCount();
    }

    /**
     * Todo: 디버그용으로 나중에 삭제
     */
    public void addCharacterDebug(ICharacter character) {
        characterList.add(character);
    }

    public ExperditionContent experditionContent() {
        return experditionContent;
    }

    @Nullable
    public ICharacter findCharacterByGameRole(GAME_ROLE gameRole) {
        for (ICharacter iCharacter : characterList) {
            if (iCharacter.getGameRole() == gameRole) {
                return iCharacter;
            }
        }

        return null;
    }

    public void deActiveVoteBlockSet() {
        collectVoteBlock = false;
    }

    public void activeVoteBlockSet() {
        collectVoteBlock = true;
    }

    public boolean getCollectVoteBlock() {
        return collectVoteBlock;
    }

    public List<Block> getActiveVoteResultBlock() {
        return activeVoteResultBlock;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public int getWinCount(){
        return winCount;
    }

    public boolean isExcaliberExsist() {
        return excaliberExsist;
    }

    public void setExcaliberExsist(boolean exsist){
        excaliberExsist = exsist;
    }
}
