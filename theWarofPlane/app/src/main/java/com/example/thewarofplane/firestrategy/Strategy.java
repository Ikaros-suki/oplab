package com.example.thewarofplane.firestrategy;

import com.example.thewarofplane.basic.AbstractFlyingObject;
import com.example.thewarofplane.bullet.BaseBullet;

import java.util.List;

public interface Strategy {
    List<BaseBullet> shoot(AbstractFlyingObject abstractFlyingObject);
}
