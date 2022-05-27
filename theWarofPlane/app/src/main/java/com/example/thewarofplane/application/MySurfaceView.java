package com.example.thewarofplane.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.thewarofplane.MainActivity;
import com.example.thewarofplane.aircraft.AbstractAircraft;
import com.example.thewarofplane.aircraft.HeroAircraft;
import com.example.thewarofplane.basic.AbstractFlyingObject;
import com.example.thewarofplane.bullet.BaseBullet;
import com.example.thewarofplane.prop.BaseProp;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import androidx.annotation.NonNull;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    public static double heroX = MainActivity.screenWidth /2;
    ;
    public static double heroY = MainActivity.screenHeight - ImageManager.HERO_IMAGE.getHeight();

    private int backGroundTop = 0;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 20;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 480;
    private int cycleTime = 0;

    private HeroAircraft heroAircraft;
    private List<AbstractAircraft> enemyAircrafts;
    private List<BaseBullet> heroBullets;
    private List<BaseBullet> enemyBullets;
    private List<BaseProp> props;

    private int score;

    private GameBase gameBase;

    int screenWidth = MainActivity.screenWidth, screenHeight = MainActivity.screenHeight;
    boolean mbLoop = false; //控制绘画线程的标志位
    private SurfaceHolder mSurfaceHolder;
    private Canvas canvas;  //绘图的画布
    private Paint mPaint;
    public MySurfaceView(Context context) {
        super(context);
        mbLoop = true;
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
    }
    public void draw(){
        //通过SurfaceHolder对象的lockCanvans()方法，我们可以获取当前的Canvas绘图对象
        canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }

        System.out.println(screenHeight);

        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE,0,this.backGroundTop-ImageManager.BACKGROUND_IMAGE.getHeight(),mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE,0,this.backGroundTop,mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE,0,this.backGroundTop+ImageManager.BACKGROUND_IMAGE.getHeight(),mPaint);
        backGroundTop += 1;
        if(backGroundTop == screenHeight){
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(enemyBullets,canvas,mPaint);
        paintImageWithPositionRevised(heroBullets,canvas,mPaint);

        paintImageWithPositionRevised( enemyAircrafts,canvas,mPaint);

        //绘制道具
        paintImageWithPositionRevised(props,canvas,mPaint);


        canvas.drawBitmap(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, mPaint);

        Paint mPaint1 = new Paint();
        mPaint1.setColor(Color.RED);
        mPaint1.setTypeface(Typeface.SERIF);
        mPaint1.setTextSize(22);
        //绘制得分和生命值
        paintScoreAndLife(canvas,mPaint1);

        //通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void paintImageWithPositionRevised( List<? extends AbstractFlyingObject> objects,Canvas canvas,Paint paint) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, mPaint);
        }
    }

    private void paintScoreAndLife(Canvas canvas,Paint mPaint1) {
        int x = 10;
        int y = 25;
        canvas.drawText("SCORE:" + this.score, x, y,mPaint1);
        y = y + 25;
        canvas.drawText("LIFE:" + this.heroAircraft.getHp(), x, y,mPaint1);
    }

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        //设置一个循环来绘制，通过标志位来控制开启绘制还是停止
        while (mbLoop){


            if(gameBase == null){
                gameBase = new GameBase();
            }
            else{
                gameBase = gameBase;
            }
            enemyAircrafts = gameBase.getEnemyAircrafts();
            heroAircraft = gameBase.getHeroAircraft1();
            heroAircraft.setLocation(heroX,heroY);
            heroBullets = gameBase.getHeroBullets();
            enemyBullets = gameBase.getEnemyBullets();
            props = gameBase.getProps();
            score = gameBase.getScore();
            if (timeCountAndNewCycleJudge()) {
                gameBase.enemyApper();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                gameBase.action();
            }

            synchronized (mSurfaceHolder){
                draw();
            }
            try {
                Thread.sleep(20);
            }catch (Exception e){}
        }
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new Thread(this).start();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mbLoop = false;
    }
}
