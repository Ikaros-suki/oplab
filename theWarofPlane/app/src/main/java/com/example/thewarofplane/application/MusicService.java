package com.example.thewarofplane.application;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.HashMap;

import androidx.annotation.Nullable;

import com.example.thewarofplane.R;

public class MusicService extends Service {

    private static  final String TAG = "MusicService";

    private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();

    private MediaPlayer player;
    public MusicService() {

    }






    @Override
    public IBinder onBind(Intent intent){
        playMusic();

    }



    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    //播放音乐
    public void playMusic(){
        if(player == null){
            player = MediaPlayer.create(this, R.raw.bgm);
            player.setLooping(true);
        }
        player.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }
    /**
     * 停止播放
     */
    public void stopMusic() {
        if (player != null) {
            player.stop();
            player.reset();//重置
            player.release();//释放
            player = null;
        }
    }
}

