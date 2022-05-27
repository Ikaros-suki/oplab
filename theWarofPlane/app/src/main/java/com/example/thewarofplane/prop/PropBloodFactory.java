package com.example.thewarofplane.prop;

import com.example.thewarofplane.aircraft.AbstractAircraft;

public class PropBloodFactory implements PropFactory{
    @Override
    public Prop createProp(AbstractAircraft enemyAircraft) {
        return new PropBlood(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                0,
                10,
                10
        );
    }
}
