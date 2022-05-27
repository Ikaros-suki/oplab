package com.example.thewarofplane.aircraft;

import android.content.Context;

import com.example.thewarofplane.MainActivity;
import com.example.thewarofplane.application.ImageManager;
import com.example.thewarofplane.bullet.BaseBullet;
import com.example.thewarofplane.firestrategy.FireContext;

import java.util.List;

public class HeroAircraft extends AbstractAircraft{

    private static HeroAircraft heroAircraft;
    private HeroAircraft(int loctionX, int loctionY, int speedX, int speedY, int hp){
        super(loctionX,loctionY,speedX,speedY,hp);
    }

    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft == null){
            return new HeroAircraft(
                    MainActivity.screenWidth /2,
                    MainActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight() ,
                    0, 0, 200);
        }else{
            return heroAircraft;
        }

    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot(FireContext fireContext, AbstractAircraft abstractAircraft) {
        return fireContext.executeStrategy(abstractAircraft);
    }
}
