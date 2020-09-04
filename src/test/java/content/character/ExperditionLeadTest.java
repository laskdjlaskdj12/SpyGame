package content.character;

import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.character.BasicCharacter;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExperditionLeadTest {
    @Test
    public void testfindCharacterFromName(){
        GameModeContent gameModeContent = new GameModeContent(null, null);
        List<ICharacter> iCharacters = mockICharacterList();
        gameModeContent.setCharacterList(iCharacters);

        Assert.assertNull(gameModeContent.findCharacterFromName("kuraje"));
        Assert.assertNotNull(gameModeContent.findCharacterFromName("kuraje0"));
    }

    private List<ICharacter> mockICharacterList(){
        return makeMockPlayer().stream().map(this::makeBasicCharacter)
        .collect(Collectors.toList());
    }

    private ICharacter makeBasicCharacter(Player player){
        ICharacter iCharacter = new BasicCharacter();
        iCharacter.setPlayer(player);

        return iCharacter;
    }

    private List<Player> makeMockPlayer(){
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            Player player = mock(Player.class);
            when(player.getDisplayName()).thenReturn("kuraje" + i);

            players.add(player);
        }

        return players;
    }

}
