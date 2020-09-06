package com.laskdjlaskdj12.spygame.content;

import com.google.gson.Gson;
import com.laskdjlaskdj12.spygame.content.character.BasicCharacter;
import com.laskdjlaskdj12.spygame.domain.JoinInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static org.bukkit.Bukkit.getLogger;

public class RestContent {

    public static void sendPlayerInfo(BasicCharacter basicCharacter) {
        CompletableFuture.runAsync(() -> {
            try {
                HashMap map = new HashMap();
                map.put("role", basicCharacter.getRole().name());

                HttpResponse<String> result = Unirest.post("https://www.5gspeedtest.kr:14006/laskdjos/minecraft/obs/avalon/character/set/" + basicCharacter.getPlayer().getDisplayName())
                        .body(new Gson().toJson(map))
                        .asString();

                if (result.getStatus() != 200) {
                    System.out.println("캐릭터 정보 전송이 실패했습니다. \n statusCode : " + result.getStatusText() + " \n 사유 : " + result.getBody());
                }

                getLogger().info("캐릭터 정보 전송이 완료되었습니다.");
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        });
    }

    public static void sendPlayerWin(Player player) {
        CompletableFuture.runAsync(() -> {
            try {
                HttpResponse<String> result = Unirest.get("https://www.5gspeedtest.kr:14006/laskdjos/minecraft/obs/avalon/setwin/" + player.getDisplayName())
                        .asString();

                if (result.getStatus() != 200) {
                    System.out.println("캐릭터 정보 전송이 실패했습니다. \n statusCode : " + result.getStatusText() + " \n 사유 : " + result.getBody());
                }

                getLogger().info("캐릭터 정보 전송이 완료되었습니다.");
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        });
    }

    public static void sendPlayerLose(Player player) {
        CompletableFuture.runAsync(() -> {
            try {
                HttpResponse<String> result = Unirest.get("https://www.5gspeedtest.kr:14006/laskdjos/minecraft/obs/avalon/setlose/" + player.getDisplayName())
                        .asString();

                if (result.getStatus() != 200) {
                    System.out.println("캐릭터 정보 전송이 실패했습니다. \n statusCode : " + result.getStatusText() + " \n 사유 : " + result.getBody());
                }

                getLogger().info("캐릭터 정보 전송이 완료되었습니다.");
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        });
    }

    public static void clearPlayer() {
        CompletableFuture.runAsync(() -> {
            try {
                HttpResponse<String> result = Unirest.get("https://www.5gspeedtest.kr:14006/laskdjos/minecraft/obs/avalon/character/clear")
                        .asString();

                if (result.getStatus() != 200) {
                    System.out.println("캐릭터 정보 전송이 실패했습니다. \n statusCode : " + result.getStatusText() + " \n 사유 : " + result.getBody());
                }

                getLogger().info("캐릭터 정보 전송이 완료되었습니다.");
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        });
    }


    public static void sendConnctedAddress(JoinInfo joinInfo) {
        CompletableFuture.runAsync(() -> {
            try {
                HttpResponse<String> result = Unirest.post("https://www.5gspeedtest.kr:14006/laskdjos/minecraft/user/save")
                        .body(new Gson().toJson(joinInfo))
                        .asString();

                if (result.getStatus() != 200) {
                    System.out.println("전송이 실패했습니다. \n statusCode : " + result.getStatusText() + " \n 사유 : " + result.getBody());
                }

                getLogger().info("전송이 완료되었습니다.");
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        });
    }
}
