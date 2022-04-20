package com.example.fit;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class TrainingActivity extends AppCompatActivity {
    VideoView vv;
    //동영상 저장
    Integer[] videoArray = {R.raw.jinseok, R.raw.jinseok_1, R.raw.jinseok_2};

    ArrayList videoarry = new ArrayList<>(Arrays.asList(videoArray));
    TextView alertMessage;
    ImageButton preBtn, nextBtn;
    int position;
    Uri videoUri;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        vv = findViewById(R.id.exV);

        alertMessage = (TextView) findViewById(R.id.alertMessage);
        preBtn = (ImageButton) findViewById(R.id.preVideo);
        nextBtn = (ImageButton) findViewById(R.id.nextVideo);


        Intent it = getIntent();


        position = it.getIntExtra("position", 0);

        Log.d("position", String.valueOf(position));
        Log.d("arrylist", String.valueOf(videoarry.get(position)));

        
        vStart();


        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    position--;

                    Log.d("position", String.valueOf(position));
                    Log.d("arrylist", String.valueOf(videoarry.get(position)));
                } catch (Exception e) {
                    //if(position<0)
                    position = videoarry.size() - 1;
                }
                vStart();

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    position++;

                    Log.d("position", String.valueOf(position));
                    Log.d("arrylist", String.valueOf(videoarry.get(position)));
                } catch (Exception e) {
                    //if(position<0)
                    position = 0;
                }

                vStart();
            }
        });
    }//onCreate ..

    void vStart() {
        //앱 내부에서 영상 꺼내올 때
        videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoarry.get(position));


        //비디오뷰의 재생, 일시정지 등을 할 수 있는 '컨트롤바'를 붙여주는 작업
        vv.setMediaController(new MediaController(this));

        //VideoView가 보여줄 동영상의 경로 주소(Uri) 설정하기
        vv.setVideoURI(videoUri);

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //Log.d("sfsfsf ",videoarry.get(position).toString());
                //비디오 시작
                vv.start();
                vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        vv.start();
                    }
                });
            }
        });
    }

        //화면에 안보일때...
        @Override
        protected void onPause() {
            super.onPause();

            //비디오 일시 정지
            if(vv!=null && vv.isPlaying()) vv.pause();
        }
        //액티비티가 메모리에서 사라질때..
        @Override
        protected void onDestroy() {
            super.onDestroy();
            //
            if(vv!=null) vv.stopPlayback();
        }
    }

