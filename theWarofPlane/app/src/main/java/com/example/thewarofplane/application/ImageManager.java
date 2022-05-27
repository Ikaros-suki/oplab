package com.example.thewarofplane.application;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.thewarofplane.R;
import com.example.thewarofplane.aircraft.HeroAircraft;
import com.example.thewarofplane.aircraft.enemyaircraft.BossEnemy;
import com.example.thewarofplane.aircraft.enemyaircraft.EliteEnemy;
import com.example.thewarofplane.aircraft.enemyaircraft.MobEnemy;
import com.example.thewarofplane.bullet.EnemyBullet;
import com.example.thewarofplane.bullet.HeroBullet;
import com.example.thewarofplane.prop.PropBlood;
import com.example.thewarofplane.prop.PropBomb;
import com.example.thewarofplane.prop.PropBullet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ImageManager {
    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Bitmap BACKGROUND_IMAGE;
    public static Bitmap BACKGROUND_IMAGE_1;
    public static Bitmap BACKGROUND_IMAGE_2;
    public static Bitmap BACKGROUND_IMAGE_3;
    public static Bitmap BACKGROUND_IMAGE_4;
    public static Bitmap BACKGROUND_IMAGE_5;
    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;
    public static Bitmap PROP_BLOOD;
    public static Bitmap PROP_BOMB;
    public static Bitmap PROP_BULLET;

    public static void imageLoad(Resources resources){

        try {
            BACKGROUND_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.bg);
            BACKGROUND_IMAGE_1 = BitmapFactory.decodeResource(resources,R.drawable.bg2);
            BACKGROUND_IMAGE_2 = BitmapFactory.decodeResource(resources,R.drawable.bg3);
            BACKGROUND_IMAGE_3 = BitmapFactory.decodeResource(resources,R.drawable.bg4);
            BACKGROUND_IMAGE_4 = BitmapFactory.decodeResource(resources,R.drawable.bg5);

            HERO_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.hero);
            MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.mob);
            ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.elite);
            BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.boss);
            HERO_BULLET_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.bullet_hero);
            ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.bullet_enemy);
            PROP_BLOOD = BitmapFactory.decodeResource(resources,R.drawable.prop_blood);
            PROP_BOMB = BitmapFactory.decodeResource(resources,R.drawable.prop_bomb);
            PROP_BULLET = BitmapFactory.decodeResource(resources,R.drawable.prop_bullet);

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(),ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(),BOSS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(PropBlood.class.getName(),PROP_BLOOD);
            CLASSNAME_IMAGE_MAP.put(PropBomb.class.getName(),PROP_BOMB);
            CLASSNAME_IMAGE_MAP.put(PropBullet.class.getName(),PROP_BULLET);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }
}
