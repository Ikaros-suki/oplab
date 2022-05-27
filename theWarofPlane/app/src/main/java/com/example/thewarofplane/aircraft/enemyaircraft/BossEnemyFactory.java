package com.example.thewarofplane.aircraft.enemyaircraft;

import com.example.thewarofplane.MainActivity;

public class BossEnemyFactory implements EnemyAircraftFactory{
    @Override
    public EnemyAircraft createEnemyAircraft(int blood,int speed) {
        return new BossEnemy(
                MainActivity.screenWidth /2 ,
                (int) (Math.random() * MainActivity.screenHeight * 0.2)*1,
                3,
                0,
                blood
        );
    }
}
