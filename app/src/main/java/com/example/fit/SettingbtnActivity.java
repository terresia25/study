package com.example.fit;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;


public class SettingbtnActivity extends AppCompatActivity {
    //  PieChart pieChart;
    ImageButton back;
    TextView extime, accuracy, wholebody, shoulder, lbody, core, arm;
    /*int rightarm;
    int leftarm;
    int rightleg;
    int leftleg;
    int core;*/
    int value[];
    TextView [] text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingbtn);
       // pieChart();
        back = findViewById(R.id.backbtn);
        extime = findViewById(R.id.extime);
        accuracy = findViewById(R.id.accuracy);
        wholebody = findViewById(R.id.wholebody);
        shoulder = findViewById(R.id.shoulder);
        lbody = findViewById(R.id.lbody);
        core = findViewById(R.id.core);
        arm = findViewById(R.id.arm);
        value = new int[]{16, 22, 38, 84, 52};
        text = new TextView[]{wholebody, shoulder, lbody, core,arm};

        for(int i = 0; i<text.length; i++) {
            startCountAnimation(value[i],text[i]);
        }






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }



/*
    private void pieChart()
    {
        pieChart = (PieChart)findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5); // 위치조정값

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setEntryLabelTextSize(30f); //  차트라벨 글씨크기
        pieChart.setEntryLabelColor(Color.WHITE); // 라벨 컬러

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        rightarm = 20;
        rightleg = 20;
        leftarm = 20;
        leftleg = 20;
        core = 20;



        yValues.add(new PieEntry(core,"core"));
        yValues.add(new PieEntry(leftarm,"LeftArm"));
        yValues.add(new PieEntry(rightarm,"India"));
        yValues.add(new PieEntry(leftleg,"Russia"));
        yValues.add(new PieEntry(rightleg,"Korea"));

        Description description = new Description();
        description.setText("세계 국가"); //라벨
        description.setTextSize(15); // 라벨글씨크기기
         pieChart.setDescription(description);

        pieChart.animateY(2500, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"Countries");
        dataSet.setSliceSpace(3f); // 조각당 간격
        dataSet.setSelectionShift(5f);// 원의크기
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS); // 조각당 컬러 랜덤



        PieData data = new PieData((dataSet));
        data.setValueTextSize(15); // 퍼센트 글자크기
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);


    }*/
private void startCountAnimation(int value, final TextView text) {

        ValueAnimator animator = ValueAnimator.ofInt(0, value);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                text.setText(animation.getAnimatedValue().toString());

            }
        });
        animator.start();
    }
}

