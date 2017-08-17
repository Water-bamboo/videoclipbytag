package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class Main2Activity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageButton button_play = (ImageButton) findViewById(R.id.imageButton);
        Button button_share = (Button) findViewById(R.id.button);
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton2);
        button_share.setOnClickListener(this);
        button_back.setOnClickListener(this);

        button_play.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //initVideoPath(); // 初始化MediaPlayer
        }

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
                
                break;
            case R.id.button:
                Intent intent1 = new Intent (Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("http://github.com"));
                startActivity(intent1);
                break;
            case R.id.imageButton2:
                ActivityCollector.finishAll();
                break;
            default:
                break;
        }
    }//转到mainactivity
    //保存文件

}
