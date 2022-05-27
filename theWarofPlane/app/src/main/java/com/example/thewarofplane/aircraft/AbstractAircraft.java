package com.example.thewarofplane.aircraft;

import android.content.Context;

import com.example.thewarofplane.basic.AbstractFlyingObject;
import com.example.thewarofplane.bullet.BaseBullet;
import com.example.thewarofplane.firestrategy.FireContext;

import java.util.List;

public abstract class AbstractAircraft extends AbstractFlyingObject {

    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public AbstractAircraft(){}

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int blood){
        if((hp + blood) <= maxHp){
            hp += blood;
        }
        else{
            hp = hp;
        }
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot(FireContext fireContext, AbstractAircraft abstractAircraft);
}
