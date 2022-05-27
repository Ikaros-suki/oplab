package com.example.thewarofplane.prop;

import com.example.thewarofplane.aircraft.AbstractAircraft;

public class PropBulletFactory implements PropFactory{
    @Override
    public Prop createProp(AbstractAircraft enemyAircraft) {
        return new PropBullet(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                0,
                10
        );
    }
}
