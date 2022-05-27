package com.example.thewarofplane.firestrategy;

import com.example.thewarofplane.basic.AbstractFlyingObject;
import com.example.thewarofplane.bullet.BaseBullet;

import java.util.List;

public class FireContext {

    private Strategy strategy;

    public FireContext(Strategy strategy){
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public List<BaseBullet> executeStrategy(AbstractFlyingObject abstractFlyingObject){
        return strategy.shoot(abstractFlyingObject);
    }
}
