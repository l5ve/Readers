package com.capstone.readers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.readers.item.JoinData;
import com.capstone.readers.item.JoinResponse;
import com.capstone.readers.lib.MyToast;
import com.capstone.readers.remote.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {
    private EditText idText;
    private EditText nameText;
    private EditText pwdText;
    private EditText pwdText_ver;
    private ImageButton sign_up_Btn;

    private ServiceApi service;

    private SharedPreferences appData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        idText = (EditText) findViewById(R.id.signin_id);
        nameText = (EditText) findViewById(R.id.signin_nickname);
        pwdText = (EditText) findViewById(R.id.signin_pw);
        pwdText_ver = (EditText) findViewById(R.id.signin_pw_ver);
        sign_up_Btn = (ImageButton) findViewById(R.id.sign_up_Btn);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        sign_up_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               attemptSignin();
            }
        });
    }

    private void attemptSignin(){
        idText.setError(null);
        nameText.setError(null);
        pwdText.setError(null);

        String id = idText.getText().toString();
        String name = nameText.getText().toString();
        String pwd = pwdText.getText().toString();
        String pwd_ver = pwdText_ver.getText().toString();

        boolean cancel = false;

        // 비밀번호 유효성 검사
        if (pwd.isEmpty()) {
            MyToast.s(getApplicationContext(), R.string.login_warning);
            cancel = true;
        } else if(isPasswordValid(pwd)) {
            MyToast.s(getApplicationContext(), R.string.pwd_length_warning);
            cancel = true;
        } else if(!isPasswordConsistent(pwd, pwd_ver)) {
            MyToast.s(getApplicationContext(),  R.string.different_pw);
            cancel = true;
        }

        // ID 유효성 검사
        if (id.isEmpty()) {
            MyToast.s(getApplicationContext(), R.string.login_warning);
            cancel = true;
        } else if(isIdValid(id)) {
            MyToast.s(getApplicationContext(), R.string.id_length_warning);
            cancel = true;
        }


        // 닉네임 유효성 검사
        if (name.isEmpty()) {
            MyToast.s(getApplicationContext(), R.string.nickname_warning);
            cancel = true;
        }

        if (!cancel) {
            startJoin(new SigninData(id, name, pwd));
        }
    }

    private void startJoin(SigninData data) {
        service.userJoin(data).enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                SigninResponse result = response.body();
                Toast.makeText(SigninActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                // 200: 회원가입 성공 시 받는 코드
                if (result.getCode() == 200) {
                    MyToast.l(getApplicationContext(), R.string.sign_up_done);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                Toast.makeText(SigninActivity.this, R.string.signin_error, Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }

    private boolean isIdValid(String id) {
        return id.length() < 6;
    }


    private boolean isPasswordValid(String password) {
        return password.length() < 6;
    }

    private boolean isPasswordConsistent(String pw, String pw_ver) {
        if (pw.equals(pw_ver))
            return true;
        else
            return false;
    }
}
