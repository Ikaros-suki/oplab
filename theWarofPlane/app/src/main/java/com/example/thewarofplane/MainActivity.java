package com.example.thewarofplane;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.thewarofplane.application.GameActivity;
import com.example.thewarofplane.dao.PlayerDao;
import com.example.thewarofplane.dao.PlayerDaoImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_simple;
    private Button bt_common;
    private Button bt_difficult;

    public static int screenWidth;
    public static int screenHeight;
    public static String difficulty;
    public static PlayerDao playerDao = new PlayerDaoImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getScreenHW();

        bt_simple = findViewById(R.id.bt_simple);
        bt_simple.setOnClickListener(this);
        bt_common = findViewById(R.id.bt_common);
        bt_common.setOnClickListener(this);
        bt_difficult = findViewById(R.id.bt_difficult);
        bt_difficult.setOnClickListener(this);
    }

    //获取屏幕宽高
    public void getScreenHW(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.bt_simple:
                MainActivity.difficulty = "simple";
                intent.setClass(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_common:
                MainActivity.difficulty = "common";
                intent.setClass(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_difficult:
                MainActivity.difficulty = "difficult";
                intent.setClass(this, GameActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}