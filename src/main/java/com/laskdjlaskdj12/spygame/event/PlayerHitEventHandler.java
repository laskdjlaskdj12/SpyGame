package com.laskdjlaskdj12.spygame.event;

import com.laskdjlaskdj12.spygame.content.CharacterContent;
import com.laskdjlaskdj12.spygame.content.GameModeContent;
import com.laskdjlaskdj12.spygame.content.MessageContent;
import com.laskdjlaskdj12.spygame.content.character.ICharacter;
import com.laskdjlaskdj12.spygame.exception.AvalonGameError;
import com.laskdjlaskdj12.spygame.status.GAME_ROLE;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerHitEventHandler implements Listener {

    private final GameModeContent gameModeContent;
    private final MessageContent messageContent;

    public PlayerHitEventHandler(GameModeContent gameModeContent, MessageContent messageContent) {
        this.gameModeContent = gameModeContent;
        this.messageContent = messageContent;
    }

    @EventHandler
    public void playerHitEventHandler(EntityDamageByEntityEvent event) {
        if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) {
            //Todo: interface나 어노테이션을 사용해서 커스텀 PlayerHit 이벤트 핸들러를 만들것
            if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
                return;
            }

            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

            if (attacker.getInventory().getItemInMainHand().getType() == Material.BOOK) {
                activeLakeElf(attacker, victim);
            } else if (attacker.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD) {
                useExcalibur(attacker, victim);
            } else if (attacker.getInventory().getItemInMainHand().getType() == Material.DIAMOND_AXE) {
                givePlayerExcalibur(attacker, victim);
            } else if (attacker.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD) {
                activeMarlineAssine(attacker, victim);
            } else if (attacker.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
                chooseExperditionMember(attacker, victim);
            }
        }
    }

    private void chooseExperditionMember(Player attacker, Player vicitim){

        try {
            //현재 원정라운드별 인원수가 있는지 체크
            if (!gameModeContent.experditionContent().canAddMoreMember()) {

                String title = "[" +
                        gameModeContent.experditionContent().applyExperditionMemberCount() +
                        "/" +
                        gameModeContent.experditionContent().roundByMaxMemberCount() +
                        "]";

                String subTitle = "더이상 대원을 선발 할수 없습니다!!";

                messageContent.sendTitleToAll(title, subTitle);
                return;
            }

            //원정대원 추가
            boolean isAddedComplete = gameModeContent.experditionContent().addExperditioner(gameModeContent.findCharacterFromPlayer(vicitim));

            if(!isAddedComplete){
                String title = "[" +
                        ChatColor.RED +
                        "실패" +
                        ChatColor.WHITE +
                        "]";

                String subTitle  = vicitim.getDisplayName() + "님은 이미 원정대원입니다.";
                messageContent.sendTitleToAll(title, subTitle);
            }

            String title = "[" +
                    gameModeContent.experditionContent().applyExperditionMemberCount() +
                    "/" +
                    gameModeContent.experditionContent().roundByMaxMemberCount() +
                    "]";

            String subTitle  = vicitim.getDisplayName() + "님이 원정대원으로 선발되셨습니다.";
            messageContent.sendTitleToAll(title, subTitle);

            if(gameModeContent.experditionContent().roundByMaxMemberCount() == gameModeContent.experditionContent().applyExperditionMemberCount()){
                attacker.getInventory().setItem(0, new ItemStack(Material.AIR));
                attacker.getInventory().setItem(1, new ItemStack(Material.AIR));
            }

        }catch(AvalonGameError e){
            Bukkit.broadcastMessage(e.getMessage());
        }
    }

    private void activeMarlineAssine(Player attacker, Player victim) {
        ICharacter attackerCharacter = gameModeContent.findCharacterFromPlayer(attacker);
        ICharacter victimCharacter = gameModeContent.findCharacterFromPlayer(victim);

        if(attackerCharacter == null || victimCharacter == null){
            Bukkit.broadcastMessage("[에러] 암살자와 지목대상을 찾을수가 없습니다.");
            return;
        }

        if(attackerCharacter.getRole().name().equals("Assassine") && victimCharacter.getRole().name().equals("Marline")){
            Bukkit.broadcastMessage("암살자가 멀린을 처리했습니다.!!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/패배선언");
        }else{
            Bukkit.broadcastMessage("암살자가 멀린이 아닌 " + victimCharacter.getRole().KRName() + "을 지목했습니다!!.");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/승리선언");
        }
    }

    private void givePlayerExcalibur(Player attacker, Player victim) {
        //플레이어에게 엑스칼리버를 수여함
        ICharacter excaliberCharacter = gameModeContent.findCharacterFromPlayer(victim);

        if (excaliberCharacter == null) {
            attacker.sendMessage(ChatColor.RED + "수여하려고 하는 사람이 게임에 참여한 사람이 아닙니다!! 다시한번 확인해주세요");
            return;
        }

        //엑스칼리버가 이미 잇는지 확인
        if (!gameModeContent.isExcaliberExsist()) {
            Bukkit.broadcastMessage(attacker.getDisplayName() + " 님이 " + victim.getDisplayName() + " 님에게 엑스칼리버를 수여하였습니다.");
            return;
        }

        Bukkit.broadcastMessage(attacker.getDisplayName() + " 님이 " + victim.getDisplayName() + " 님에게 엑스칼리버를 수여하였습니다.");
        gameModeContent.setExcaliberExsist(true);

        //플레이어에게 엑스칼리버를 수여함
        gameModeContent.awardExcalibur(excaliberCharacter);

        //도끼 제거
        ICharacter owner = gameModeContent.findCharacterFromPlayer(attacker);
        CharacterContent.removeItem(owner, Material.DIAMOND_AXE);
    }

    private void useExcalibur(Player attacker, Player victim) {
        //공격당한 사람들의 투표결과를 변경
        ICharacter victimCharacter = gameModeContent.findCharacterFromPlayer(victim);
        ICharacter attackerCharacter = gameModeContent.findCharacterFromPlayer(attacker);

        if (victimCharacter == null) {
            Bukkit.broadcastMessage(ChatColor.RED + "[에러] 게임에 참여하지 않는 사람이 엑스칼리버를 가질수 없습니다.");
            return;
        }

        if(gameModeContent.experditionContent().getExperditionInfo() == null){
            attacker.sendMessage(ChatColor.RED + "원정이 아직 시작이 안되서 엑스칼리버 검을 사용할수없습니다.");
            return;
        }

        //받는 사람이 호수의 요정인지 확인
        if(victimCharacter.getGameRole() == GAME_ROLE.LAKE_ELF){
            attacker.sendMessage(ChatColor.RED + "호수의 요정에게는 엑스칼리버를 사용할수없습니다.");
            return;
        }

        if(attackerCharacter == null){
            Bukkit.broadcastMessage("게임참여 유저가 아닌 사람이 사용할수가 없습니다.");
            return;
        }

        if(attackerCharacter.getGameRole() != GAME_ROLE.EXCALIBUR_OWNER){
            attacker.sendMessage(ChatColor.RED + "엑스칼리버 사용권한이 없는 사람이 엑스칼리버를 사용할수없습니다.");
            return;
        }

        //공격자의 엑스칼리버를 제거
        gameModeContent.useExcaliber(attackerCharacter, victimCharacter);

        Bukkit.broadcastMessage(attacker.getDisplayName() + "님이 " + victim.getDisplayName() + "님에게 엑스칼리버를 사용했습니다.");
    }

    private void activeLakeElf(Player attacker, Player victim) {
        if (attacker.getInventory().getItemInMainHand().getType() != Material.BOOK) {
            return;
        }

        //3라운드 이후에 능력이 발동되도록 세팅
        if (gameModeContent.experditionCount() < 3) {
            attacker.sendMessage("호수의 여신 능력은 3라운드 이후에 사용이 가능합니다.");
            return;
        }

        if(!gameModeContent.experditionContent().isLakeElfAvalityAvailable()){
            attacker.sendMessage("호수의 여신 능력은 이미 사용됬어서 다음라운드에 사용이 가능합니다.");
            return;
        }

        ICharacter elfAttacker = gameModeContent.findCharacterFromPlayer(attacker);
        ICharacter elfVictim = gameModeContent.findCharacterFromPlayer(victim);
        if (elfVictim == null) {
            attacker.sendMessage("호수의 여신 능력을 사용할수가 없습니다. [이유] : 게임에 참여하지 않는 사람입니다.");
            return;
        }

        if(elfVictim.getGameRole() == GAME_ROLE.EXCALIBUR_OWNER){
            attacker.sendMessage("엑스칼리버 소유자에게 호수의 요정을 사용할수가 없습니다.");
            return;
        }

        ROLE_TYPE gameRole = elfVictim.getRole().roleType();
        attacker.sendMessage(elfVictim.getPlayer().getDisplayName() + "님은 " + gameRole.factionName + "입니다.");

        //호수의 여신에게 역할을 전달해주는 기능 추가
        gameModeContent.gameRoleContent().transitionToElf(elfAttacker, elfVictim);
        gameModeContent.experditionContent().useLakeElfAvality();
    }
}
