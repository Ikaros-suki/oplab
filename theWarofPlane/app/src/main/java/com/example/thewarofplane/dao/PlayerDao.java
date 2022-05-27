package com.example.thewarofplane.dao;

import java.util.List;

public interface PlayerDao {
    void  findById(String playerId);

    List<Player> getAllPlayers();

    void addPlayer(Player player);

    void deletePlayer(String playerId);
}
