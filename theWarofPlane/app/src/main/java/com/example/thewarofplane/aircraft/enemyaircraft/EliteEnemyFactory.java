package com.example.thewarofplane.aircraft.enemyaircraft;

import com.example.thewarofplane.MainActivity;
import com.example.thewarofplane.application.ImageManager;

public class EliteEnemyFactory implements EnemyAircraftFactory{
    int[] ran = {3,-3};
    @Override
    public EnemyAircraft createEnemyAircraft(int blood,int speed) {
        return new EliteEnemy(
                (int) ( Math.random() * (MainActivity.screenWidth - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * MainActivity.screenHeight * 0.2)*1,
                ran[(int) (Math.random()*ran.length)],
                speed,
                blood
        );
    }
}
