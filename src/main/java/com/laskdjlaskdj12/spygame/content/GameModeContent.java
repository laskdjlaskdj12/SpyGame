package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GameModeContent {

    private List<ICharacter> characterList = new ArrayList<>();
    private final ExperditionContent experditionContent;

    public GameModeContent(ExperditionContent experditionContent) {
        this.experditionContent = experditionContent;
    }

    public void saveCharacter(List<ICharacter> characters) {
        characterList = characters;
    }

    public List<ICharacter> characterList() {
        return characterList;
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

    public int experditionCount(){
        return experditionContent.roundCount();
    }

    /**
     * Todo: 디버그용으로 나중에 삭제
     */
    public void addCharacterDebug(ICharacter character){
        characterList.add(character);
    }
}
