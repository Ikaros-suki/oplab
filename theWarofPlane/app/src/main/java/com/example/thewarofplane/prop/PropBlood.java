package com.example.thewarofplane.prop;

import com.example.thewarofplane.aircraft.HeroAircraft;

public class PropBlood extends BaseProp implements Prop{

    private int blood = 10;
    public PropBlood(int locationX, int locationY, int speedX, int speedY,int blood) {
        super(locationX, locationY, speedX, speedY);
        this.blood = blood;
    }

    @Override
    public void func(HeroAircraft heroAircraft) {
        //new MusicThread("src/videos/get_supply.wav",true,false).start();
        heroAircraft.setHp(blood);
    }
}
