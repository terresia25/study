package com.example.fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {

    ImageButton join;
    Intent intent;
    EditText join_id, join_pw, join_name, join_address, join_phone, join_email;
    final String[] return_msg = {null};
    String id, pw, name, address, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join = findViewById(R.id.join_start);
        join_id = findViewById(R.id.join_user_id_et);
        join_pw = findViewById(R.id.join_user_pw_et);
        join_name = findViewById(R.id.join_user_name_et);
        join_address = findViewById(R.id.join_user_address_et);
        join_phone = findViewById(R.id.join_user_phone_et);
        join_email = findViewById(R.id.join_user_email_et);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = join_id.getText().toString();
                pw = join_pw.getText().toString();
                name = join_name.getText().toString();
                address = join_address.getText().toString();
                phone = join_phone.getText().toString();
                email = join_email.getText().toString();
                try {
                    CustomTask task = new CustomTask("User_Join.jsp");
                    return_msg[0] = task.execute(id, pw, name, address, phone, email).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return_msg[0] = return_msg[0].replaceAll(" ", "");

                if (return_msg[0].equals("join_success")){
                    intent = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (return_msg[0].equals("id_overlap")){
                    Toast.makeText(JoinActivity.this, "ID가 중복입니다. 다른 ID를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(JoinActivity.this, "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
