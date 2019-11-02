package com.capstone.readers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.capstone.readers.lib.MyLog;
import com.capstone.readers.lib.MyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SIGNIN = 101;

    private EditText idText;
    private EditText pwdText;
    private CheckBox checkBox;
    private ImageButton loginBtn;
    private ImageButton sign_up_Btn;

    private ServiceApi service;

    private SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText) findViewById(R.id.idText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        sign_up_Btn = (ImageButton) findViewById(R.id.sign_up_page_Btn);

        service = RetrofitClient.getClient().create(ServiceApi.class);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        sign_up_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SIGNIN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGNIN) {
            Log.d("회원가입/", "회원가입 성공 후 인텐트 로그인 액티비티에 전달");
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }
    }

    private void attemptLogin(){
        idText.setError(null);
        pwdText.setError(null);

        String id = idText.getText().toString();
        String pwd = pwdText.getText().toString();

        boolean cancel = false;

        // 비밀번호 유효성 검사
        if (pwd.isEmpty()) {
            MyToast.s(getApplicationContext(), R.string.login_warning);
            cancel = true;
        }
        // ID 유효성 검사
        if (id.isEmpty()) {
            MyToast.s(getApplicationContext(), R.string.login_warning);
            cancel = true;
        }

        if (!cancel) {
            startLogin(new LoginData(id, pwd));
        }

    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }

    private boolean isPasswordConsistent(String pw, String pw_ver) {
        if (pw.equals(pw_ver))
            return true;
        else
            return false;
    }
}