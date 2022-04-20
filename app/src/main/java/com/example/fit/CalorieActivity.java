package com.example.fit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class CalorieActivity extends AppCompatActivity {
    public static Object context_main;
    EditText edit;


    public ArrayList<String> cal;
    //ScrollView kcalo2;
    XmlPullParser xpp;



    public ListView Blistview;
    private ListViewAdapter_calorie adapter;

    String key="8296be970e1b4f8a81e0";
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        context_main = this;

        //Adapter 생성
        adapter = new ListViewAdapter_calorie();
        //리스트 뷰 객체 생성 및 Adapter 설정
        Blistview = (ListView) findViewById(R.id.resultlist);

        edit= (EditText)findViewById(R.id.edit);
        //final Context context;
        final Button button = (Button)findViewById(R.id.button);
        final LinearLayout kcalosum = (LinearLayout) findViewById(R.id.kcalosum);
        final LinearLayout kcalo = (LinearLayout) findViewById(R.id.kcalo);
        final TextView calsum = (TextView) findViewById(R.id.calsum);
        //final Button kcalolist = (Button)findViewById(R.id.kcalolist);

        //리스트뷰에 어댑터 설정
        Blistview.setAdapter(adapter);


    }


    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.button:

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        getXmlData();
                        //adapter.addItem("Dd","Dd");
                         runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //adapter.addItem("Dd","Dd");
                                //Blistview.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
                break;
        }
    }


    public void getXmlData(){
        //String cal=" ", men= " ";
        //adapter.addItem("Dd","Dd");<<<<<<<<<<<<<<<<이거 됨 ㅋㅋ

        StringBuffer buffer = new StringBuffer();
        String bufstr;
        cal = new ArrayList<String>();
        ArrayList<String> men = new ArrayList<String>();


        String str= edit.getText().toString();//EditText에 작성된 Text얻어오기
        String location = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding     //지역 검색 위한 변수
        int i = 1;
        int j = 1000;
        //adapter.addItem("Dd","Dd"); <<<<<<<<<<<<<<<<됨 ㅋㅋ
        while(j<=29000) {
            /*if( j == 1000 )
                adapter.addItem("Dd","Dd 이거 천");
            if( j ==  29000)
                adapter.addItem("Dd","Dd이거 이만ㄴ구천");*/

            String queryUrl = "https://openapi.foodsafetykorea.go.kr/api/8296be970e1b4f8a81e0/I2790/xml/" + i + "/" + j + "/DESC_KOR=" + location;
            //String queryUrl = "https://openapi.foodsafetykorea.go.kr/api/8296be970e1b4f8a81e0/I2790/xml/1/1000/DESC_KOR=" + location;
            try {

                URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
                InputStream is = url.openStream(); //url위치로 입력스트림 연결

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//xml파싱을 위한
                //adapter.addItem("Dd","Dd");
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기


                String tag;

                xpp.next();
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    //adapter.addItem("Dd","Dd");
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:

                            buffer.append("파싱 시작...\n\n");
                            break;

                        case XmlPullParser.START_TAG:

                            tag = xpp.getName();//테그 이름 얻어오기

                            if (tag.equals("row")) ;// 첫번째 검색결과

                            else if (tag.equals("NUTR_CONT1")) {
                                //buffer.append("열량(kcal : ");
                                xpp.next();
                                cal.add(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                //buffer.append("\n"); //줄바꿈 문자 추가
                            } else if (tag.equals("DESC_KOR")) {
                                //buffer.append("메뉴명 : )");
                                xpp.next();
                                men.add("메뉴명 : "+xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                //buffer.append("\n"); //줄바꿈 문자 추가
                            }

                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                           tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("DESC_KOR")) //buffer.append("\n");// 첫번째 검색결과종료..줄바꿈

                            break;
                    }

                    eventType = xpp.next();
                }



            } catch (Exception e) {
                e.printStackTrace();

            }
            i = i + 1000;
            j = j + 1000;

        }
        for(int k = 0; k < men.size() ; k++){
            adapter.addItem(men.get(k),"칼로리(kcal) : "+cal.get(k));
        }
            //buffer.append("파싱 끝\n");
            //return buffer.toString();//StringBuffer 문자열 객체 반환

            //bufferstr = buffer.toString().split("\n\n");
    }//getXmlData method....

}