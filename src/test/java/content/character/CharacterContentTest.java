package content.character;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.RoleContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.content.role.IRole;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class CharacterContentTest {

    @Test
    public void createCharacterTest(){
        //given
        RoleContent roleContent = new RoleContent();
        CharacterContent characterContent = new CharacterContent();
        List<Player> players = new ArrayList<>();
        int applier = 10;

        for(int i = 0; i < applier; i++){
            Player player = mock(Player.class);
            players.add(mock(Player.class));
        }

        List<IRole> roleList = roleContent.assignmentRole(players);
        List<ICharacter> iCharacters = characterContent.createCharacter(roleList, players);

        //when
        boolean isRoleListSizeSameApplier = roleList.size() == applier;
        boolean isModredInclude = roleList.stream().anyMatch(iRole -> iRole.name().equals("Modred"));
        boolean isAssassineInclude = roleList.stream().anyMatch(iRole -> iRole.name().equals("Assassine"));
        boolean isMorganaInclude = roleList.stream().anyMatch(iRole -> iRole.name().equals("Morgana"));
        boolean isMarlineInclude = roleList.stream().anyMatch(iRole -> iRole.name().equals("Marline"));
        boolean isPercivalInclude = roleList.stream().anyMatch(iRole -> iRole.name().equals("Percival"));
        boolean isCerventInclude = roleList.stream().anyMatch(iRole -> iRole.name().equals("Cervent"));

        boolean isCharacterRoleListSizeSameApplier = iCharacters.size() == applier;
        boolean isCharacterModredInclude = iCharacters.stream().anyMatch(character -> character.getRole().name().equals("Modred"));
        boolean isCharacterAssassineInclude = iCharacters.stream().anyMatch(character -> character.getRole().name().equals("Assassine"));
        boolean isCharacterMorganaInclude = iCharacters.stream().anyMatch(character -> character.getRole().name().equals("Morgana"));
        boolean isCharacterMarlineInclude = iCharacters.stream().anyMatch(character -> character.getRole().name().equals("Marline"));
        boolean isCharacterPercivalInclude = iCharacters.stream().anyMatch(character -> character.getRole().name().equals("Percival"));
        boolean isCharacterCerventInclude = iCharacters.stream().anyMatch(character -> character.getRole().name().equals("Cervent"));

        //then
        Assert.assertEquals(10, roleList.size());
        Assert.assertTrue(isRoleListSizeSameApplier);
        Assert.assertTrue(isModredInclude);
        Assert.assertTrue(isAssassineInclude);
        Assert.assertTrue(isMorganaInclude);
        Assert.assertTrue(isMarlineInclude);
        Assert.assertTrue(isPercivalInclude);
        Assert.assertTrue(isCerventInclude);

        Assert.assertTrue(isCharacterRoleListSizeSameApplier);
        Assert.assertTrue(isCharacterModredInclude);
        Assert.assertTrue(isCharacterAssassineInclude);
        Assert.assertTrue(isCharacterMorganaInclude);
        Assert.assertTrue(isCharacterMarlineInclude);
        Assert.assertTrue(isCharacterPercivalInclude);
        Assert.assertTrue(isCharacterCerventInclude);
    }
}
