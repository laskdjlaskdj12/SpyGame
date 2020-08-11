package com.laskdjlaskdj12.spygame.content;

import com.laskdjlaskdj12.spygame.content.role.IRole;
import com.laskdjlaskdj12.spygame.factory.RoleFactory;
import com.laskdjlaskdj12.spygame.status.ROLE_TYPE;
import org.apache.commons.collections4.ListUtils;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class RoleContent {

    public List<IRole> assignmentRole(List<Player> players) {
        List<IRole> playerRoleList = ListUtils.union(RoleFactory.createMustPickRole(), RoleFactory.createMinerPickRole());

        //만약 플레이어수가 현재 리스트보다 더 넘을경우 신하를 추가
        if (players.size() > playerRoleList.size()) {
            int addServentCount = players.size() - playerRoleList.size();

            for (int i = 0; i < addServentCount; i++) {
                playerRoleList.add(RoleFactory.createRole(ROLE_TYPE.CERVENT));
            }
        }

        Collections.shuffle(playerRoleList);

        return playerRoleList.subList(0, players.size());
    }
}
