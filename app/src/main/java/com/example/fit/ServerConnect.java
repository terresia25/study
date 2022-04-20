package com.example.fit;

import android.os.Handler;
import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnect {
    // 소켓통신에 필요한것
    private Handler mHandler;

    private Socket socket;

    private DataOutputStream dos;
    private DataInputStream dis;

    private String ip = "54.198.141.13"; // IP 번호
    private int port = 9999; // port 번호

    // 로그인 정보 db에 넣어주고 연결시켜야 함.
    void connect(){
        mHandler = new Handler();
        Log.w("connect","연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
                // ip받기
                String newip = ip;

                // 서버 접속
                try {
                    socket = new Socket(newip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결요청");

                try {
                    dos = new DataOutputStream(socket.getOutputStream()); // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream());  // input에 받을꺼 넣어짐
                    dos.writeUTF("안드로이드에서 서버로 연결요청");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

                // 서버에서 계속 받아옴
                try {
                    byte[] data;
                    while(true){
                        data = new byte[15];
                        dis.read(data);

                        String msg = "";

                        for(byte b : data){
                            if(b == '\0'){
                                break;
                            }
                            else {
                                char c = (char) b;
                                msg = msg + c;
                            }
                        }
                        Log.w("서버로 받아온 값","" + msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }

}
