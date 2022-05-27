package com.example.thewarofplane.prop;

import com.example.thewarofplane.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;

public class Bomb {
    private static List<AbstractFlyingObject> flyingList = new ArrayList<>();

    public void addFlying(AbstractFlyingObject abstractFlyingObject){
        flyingList.add(abstractFlyingObject);
    }

    public void removeFlying(AbstractFlyingObject abstractFlyingObject){
        flyingList.remove(abstractFlyingObject);
    }

    public void removeAllFlying(){
        flyingList.removeAll(flyingList);
    }

    public static void notifyAll(int a){
        for (AbstractFlyingObject abstractFlyingObject : flyingList){
            abstractFlyingObject.update(abstractFlyingObject);
        }
    }
}
