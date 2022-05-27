package com.example.thewarofplane.aircraft.enemyaircraft;

import com.example.thewarofplane.MainActivity;
import com.example.thewarofplane.aircraft.AbstractAircraft;
import com.example.thewarofplane.bullet.BaseBullet;
import com.example.thewarofplane.firestrategy.FireContext;

import java.util.LinkedList;
import java.util.List;

public class MobEnemy extends AbstractAircraft implements EnemyAircraft {
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.screenHeight) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot(FireContext fireContext, AbstractAircraft abstractAircraft) {
        return new LinkedList<>();
    }
}
