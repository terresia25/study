package com.example.fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    Button join;
    ImageButton login;
    EditText id, pw;
    String login_id, login_pw;
    Intent intent = null;
    final String[] return_msg = {null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.id_et);
        pw = findViewById(R.id.pw_et);
        login = findViewById(R.id.login_btn);
        join = findViewById(R.id.join_btn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_id = id.getText().toString();
                login_pw = pw.getText().toString();
                try {
                    // jsp로 부터 리턴값을 받을 경우 task.execute().get();
                    // .get()에서 에러가 발생할 수 있기때문에 try/catch 로 감싼다.
                    // execute() 의 매개값은 doInBackground 에서 사용할 문자열 배열에 필요한 값을 순서대로 넣는다.
                    CustomTask task = new CustomTask("User_Login.jsp");
                    return_msg[0] = task.execute(login_id, login_pw).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return_msg[0] = return_msg[0].replaceAll(" ", "");
                Log.i("통신 결과", return_msg[0]);


                if (return_msg[0].equals("success")){
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user_id", login_id);
                    startActivity(intent);
                    finish();
                }
                else if(return_msg[0].equals("password")){
                    Toast.makeText(LoginActivity.this, "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                }
                else if(return_msg[0].equals("id")){
                    Toast.makeText(LoginActivity.this, "계정이 존재하지 않거나 ID가 틀립니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}
