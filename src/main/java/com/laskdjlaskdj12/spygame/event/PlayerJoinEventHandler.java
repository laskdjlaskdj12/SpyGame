package com.laskdjlaskdj12.spygame.event;

import com.google.gson.Gson;
import com.laskdjlaskdj12.spygame.content.RestContent;
import com.laskdjlaskdj12.spygame.domain.JoinInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import static org.bukkit.Bukkit.getLogger;


public class PlayerJoinEventHandler implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        InetSocketAddress address = player.getAddress();
        if (address == null) {
            return;
        }

        player.sendMessage("모든 세팅이 완료되었습니다!! Dev by " + ChatColor.YELLOW + "laskdjlaskdj12 (라스크) " + ChatColor.WHITE + "& Thanks for " + ChatColor.GREEN + "우리들의 마인크래프트 공간");

        JoinInfo joinInfo = new JoinInfo();
        joinInfo.setName(player.getDisplayName());
        joinInfo.setAddress(address.getAddress().getHostName());
        getLogger().info("접속자 정보 : " + player.getAddress().getHostString() + ":" + player.getAddress().getPort());

        RestContent.sendConnctedAddress(joinInfo);

        getLogger().info("전송된 접속자 정보 : " + player.getAddress().getHostString() + ":" + player.getAddress().getPort());
    }
}
