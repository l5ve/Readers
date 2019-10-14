package com.capstone.readers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.readers.lib.MyToast;

public class SigninActivity extends AppCompatActivity {
    private boolean saveLoginData;
    private String id;
    private String pwd;

    private EditText idText;
    private EditText pwdText;
    private EditText pwdText_ver;
    private ImageButton sign_up_Btn;

    private SharedPreferences appData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        idText = (EditText) findViewById(R.id.signin_id);
        pwdText = (EditText) findViewById(R.id.signin_pw);
        pwdText_ver = (EditText) findViewById(R.id.signin_pw_ver);
        sign_up_Btn = (ImageButton) findViewById(R.id.sign_up_Btn);


        sign_up_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id_input = idText.getText().toString();
                String pw_input = pwdText.getText().toString();
                String pw_ver = pwdText_ver.getText().toString();
                if (id_input.length() != 0 && pw_input.length() != 0){
                    if (pw_input.equals(pw_ver)){
                        MyToast.s(getApplicationContext(), getString(R.string.sign_up_done));
                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                        startActivity(intent);

                        finish();
                    }
                    else{
                        MyToast.s(getApplicationContext(), getString(R.string.different_pw));
                    }
                }
                else {
                    MyToast.s(getApplicationContext(), getString(R.string.login_warning));
                }
            }
        });
    }

    // 설정값 저장
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", false);
        editor.putString("ID", idText.getText().toString().trim());
        editor.putString("PWD", pwdText.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }
}
