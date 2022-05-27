package com.example.thewarofplane.prop;

import com.example.thewarofplane.aircraft.HeroAircraft;
import com.example.thewarofplane.firestrategy.FireContext;
import com.example.thewarofplane.firestrategy.ScatterShoot;

public class PropBullet extends BaseProp implements Prop{
    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void func(HeroAircraft heroAircraft) {
        //new MusicThread("src/videos/bullet.wav",true,false).start();
        FireContext context = new FireContext(new ScatterShoot());
        heroAircraft.shoot(context,heroAircraft);
    }
}
