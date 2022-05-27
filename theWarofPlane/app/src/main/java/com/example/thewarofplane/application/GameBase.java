package com.example.thewarofplane.application;

import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;

import com.example.thewarofplane.MainActivity;
import com.example.thewarofplane.aircraft.AbstractAircraft;
import com.example.thewarofplane.aircraft.HeroAircraft;
import com.example.thewarofplane.aircraft.enemyaircraft.BossEnemy;
import com.example.thewarofplane.aircraft.enemyaircraft.BossEnemyFactory;
import com.example.thewarofplane.aircraft.enemyaircraft.EliteEnemy;
import com.example.thewarofplane.aircraft.enemyaircraft.EliteEnemyFactory;
import com.example.thewarofplane.aircraft.enemyaircraft.EnemyAircraft;
import com.example.thewarofplane.aircraft.enemyaircraft.EnemyAircraftFactory;
import com.example.thewarofplane.aircraft.enemyaircraft.MobEnemy;
import com.example.thewarofplane.aircraft.enemyaircraft.MobEnemyFactory;
import com.example.thewarofplane.basic.AbstractFlyingObject;
import com.example.thewarofplane.bullet.BaseBullet;
import com.example.thewarofplane.dao.Player;
import com.example.thewarofplane.firestrategy.BossEnemyFire;
import com.example.thewarofplane.firestrategy.DirectShoot;
import com.example.thewarofplane.firestrategy.EliteEnemyFire;
import com.example.thewarofplane.firestrategy.FireContext;
import com.example.thewarofplane.firestrategy.ScatterShoot;
import com.example.thewarofplane.prop.BaseProp;
import com.example.thewarofplane.prop.Bomb;
import com.example.thewarofplane.prop.Prop;
import com.example.thewarofplane.prop.PropBlood;
import com.example.thewarofplane.prop.PropBloodFactory;
import com.example.thewarofplane.prop.PropBomb;
import com.example.thewarofplane.prop.PropBombFactory;
import com.example.thewarofplane.prop.PropBullet;
import com.example.thewarofplane.prop.PropBulletFactory;
import com.example.thewarofplane.prop.PropFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class GameBase{
    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> props;
    private int score;

    private FireContext heroContext;
    private FireContext eliteContext;
    private FireContext bossContext;

    protected EnemyAircraftFactory enemyAircraftFactory;
    protected EnemyAircraft enemyAircraft;
    private PropFactory propFactory;
    private Prop prop;

    private int bulletTime = 0;
    private boolean bulletFlag = false;
    private boolean bullet_f = false;

    protected int bossFlag = 0;

    protected int time = 0;

    private Bomb bomb;

    public GameBase(){
        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        heroContext = new FireContext(new DirectShoot());
        eliteContext = new FireContext(new EliteEnemyFire());
        bossContext = new FireContext(new BossEnemyFire());

        bomb = new Bomb();
    }

    public int getScore(){
        return this.score;
    }
    public HeroAircraft getHeroAircraft1(){
        return this.heroAircraft;
    }

    public List<AbstractAircraft> getEnemyAircrafts(){
        return enemyAircrafts;
    }

    public List<BaseBullet> getHeroBullets(){
        return this.heroBullets;
    }

    public List<BaseBullet> getEnemyBullets(){
        return this.enemyBullets;
    }

    public List<BaseProp> getProps(){
        return this.props;
    }

    public void enemyApper(){
        if(bulletFlag){
            heroContext.setStrategy(new ScatterShoot());
            if(!bullet_f){
                bulletTime = time;
                bullet_f = true;
            }
            if(time - bulletTime == 2400){
                bulletFlag = false;
                bullet_f = false;
            }
        }
        else{
            heroContext.setStrategy(new DirectShoot());
        }
        System.out.println(time);
        // 新敌机产生
        if(MainActivity.difficulty.equals("simple")){
            Mob();
        }else if(MainActivity.difficulty.equals("common")){
            Common();
        }else{
            Difficult();
        }
        // 飞机射出子弹
        shootAction();
    }

    private int bossAppearFlag = 0;
    private void Mob(){
        if (enemyAircrafts.size() < 4) {
            Random r = new Random();
            int ran = r.nextInt(4);
            if (ran == 0) {
                enemyAircraftFactory = new EliteEnemyFactory();
            } else {
                enemyAircraftFactory =  new MobEnemyFactory();
            }
            enemyAircraft = enemyAircraftFactory.createEnemyAircraft(30,8);
            enemyAircrafts.add((AbstractAircraft) enemyAircraft);

        }
    }

    private final int enemyBlood = 60;
    private int speedY = 8;
    private int extraBlood = 0;
    private int extraSpeedY = 0;
    private int addBloodFlag = 1;
    private int ran;
    private int rate = 30;

    private void Common(){
        if(time/10000 == addBloodFlag){
            extraBlood += 30;
            extraSpeedY += 2;
            rate += 1;
            addBloodFlag++;
            System.out.printf("提高难度！精英机概率：%.2f；精英敌机血量增加30，此时为%d；\nboss敌机血量增加30，此时为%d；敌机Y方向速度增加2，此时为%d。\n",(double)rate/100,enemyBlood+extraBlood,120+extraBlood,speedY+extraSpeedY);
        }
        if (enemyAircrafts.size() < 5) {
            if(bossFlag == 0  && score != 0){
                int flag = score / 300;
                if(flag != bossAppearFlag){
                    bossAppearFlag = flag;
                    enemyAircraftFactory = new BossEnemyFactory();
                    enemyAircraft = enemyAircraftFactory.createEnemyAircraft(120 + extraBlood,speedY + extraSpeedY);
                    enemyAircrafts.add((AbstractAircraft) enemyAircraft);
                    bossFlag = 1;
                }
            }

            Random r = new Random();
            ran = r.nextInt(100);
            if (ran < rate) {
                enemyAircraftFactory = new EliteEnemyFactory();
            } else {
                enemyAircraftFactory = new MobEnemyFactory();
            }
            enemyAircraft = enemyAircraftFactory.createEnemyAircraft(enemyBlood,speedY + extraSpeedY);
            enemyAircrafts.add((AbstractAircraft) enemyAircraft);

        }
    }

    public void Difficult(){
        if(time/10000 == addBloodFlag){
            extraBlood += 30;
            extraSpeedY += 2;
            rate += 1;
            addBloodFlag++;
            System.out.printf("提高难度！精英机概率：%.2f；精英敌机血量增加30，此时为%d；\nboss敌机血量增加30，此时为%d；敌机Y方向速度增加2，此时为%d。\n",(double)rate/100,enemyBlood+extraBlood,180+extraBlood,speedY+extraSpeedY);
        }
        if (enemyAircrafts.size() < 6) {
            if(bossFlag == 0  && score != 0){
                int flag = score / 200;
                if(flag != bossAppearFlag){

                    bossAppearFlag = flag;
                    enemyAircraftFactory = new BossEnemyFactory();
                    enemyAircraft = enemyAircraftFactory.createEnemyAircraft(180 + extraBlood,speedY + extraSpeedY);
                    enemyAircrafts.add((AbstractAircraft) enemyAircraft);
                    bossFlag = 1;
                }
            }

            Random r = new Random();
            ran = r.nextInt(100);
            if (ran < rate) {
                enemyAircraftFactory = new EliteEnemyFactory();
            } else {
                enemyAircraftFactory = new MobEnemyFactory();
            }
            enemyAircraft = enemyAircraftFactory.createEnemyAircraft(enemyBlood,speedY + extraSpeedY);
            enemyAircrafts.add((AbstractAircraft) enemyAircraft);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void action() {

        // 子弹移动
        bulletsMoveAction();

        // 飞机移动
        aircraftsMoveAction();

        //道具移动
        propsMoveAction();

        // 撞击检测
        crashCheckAction();

        // 后处理
        postProcessAction();

    }

    //***********************
    //      Action 各部分
    //***********************

    private void shootAction() {
        // TODO 敌机射击
        for(AbstractAircraft enemy : enemyAircrafts){
            if(enemy instanceof EliteEnemy){
                enemyBullets.addAll(enemy.shoot(eliteContext,enemy));
            }
            else if(enemy instanceof BossEnemy){
                enemyBullets.addAll(enemy.shoot(bossContext,enemy));
            }
        }
        // 英雄射击

        Log.i("11111", String.valueOf(heroAircraft.getLocationX()));
        Log.i("11111", String.valueOf(heroAircraft.getLocationY()));
        heroBullets.addAll(heroAircraft.shoot(heroContext,heroAircraft));
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (BaseProp prop : props) {
            prop.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if(enemyAircraft instanceof MobEnemy){
                            score += 10;
                        }
                        else{
                            if(enemyAircraft instanceof EliteEnemy){
                                score += 20;
                            }
                            else{
                                score += 50;
                                bossFlag = 0;
                            }
                            Random r = new Random();
                            int ran = r.nextInt(4);
                            switch (ran){
                                case 0:
                                    propFactory = new PropBloodFactory();
                                    prop = propFactory.createProp(enemyAircraft);
                                    props.add((BaseProp) prop);
                                    break;
                                case 1:
                                    propFactory = new PropBombFactory();
                                    prop = propFactory.createProp(enemyAircraft);
                                    props.add((BaseProp) prop);
                                    break;
                                case 2:
                                    propFactory = new PropBulletFactory();
                                    prop = propFactory.createProp(enemyAircraft);
                                    props.add((BaseProp) prop);
                                    break;
                                case 3:
                                    break;
                                default:break;
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for(BaseProp prop : props){
            if (heroAircraft.crash(prop) || prop.crash(heroAircraft)) {
                if(prop instanceof PropBlood){
                    prop.vanish();
                    ((PropBlood) prop).func(heroAircraft);
                }
                else if(prop instanceof PropBomb){
                    prop.vanish();
                    for(AbstractAircraft enemy:enemyAircrafts){
                        bomb.addFlying(enemy);
                    }
                    for(BaseBullet bullet:enemyBullets){
                        bomb.addFlying(bullet);
                    }
                    ((PropBomb) prop).func(heroAircraft);
                    bomb.removeAllFlying();
                }
                else {
                    bulletFlag = true;
                    prop.vanish();
                    ((PropBullet) prop).func(heroAircraft);
                }
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }
}
