package com.example.fit;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class CustomTask extends AsyncTask<String, Void, String> {
    private String sendMsg, receiveMsg;
    private String url_ip = "http://54.198.141.13:8080/APP_JSP/";
    private String file_location = null;

    public CustomTask(String file_location){
        this.file_location = file_location;
    }

    @Override
    // doInBackground의 매개값 문자열 배열
    protected String doInBackground(String... strings) {
        try {
            String str;
            URL url = new URL(url_ip + file_location);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //데이터를 POST 방식으로 전송
            conn.setRequestMethod("POST");

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            make_sendMsg(strings);      //전달 정보 설정 메소드

            //OutputStreamWriter에 담아 전송
            osw.write(sendMsg);
            osw.flush();

            //통신 완료 후 코드
            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                //jsp에서 보낸 값을 받는 부분
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();

            } else {
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //jsp로부터 받은 리턴 값
        return receiveMsg;
    }

    // 전달정보 생성 메소드
    private void make_sendMsg(String[] strings){
        if (file_location.equals("User_Login.jsp")){
            sendMsg = "user_id="+strings[0]+"&user_pw="+strings[1];
        }
        else if (file_location.equals("User_Join.jsp")){
            sendMsg = "user_id="+strings[0]+"&user_pw="+strings[1]+"&user_name="+strings[2]+
                    "&user_address="+strings[3]+"&user_phone="+strings[4]+"&user_email="+strings[5];
        }
        else if (file_location.equals("UserInfo_Load.jsp")){
            sendMsg = "user_id="+strings[0];
        }
        else if (file_location.equals("UserInfo_Update.jsp")){
            sendMsg = "user_id="+strings[0]+"&user_pw="+strings[1]+
                    "&user_address=" +strings[2]+"&user_phone=" +strings[3]+
                    "&user_email="+strings[4];
        }
        else if (file_location.equals("community_enroll.jsp")){
            sendMsg = "post_name="+strings[0]+"&post_title="+strings[1]+"&post_content="+strings[2]+"&date="+strings[3];
        }
        else if (file_location.equals("community_list.jsp")){
            sendMsg = "test="+strings[0];
        }
    }
}