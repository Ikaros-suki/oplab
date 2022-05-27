package com.example.musicdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public MusicService.MyBinder myBinder;
    private Connect conn;
    private Intent intent;

    private Button btnBullet;
    private Button btnOver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBullet = (Button) findViewById(R.id.button);
        btnOver = (Button) findViewById(R.id.button2);

        btnBullet.setOnClickListener(this);
        btnOver.setOnClickListener(this);

        Log.i("music demo","bind service");
        conn = new Connect();
        intent = new Intent(this,MusicService.class);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()) {
            case R.id.button://播放
                myBinder.playBullet();
                break;
            case R.id.button2:
                myBinder.playGameOver();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("music demo","stop");
        unbindService(conn);
    }

    class Connect implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            Log.i("music demo","Service Connnected");
            myBinder = (MusicService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
}