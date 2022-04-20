package com.example.fit;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommunityActivity extends AppCompatActivity {
    private ListView Blistview;
    private ListViewAdapter adapter;
    private Button btnWriting, okbtn;
    private RelativeLayout writebtnxml;
    private LinearLayout writexml, comuxml;
    int i=0;
    String[] content_list;

    SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

    EditText eidt_title, edit_content;

    String return_msg = null;

    Intent intent;
    String user_id;

    String post_name, post_content, post_title, date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        //Adapter 생성
        adapter = new ListViewAdapter();
        //리스트 뷰 객체 생성 및 Adapter 설정
        Blistview = (ListView) findViewById(R.id.listview);
        btnWriting = (Button) findViewById((R.id.writingGeul)); // 글쓰기 버튼
        writebtnxml = findViewById(R.id.writebtnxml); // 글쓰기버튼감싸는 렐러티브 레이아웃
        writexml = findViewById(R.id.writexml); //글쓰는 텍스트 에디트 창
        comuxml = findViewById(R.id.comuxml);
        okbtn = findViewById(R.id.okbtn);
        intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        edit_content = findViewById(R.id.post_content);
        eidt_title = findViewById(R.id.post_title);

        final View header = getLayoutInflater().inflate(R.layout.listview_header,null,false);

        //리스트뷰에 어댑터 설정정
        Blistview.setAdapter(adapter);

        Blistview.addHeaderView(header);

        //addItem함수로 리스트 뷰 작성 -> @@@@#####근데 디비로 연결해서 꺼내야 됨. 아래는 예시#####@@@@@@
        /*
        adapter.addItem("예시", "혜주");
        adapter.addItem("운동열시미", "옒");
        adapter.addItem("에베베베베ㅔ벱", "성진");
        adapter.addItem("ㅇㅅㅇ", "지원");
        */

        post_list();

        //글쓰기 버튼 클릭시
        btnWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writexml.setVisibility(View.VISIBLE);
                writebtnxml.setVisibility(View.INVISIBLE);
                comuxml.setVisibility(View.INVISIBLE);

            }
        });

        // 확인 버튼 누를시 데이터 저장하고 게시판에 추가
        okbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                post_name = user_id;
                Date time = new Date();
                date = format1.format(time);

                post_title = eidt_title.getText().toString();
                post_content = edit_content.getText().toString();
                Log.d("date", date);
                Log.d("content", post_content);
                post_register();
                //  보낸 값들을 jsp에서 db에 넣어주면 된다.

                post_list();

                //  넣어준 값을 포함해서 리스트에 띄워준다.
                writexml.setVisibility(View.INVISIBLE);
                writebtnxml.setVisibility(View.VISIBLE);
                comuxml.setVisibility(View.VISIBLE);

            }
        });;




    }
    void post_register(){
        try {
            CustomTask task = new CustomTask("community_enroll.jsp");
            return_msg = task.execute(post_name,post_title,post_content,date).get();
            Log.d("id", return_msg);
        } catch ( Exception e){
            e.printStackTrace();
        }
    }
    void post_list(){
        try{
            CustomTask task = new CustomTask("community_list.jsp");
            return_msg = task.execute("test").get();
            Log.d("return", return_msg);
            content_list = return_msg.split("#");
            while(i < content_list.length) {
                adapter.addItem(content_list[i+2], content_list[i+1]);
                i = i+2;
                if( i == content_list.length-1)
                    break;
            }
            Blistview.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
