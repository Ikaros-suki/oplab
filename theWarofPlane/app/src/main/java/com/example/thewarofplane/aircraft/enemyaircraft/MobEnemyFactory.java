package com.example.thewarofplane.aircraft.enemyaircraft;

import com.example.thewarofplane.MainActivity;
import com.example.thewarofplane.application.ImageManager;

public class MobEnemyFactory implements EnemyAircraftFactory{
    @Override
    public EnemyAircraft createEnemyAircraft(int blood,int speed) {
        return new MobEnemy(
                (int) ( Math.random() * (MainActivity.screenWidth- ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * MainActivity.screenHeight * 0.2)*1,
                0,
                speed,
                blood
        );
    }
}
