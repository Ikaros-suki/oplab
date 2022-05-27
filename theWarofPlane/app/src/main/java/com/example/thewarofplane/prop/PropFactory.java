package com.example.thewarofplane.prop;

import com.example.thewarofplane.aircraft.AbstractAircraft;

public interface PropFactory {
    Prop createProp(AbstractAircraft enemyAircraft);
}
