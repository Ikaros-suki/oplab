package com.example.thewarofplane.dao;

import android.os.Build;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.RequiresApi;

public class PlayerDaoImpl implements PlayerDao{
    //模拟数据库数据
    private List<Player> players;

    public PlayerDaoImpl(){
        players = new ArrayList<Player>();
    }
    @Override
    public void findById(String playerId) {
        for(Player player : players){
            if(player.getPlayerId().equals(playerId)){
                System.out.println("Find player :" + playerId);
            }
        }
    }

    @Override
    public List<Player> getAllPlayers() {
        return players;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void addPlayer(Player player) {
        players.add(player);
        players.sort(Comparator.comparing(Player::getScore).reversed());
    }

    @Override
    public void deletePlayer(String playerId) {
        for (Player player : players){
            if(player.getPlayerId() == playerId){
                players.remove(player);
            }
        }
    }
}
