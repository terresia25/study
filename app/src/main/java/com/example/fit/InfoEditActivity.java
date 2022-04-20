package com.example.fit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class InfoEditActivity extends AppCompatActivity {

    TextView unv;
    EditText edit_pw, edit_address, edit_phone, edit_email;
    String recv_pw, recv_address, recv_phone, recv_email;
    String return_msg = null;
    Intent intent;
    String user_id,add_value,em_value,pho_value;
    ImageButton sendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_edit);

        intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        sendbtn = findViewById(R.id.update_start);
        edit_pw = findViewById(R.id.user_pw_edit);
        edit_address = findViewById(R.id.user_address_edit);
        edit_phone = findViewById(R.id.user_phone_edit);
        edit_email = findViewById(R.id.user_email_edit);

        add_value = intent.getStringExtra("address");
        pho_value = intent.getStringExtra("phone");
        em_value = intent.getStringExtra("email");


        edit_address.setText(add_value);
        edit_phone.setText(pho_value);
        edit_email.setText(em_value);


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recv_pw =edit_pw.getText().toString();
                recv_address = edit_address.getText().toString();
                recv_phone = edit_phone.getText().toString();
                recv_email = edit_email.getText().toString();
                try {
                    CustomTask task = new CustomTask("UserInfo_Update.jsp");
                    return_msg = task.execute(  user_id,recv_pw, recv_address,  recv_phone,  recv_email).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (return_msg.equals("edit_success")){
                    intent = new Intent(InfoEditActivity.this, MainActivity.class);
                    Toast.makeText(InfoEditActivity.this, "수정 되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(InfoEditActivity.this, "서버 오류", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}