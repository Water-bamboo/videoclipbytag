package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


//8月3：存储视频、分享、进度条



public class MainActivity extends BaseActivity implements View.OnClickListener {

    private VideoView videoView;
    private TextView time_display;
    private ImageButton button_start;
    private long periodtime;
    private long starttime;
    private File file = new File(Environment.getExternalStorageDirectory(), "timer1.mp4");
    private File file_s = new File(Environment.getExternalStorageDirectory(), "");
    private ImageView glide;
    private ImageView glide1;
    private ImageView glide2;
    private Button button_load;
    static private int openfileDialogId = 0;
    private String filepath ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton button_saved = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton button_back = (ImageButton) findViewById(R.id.imageButton8);
        button_start = (ImageButton) findViewById(R.id.imageButton9);
        ImageButton button_stop = (ImageButton) findViewById(R.id.imageButton6);
        ImageButton button_share = (ImageButton) findViewById(R.id.imageButton5);
        ImageButton button_5 = (ImageButton) findViewById(R.id.imageButton10);
        ImageButton button_10 = (ImageButton) findViewById(R.id.imageButton11);
        ImageButton button_25 = (ImageButton) findViewById(R.id.imageButton12);
        Button button_seekto5 = (Button) findViewById(R.id.button3);
        Button button_seekto10 = (Button) findViewById(R.id.button4);
        Button button_seekto20 = (Button) findViewById(R.id.button6);
        Button button_seekto30 = (Button) findViewById(R.id.button7);
        Button button_generate = (Button) findViewById(R.id.button5);
        button_load = (Button) findViewById(R.id.button8);
        time_display = (TextView) findViewById(R.id.textView2);
        glide = (ImageView) findViewById(R.id.imageView2);
        glide1 = (ImageView) findViewById(R.id.imageView3);
        glide2 = (ImageView) findViewById(R.id.imageView4);


        // videoView.setVideoPath(Environment.getExternalStorageDirectory()+ File.separator+"/pwm.mp4");registerButtonHandler();
        // videoView.requestFocus();
        videoView = (VideoView) findViewById(R.id.videoView2);
        //视频大小
        MediaController mc = new MediaController(MainActivity.this);//Video是我类名，是你当前的类
        videoView.setMediaController(mc);//设置VedioView与MediaController相关联
        button_start.setOnClickListener(this);
        button_back.setOnClickListener(this);
        button_stop.setOnClickListener(this);
        button_saved.setOnClickListener(this);
        button_share.setOnClickListener(this);
        button_seekto10.setOnClickListener(this);
        button_seekto5.setOnClickListener(this);
        button_seekto20.setOnClickListener(this);
        button_seekto30.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_10.setOnClickListener(this);
        button_25.setOnClickListener(this);
        button_generate.setOnClickListener(this);
        button_load.setOnClickListener(this);
        String time = stringForTime(videoView.getCurrentPosition());
        time_display.setText(time);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            showDialog(openfileDialogId);
            //initVideoPath(); // 初始化MediaPlayer
        }
       /* if (videoView.isPlaying()) {
            for (; ; ) {
                if ((videoView.getCurrentPosition() == 5000 + periodtime) || (videoView.getCurrentPosition() == 10000 + periodtime) || (videoView.getCurrentPosition() == 25000 + periodtime)) {
                    videoView.pause();
                }
            }
        }*/

    }

    private void initVideoPath() {
        //File file = new File(Environment.getExternalStorageDirectory(), "timer1.mp4");

        videoView.setVideoPath(filepath); // 指定视频文件的路径
        //videoView.setDrawingCacheEnabled();
    }


    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public void save(VideoView videoView) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(String.valueOf(videoView));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton9:
                if (!videoView.isPlaying()) {
                    time_display.setText(null);
                    button_start.setImageDrawable(null);
                    videoView.start(); // 开始播放
                } else {
                    videoView.pause();
                    String time = stringForTime(videoView.getCurrentPosition());
                    time_display.setText(time);
                    button_start.setImageDrawable(getResources().getDrawable(R.mipmap.artboard_2_copy_7));
                }
                break;
            case R.id.imageButton8:
                ActivityCollector.finishAll();
                break;
            case R.id.imageButton6:
                if (videoView.isPlaying()) {
                    videoView.stopPlayback(); // 重新播放
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Cancel");
                dialog.setMessage("Cancelling Confirmed?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        videoView.setVideoPath(null);
                    }
                });
                dialog.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
            case R.id.imageButton3:

                videoView.setVideoPath(filepath);
                save(videoView);//save
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.button5:
                if(periodtime == 10*1000){
                    cutMp4(starttime,starttime+periodtime,filepath,Environment.getExternalStorageDirectory().getPath(),"timer1_10_saved.mp4");
                   // File file_10 =new File(Environment.getExternalStorageDirectory(), "timer1_10_saved.mp4");
                   // videoView.setVideoPath(file_10.getPath());
                }else if(periodtime == 5*1000){
                    cutMp4(starttime,starttime+periodtime,filepath,Environment.getExternalStorageDirectory().getPath(),"timer1_5_saved.mp4");
                    //File file_5 =new File(Environment.getExternalStorageDirectory(), "timer1_5_saved.mp4");
                    //videoView.setVideoPath(file_5.getPath());
                }else if(periodtime == 20*1000){
                    cutMp4(starttime,starttime+periodtime,filepath,Environment.getExternalStorageDirectory().getPath(),"timer1_20_saved.mp4");
                    //File file_20 =new File(Environment.getExternalStorageDirectory(), "timer1_20_saved.mp4");
                    //videoView.setVideoPath(file_20.getPath());
                }else if(periodtime == 30*1000){
                    cutMp4(starttime,starttime+periodtime,filepath,Environment.getExternalStorageDirectory().getPath(),"timer1_30_saved.mp4");
                    //File file_30 =new File(Environment.getExternalStorageDirectory(), "timer1_30_saved.mp4");
                    //videoView.setVideoPath(file_30.getPath());
                }
                Intent intent_1 = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent_1);
                break;
            case R.id.imageButton5:
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("http://github.com"));
                startActivity(intent1);
                break;
            case R.id.button3:
                //if ((videoView.getCurrentPosition()== 15000)||(videoView.getCurrentPosition()==20000)||(videoView.getCurrentPosition()==35000)) {
                // videoView.pause();
                //videoView.seekTo(10*1000);
                periodtime = 10*1000;
                cutMp4(starttime,starttime+periodtime,filepath,Environment.getExternalStorageDirectory().getPath(),"timer1_10.mp4");
                File file_10 =new File(Environment.getExternalStorageDirectory(), "timer1_10.mp4");
                videoView.setVideoPath(file_10.getPath());


                // }
                break;
            case R.id.button4:
                //if ((videoView.getCurrentPosition()== 10000)||(videoView.getCurrentPosition()==15000)||(videoView.getCurrentPosition()==30000)) {
                // videoView.pause();
                //videoView.seekTo(5*1000);
                periodtime = 5*1000;
                cutMp4(starttime,starttime+periodtime,filepath,Environment.getExternalStorageDirectory().getPath(),"timer1_5.mp4");
               File file_5 =new File(Environment.getExternalStorageDirectory(), "timer1_5.mp4");

                videoView.setVideoPath(file_5.getPath());
                // }
                break;
            case R.id.button6:
                //if ((videoView.getCurrentPosition()== 25000)||(videoView.getCurrentPosition()==30000)||(videoView.getCurrentPosition()==45000)) {
                // videoView.pause();
                //if (videoView.isPlaying()) {
                //videoView.seekTo(20*1000);
                periodtime = 20*1000;
                cutMp4(starttime,starttime+periodtime,filepath,Environment.getExternalStorageDirectory().getPath(),"timer1_20.mp4");
                File file_20 =new File(Environment.getExternalStorageDirectory(), "timer1_20.mp4");

                videoView.setVideoPath(file_20.getPath());
                //}
                break;
            case R.id.button7:
                //if ((videoView.getCurrentPosition()== 35000)||(videoView.getCurrentPosition()==40000)||(videoView.getCurrentPosition()==55000)) {
                // videoView.pause();
                //if (videoView.isPlaying()) {
                //videoView.seekTo(30*1000);
                periodtime = 30*1000;
                cutMp4(starttime,starttime+periodtime,filepath,Environment.getExternalStorageDirectory().getPath(),"timer1_30.mp4");
                File file_30 =new File(Environment.getExternalStorageDirectory(), "timer1_30.mp4");

                videoView.setVideoPath(file_30.getPath());
                //}
                break;
            case R.id.imageButton10:
                //if (videoView.isPlaying()) {
                videoView.seekTo(5 * 1000);
                starttime = 5*1000;
                //cutMp4(starttime,starttime+periodtime,file.getPath(),Environment.getExternalStorageDirectory().getPath(),"timer1_5.mp4");
                //File file_5 =new File(Environment.getExternalStorageDirectory(), "timer1_5.mp4");
                //videoView.setVideoPath(file_5.getPath());
                if (starttime == 5*1000) {
                    glide.setImageDrawable(getResources().getDrawable(R.mipmap.artboard_2_copy_5));
                    glide1.setImageDrawable(null);
                    glide2.setImageDrawable(null);
                 }
                break;
            case R.id.imageButton11:
                //if (videoView.isPlaying()) {
                videoView.seekTo(10 * 1000);
                starttime = 10*1000;
                //cutMp4(starttime,starttime+periodtime,file.getPath(),Environment.getExternalStorageDirectory().getPath(),"timer1_10.mp4");
                //File file_10 =new File(Environment.getExternalStorageDirectory(), "timer1_10.mp4");
                //videoView.setVideoPath(file_10.getPath());
                if (starttime == 10*1000) {
                    glide.setImageDrawable(null);
                    glide1.setImageDrawable(getResources().getDrawable(R.mipmap.artboard_2_copy_5));
                    glide2.setImageDrawable(null);
                 }
                break;
            case R.id.imageButton12:
                //if (videoView.isPlaying()) {
                videoView.seekTo(25 * 1000);
                starttime = 25*1000;
                //cutMp4(starttime,starttime+periodtime,file.getPath(),Environment.getExternalStorageDirectory().getPath(),"timer1_25.mp4");
               // File file_25 =new File(Environment.getExternalStorageDirectory(), "timer1_25.mp4");
               // videoView.setVideoPath(file_25.getPath());
                if (starttime == 25*1000) {
                    glide.setImageDrawable(null);
                    glide1.setImageDrawable(null);
                    glide2.setImageDrawable(getResources().getDrawable(R.mipmap.artboard_2_copy_5));
                }
                break;
            case R.id.button8:
                showDialog(openfileDialogId);
                //videoView.setVideoPath(filepath);
            default:

                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==openfileDialogId){
            Map<String, Integer> images = new HashMap<String, Integer>();
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
            images.put(OpenFileDialog.sRoot, R.mipmap.ic_launcher);   // 根目录图标
            images.put(OpenFileDialog.sParent, R.mipmap.ic_launcher);    //返回上一层的图标
            images.put(OpenFileDialog.sFolder, R.mipmap.ic_launcher);   //文件夹图标
            images.put("mp4", R.mipmap.ic_launcher_round);   //wav文件图标
            images.put(OpenFileDialog.sEmpty, R.mipmap.ic_launcher);
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                             filepath = bundle.getString("path");
                            setTitle(filepath);
                            videoView.setVideoPath(filepath);
                            // 把文件路径显示在标题上
                        }
                    },
                    ".mp4;",
                    images);
            return dialog;
        }
        return null;
    }


    private synchronized void cutMp4(final long startTime, final long endTime, final String FilePath, final String WorkingPath, final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //视频剪切
                    VideoClip videoClip = new VideoClip();//实例化VideoClip类
                    videoClip.setFilePath(FilePath);//设置被编辑视频的文件路径  FileUtil.getMediaDir()+"/test/laoma3.mp4"
                    videoClip.setWorkingPath(WorkingPath);//设置被编辑的视频输出路径  FileUtil.getMediaDir()
                    videoClip.setStartTime(startTime);//设置剪辑开始的时间
                    videoClip.setEndTime(endTime);//设置剪辑结束的时间
                    videoClip.setOutName(fileName);//设置输出的文件名称
                    videoClip.clip();//调用剪辑并保存视频文件方法（建议作为点击保存时的操作并加入等待对话框）

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}



