package com.example.fit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity {
    //기본 세팅
    ImageButton home_button, food_button, training_button, mypage_button, community_btn, setting_btn, infoedit_btn, help_btn, start_training_btn;
    LinearLayout home, foodlist, training_page;
    ConstraintLayout pagelist;
    TextView unv,unv2;
    Intent select_intent = null;
    ImageButton profileimgbtn;

    //카메라 세팅
    private static final int MY_PERMISSION_CAMERA=1111;
    private static final int REQUSET_TAKE_PHOTO=2222;
    private static final int REQUSET_TAKE_ALBUM=3333;
    private static final int REQUSET_TAKE_CROP=4444;
    String mCurrentPhotoPath;
    Uri imageURI;
    Uri photoURI, albumURI;
    public static final int REQUESTCODE=101;

    //식단 세팅
    public static Object context_main;
    EditText edit;

    public ArrayList<String> cal;
    //ScrollView kcalo2;
    XmlPullParser xpp;

    public ListView Blistview;
    private ListViewAdapter_calorie adapter;

    String key="8296be970e1b4f8a81e0";
    String data;
    String return_msg = null;
    String user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        home_button = findViewById(R.id.fit_home_button);
        food_button = findViewById(R.id.fit_food_button);
        training_button = findViewById(R.id.fit_trainingpage_button);
        mypage_button = findViewById(R.id.fit_mypage_button);

        home = findViewById(R.id.fit_home);
        foodlist = findViewById(R.id.fit_foodlist);
        training_page = findViewById(R.id.fit_start_training);
        pagelist = findViewById(R.id.fit_pagelist);

        unv = findViewById(R.id.usernameview);
        unv.setText(user_id + " 님 환영합니다.");

        unv2 = findViewById(R.id.usernameview2);
        unv2.setText(user_id);

        home_button = findViewById(R.id.fit_home_button);

        community_btn = findViewById(R.id.com_btn);
        setting_btn = findViewById(R.id.exset_btn);
        infoedit_btn = findViewById(R.id.infoedit_btn);
        help_btn = findViewById(R.id.help_btn);

        profileimgbtn = findViewById(R.id.profileimgbtn);


        start_training_btn = findViewById(R.id.fit_training_start_button);

        // 식단 세팅
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



        //사용자 페이지 버튼 리스너
        infoedit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_intent = new Intent(getApplicationContext(), InfoEditActivity.class);

                try {
                    CustomTask task = new CustomTask("UserInfo_Load.jsp");
                    return_msg = task.execute(user_id).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String[] array = return_msg.split("#");
                select_intent.putExtra("address", array[0]);
                select_intent.putExtra("phone", array[1]);
                select_intent.putExtra("email", array[2]);
                select_intent.putExtra("user_id",user_id);
                startActivity(select_intent);
            }
        });

        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_intent = new Intent(getApplicationContext(), SettingbtnActivity.class);
                startActivity(select_intent);
            }
        });

        community_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_intent = new Intent(getApplicationContext(), CommunityActivity.class);
                select_intent.putExtra("user_id", user_id);
                startActivity(select_intent);
            }
        });

        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_intent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(select_intent);
            }
        });


        //운동 시작 버튼 리스너
        start_training_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_intent = new Intent(MainActivity.this, TraininglistActivity.class);
                startActivity(select_intent);
            }
        });


        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setVisibility(View.VISIBLE);
                foodlist.setVisibility(View.INVISIBLE);
                training_page.setVisibility(View.INVISIBLE);
                pagelist.setVisibility(View.INVISIBLE);
                home_button.setImageResource(R.drawable.home_icon_select);
                food_button.setImageResource(R.drawable.food_icon);
                training_button.setImageResource(R.drawable.training_icon);
                mypage_button.setImageResource(R.drawable.mypage_icon);
            }
        });

        food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setVisibility(View.INVISIBLE);
                foodlist.setVisibility(View.VISIBLE);
                //Intent intent = new Intent(MainActivity.this, CalorieActivity.class);
                //startActivity(intent);
                training_page.setVisibility(View.INVISIBLE);
                pagelist.setVisibility(View.INVISIBLE);
                home_button.setImageResource(R.drawable.home_icon);
                food_button.setImageResource(R.drawable.food_icon_select);
                training_button.setImageResource(R.drawable.training_icon);
                mypage_button.setImageResource(R.drawable.mypage_icon);
            }
        });

        training_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setVisibility(View.INVISIBLE);
                foodlist.setVisibility(View.INVISIBLE);
                training_page.setVisibility(View.VISIBLE);
                pagelist.setVisibility(View.INVISIBLE);
                home_button.setImageResource(R.drawable.home_icon);
                food_button.setImageResource(R.drawable.food_icon);
                training_button.setImageResource(R.drawable.training_icon_select);
                mypage_button.setImageResource(R.drawable.mypage_icon);
            }
        });

        mypage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.setVisibility(View.INVISIBLE);
                foodlist.setVisibility(View.INVISIBLE);
                training_page.setVisibility(View.INVISIBLE);
                pagelist.setVisibility(View.VISIBLE);
                home_button.setImageResource(R.drawable.home_icon);
                food_button.setImageResource(R.drawable.food_icon);
                training_button.setImageResource(R.drawable.training_icon);
                mypage_button.setImageResource(R.drawable.mypage_icon_reverse);
            }
        });



        // 아래로 카메라 및 프로필 이미지를 위한 코드
        // 카메라 event 코드
        /*int permission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (permission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
        } else {
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent1, 1);
        }*/
        profileimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pop = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.menu, pop.getMenu());

                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.one:
                                captureCamera();
                                break;
                            case R.id.two:
                                getAlbum();
                                break;
                            case R.id.three:
                                profileimgbtn.setImageResource(R.drawable.basic_profile_img);
                        }
                        return true;
                    }
                });
                pop.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        if (requestCode == 0) {
            if (grantResults[0] == 0) {
                Toast.makeText(this, "카메라 권한 승인 완료", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "카메라 권한 승인 거절", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==1){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void captureCamera(){
        String state= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                File photoFile=null;
                try{
                    photoFile=createImageFile();
                }catch(IOException e){
                    e.printStackTrace();
                }
                if(photoFile!=null){
                    Uri providerUri= FileProvider.getUriForFile(this,getPackageName(),photoFile);
                    imageURI=providerUri;

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,providerUri);
                    startActivityForResult(takePictureIntent,REQUSET_TAKE_PHOTO);
                }
            }else{
                Toast.makeText(this,"접근 불가능 합니다.",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private File createImageFile() throws IOException{
        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String  imageFilename="JPEG_"+timestamp+".jpg";
        File imageFile=null;
        File storageDir=new File(Environment.getExternalStorageDirectory()+"/Pictures");

        if(!storageDir.exists()){
            storageDir.mkdirs();
        }

        imageFile=new File(storageDir,imageFilename);
        mCurrentPhotoPath=imageFile.getAbsolutePath();

        return imageFile;
    }


    private void getAlbum(){
        Intent intent1=new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        intent1.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent1,REQUSET_TAKE_ALBUM);
    }

    public void cropImage(){
        Intent cropIntent=new Intent("com.android.camera.action.CROP");
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI,"image/*");
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("aspectY",1);
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("ouput",albumURI);
        startActivityForResult(cropIntent,REQUSET_TAKE_CROP);
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





