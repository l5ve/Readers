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

import com.capstone.readers.item.LoginResponse;
import com.capstone.readers.item.LoginData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SIGNIN = 101;

    private boolean saveLoginData;
    private String saved_id;
    private String saved_pw;

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

        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();


        idText = (EditText) findViewById(R.id.idText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        sign_up_Btn = (ImageButton) findViewById(R.id.sign_up_page_Btn);


        // 이전에 로그인 정보를 저장시킨 기록이 있다면
        if (saveLoginData) {
            idText.setText(saved_id);
            pwdText.setText(saved_pw);
            attemptLogin();
        }

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
        }else if(!isPasswordValid(pwd)) {
            MyToast.s(getApplicationContext(), R.string.pwd_length_warning);
            cancel = true;
        }

        // ID 유효성 검사
        if (id.isEmpty()) {
            MyToast.s(getApplicationContext(), R.string.login_warning);
            cancel = true;
        } else if(!isIdValid(id)) {
            MyToast.s(getApplicationContext(), R.string.id_length_warning);
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
                if (response.isSuccessful()){
                    Log.d("RESPONSE_BODY", "RESPONSE_BODY_IS_NOT_NULL");
                    LoginResponse result = response.body();
                    Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                    if (result.getCode() == 200) {
                        if (checkBox.isChecked()){
                            save();
                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else{
                    ResponseBody errorBody = response.errorBody();
                    Log.e("RESPONSE_BODY", "RESPONSE_BODY_IS_NULL");
                    MyToast.s(getApplicationContext(), R.string.login_error);
                }
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

    private boolean isIdValid(String id) {
        return id.length() >= 5;
    }


    private boolean isPasswordValid(String password) {
        return password.length() >= 5;
    }

    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", idText.getText().toString().trim());
        editor.putString("PWD", pwdText.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        saved_id = appData.getString("ID", "");
        saved_pw = appData.getString("PWD", "");
    }

}