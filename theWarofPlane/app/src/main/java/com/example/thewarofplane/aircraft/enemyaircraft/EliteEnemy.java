package com.example.thewarofplane.aircraft.enemyaircraft;

import com.example.thewarofplane.MainActivity;
import com.example.thewarofplane.aircraft.AbstractAircraft;
import com.example.thewarofplane.bullet.BaseBullet;
import com.example.thewarofplane.firestrategy.FireContext;

import java.util.List;

public class EliteEnemy extends AbstractAircraft implements EnemyAircraft {
    /**
     * @param locationX 精英敌机位置x坐标
     * @param locationY 精英敌机位置y坐标
     * @param speedX 精英敌机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 精英敌机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.screenHeight) {
            vanish();
        }
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
