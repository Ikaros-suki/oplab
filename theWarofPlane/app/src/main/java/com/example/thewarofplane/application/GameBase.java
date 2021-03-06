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
        // ???????????????
        if(MainActivity.difficulty.equals("simple")){
            Mob();
        }else if(MainActivity.difficulty.equals("common")){
            Common();
        }else{
            Difficult();
        }
        // ??????????????????
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
            System.out.printf("?????????????????????????????????%.2f???????????????????????????30????????????%d???\nboss??????????????????30????????????%d?????????Y??????????????????2????????????%d???\n",(double)rate/100,enemyBlood+extraBlood,120+extraBlood,speedY+extraSpeedY);
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
            System.out.printf("?????????????????????????????????%.2f???????????????????????????30????????????%d???\nboss??????????????????30????????????%d?????????Y??????????????????2????????????%d???\n",(double)rate/100,enemyBlood+extraBlood,180+extraBlood,speedY+extraSpeedY);
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

        // ????????????
        bulletsMoveAction();

        // ????????????
        aircraftsMoveAction();

        //????????????
        propsMoveAction();

        // ????????????
        crashCheckAction();

        // ?????????
        postProcessAction();

    }

    //***********************
    //      Action ?????????
    //***********************

    private void shootAction() {
        // TODO ????????????
        for(AbstractAircraft enemy : enemyAircrafts){
            if(enemy instanceof EliteEnemy){
                enemyBullets.addAll(enemy.shoot(eliteContext,enemy));
            }
            else if(enemy instanceof BossEnemy){
                enemyBullets.addAll(enemy.shoot(bossContext,enemy));
            }
        }
        // ????????????

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
     * ???????????????
     * 1. ??????????????????
     * 2. ????????????/????????????
     * 3. ??????????????????
     */
    private void crashCheckAction() {
        // TODO ????????????????????????
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)){
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // ????????????????????????
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // ????????????????????????????????????????????????
                    // ???????????????????????????????????????????????????
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // ??????????????????????????????
                    // ???????????????????????????
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO ?????????????????????????????????
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
                // ????????? ??? ?????? ??????????????????
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: ?????????????????????????????????
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
     * ????????????
     * 1. ?????????????????????
     * 2. ?????????????????????
     * 3. ?????????????????????
     * <p>
     * ????????????????????????????????????????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }
}
