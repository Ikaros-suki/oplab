package com.example.thewarofplane.aircraft.enemyaircraft;

import com.example.thewarofplane.MainActivity;
import com.example.thewarofplane.aircraft.AbstractAircraft;
import com.example.thewarofplane.bullet.BaseBullet;
import com.example.thewarofplane.firestrategy.FireContext;

import java.util.List;

public class BossEnemy extends AbstractAircraft implements EnemyAircraft {
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward(){
        super.forward();
        if (locationX >= MainActivity.screenWidth) {
            speedX = -3;
        }
        else if( locationX <= 0) {
            speedX = 3;
        }
    }

    @Override
    public List<BaseBullet> shoot(FireContext fireContext, AbstractAircraft abstractAircraft) {
        return fireContext.executeStrategy(abstractAircraft);
    }
}
