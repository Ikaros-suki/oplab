package com.example.thewarofplane.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.thewarofplane.R;
import com.example.thewarofplane.aircraft.HeroAircraft;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageManager.imageLoad(getResources());
        MySurfaceView mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            MySurfaceView.heroX = (double) event.getX();
            MySurfaceView.heroY = (double) event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            MySurfaceView.heroX = (double) event.getX();
            MySurfaceView.heroY = (double) event.getY();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        return true;
    }
}