package com.example.thewarofplane.prop;

import com.example.thewarofplane.aircraft.HeroAircraft;

public class PropBomb extends BaseProp implements Prop{
    public PropBomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void func(HeroAircraft heroAircraft) {
        //new MusicThread("src/videos/bomb_explosion.wav",true,false).start();
        //Bomb.notifyAll(0);
        System.out.println("Bomb activity!");
    }
}
