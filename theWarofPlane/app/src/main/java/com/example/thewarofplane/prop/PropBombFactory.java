package com.example.thewarofplane.prop;

import com.example.thewarofplane.aircraft.AbstractAircraft;

public class PropBombFactory implements PropFactory{
    @Override
    public Prop createProp(AbstractAircraft enemyAircraft) {
        return new PropBomb(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                0,
                10
        );
    }
}
