package com.example.thewarofplane.firestrategy;

import com.example.thewarofplane.basic.AbstractFlyingObject;
import com.example.thewarofplane.bullet.BaseBullet;
import com.example.thewarofplane.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShoot extends AbstractFlyingObject implements Strategy {

    /** 攻击方式 */
    /** 子弹一次发射数量 */
    private int shootNum = 2;
    /** 子弹伤害 */
    private int power = 30;
    /** 子弹射击方向 (向上发射：1，向下发射：-1) */
    private int direction = -1;
    @Override
    public List<BaseBullet> shoot(AbstractFlyingObject abstractFlyingObject) {
        List<BaseBullet> res = new LinkedList<>();
        int x = abstractFlyingObject.getLocationX();
        int y = abstractFlyingObject.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = abstractFlyingObject.getSpeedY() + direction*5;
        BaseBullet baseBullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(baseBullet);
        }
        return res;
    }
}
