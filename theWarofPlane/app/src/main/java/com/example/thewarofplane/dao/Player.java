package com.example.thewarofplane.dao;

public class Player {
    private String playerId;
    private int score;
    private String date;

    public Player(String playerId, int score,String date){
        this.playerId = playerId;
        this.score = score;
        this.date = date;
    }

    public String getPlayerId(){
        return playerId;
    }

    public void setPlayerId(String playerId){
        this.playerId = playerId;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public String getDate(){
        return date;
    }
}
