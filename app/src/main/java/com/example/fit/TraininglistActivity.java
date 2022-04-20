package com.example.fit;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TraininglistActivity extends AppCompatActivity {


    public static Object context_main;
    private ListViewItem data;
    private ArrayList<ListViewItem> arrays;
    public ListView Elistview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traininglist);


        context_main = this;

        EListViewAdapter adapter;
        arrays = new ArrayList<ListViewItem>();

        initialData();




        adapter = new EListViewAdapter(arrays);
        Log.i("kani", "test");


        Elistview = (ListView) findViewById(R.id.exer_list);



        Elistview.setAdapter(adapter);

    }

    private void initialData(){
        data = new ListViewItem();
        data.setData(getResources().getDrawable(R.drawable.jinseok),"다리운동", "허벅지를 불태워요 ㅎㅎ");
        arrays.add(data);

        data = new ListViewItem();
        data.setData(getResources().getDrawable(R.drawable.jinseok),"팔뚝운동", "팔뚝을 조져욯ㅎ");
        arrays.add(data);

        data = new ListViewItem();
        data.setData(getResources().getDrawable(R.drawable.jinseok),"복근운동", "식스팩을 만들어봐요 ㅎㅎ");
        arrays.add(data);
    }




}
