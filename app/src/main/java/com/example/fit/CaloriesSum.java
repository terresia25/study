package com.example.fit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CaloriesSum extends AppCompatActivity {

    ArrayList<String> cal = ((CalorieActivity) CalorieActivity.context_main).cal;

    TextView resultall;

    Intent it;
    float sumsum = 0.0f;
    StringBuffer buf = new StringBuffer(); // 해쉬맵을 버퍼에 저장해서 텍스트 뷰로 보여줄..
    HashMap<String, String> hash = new HashMap<String, String>(); // 인텐트로 받아온 칼로리와 메뉴명을 묶을 해쉬맵

    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.calorielist);
        super.onCreate(savedInstanceState);

        resultall =  findViewById(R.id.resultall);

        Button back = (Button) findViewById(R.id.backbtn);
        TextView sum = (TextView)findViewById(R.id.sum);

        it = getIntent();


        String calorie = it.getStringExtra("calorie");
        String menu = it.getStringExtra("menu");

        int position = it.getIntExtra("position",0);

        hash.put(menu, calorie);
        buf.append(hash);
        buf.append("\n");

        //Log.w("zzz",menu);

        resultall.setText(buf);


        sumsum = sumsum + Float.parseFloat(cal.get(position)); // 총 칼로리 값 더하기

        sum.setText(String.valueOf(sumsum));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it = new Intent(CaloriesSum.this, CalorieActivity.class);
                startActivity(it);
                finish();
            }
        });



    }

}
