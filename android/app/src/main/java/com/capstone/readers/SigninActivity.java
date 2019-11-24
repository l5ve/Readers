package com.capstone.readers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.readers.Taste.TasteFragment;
import com.capstone.readers.item.UserTasteData;
import com.capstone.readers.lib.MyToast;
import com.capstone.readers.item.JoinResponse;
import com.capstone.readers.item.JoinData;
import com.capstone.readers.security.SecurityUtil;
import java.security.Security;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {
    private EditText idText;
    private EditText nameText;
    private EditText pwdText;
    private EditText pwdText_ver;
    private ImageButton sign_up_Btn;
    private boolean[] taste_selected;
    private String[] genre_list;

    private ServiceApi service;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        idText = (EditText) findViewById(R.id.signin_id);
        nameText = (EditText) findViewById(R.id.signin_nickname);
        pwdText = (EditText) findViewById(R.id.signin_pw);
        pwdText_ver = (EditText) findViewById(R.id.signin_pw_ver);
        sign_up_Btn = (ImageButton) findViewById(R.id.sign_up_Btn);

        service = RetrofitClient.getClient().create(ServiceApi.class);
        genre_list = ((MyApp) getApplication()).getGenre_list();

        sign_up_Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               attemptSignin();
            }
        });

        /* 사용자가 취향을 고를 수 있는 button을 담은 recyclerview */
        Fragment fg = TasteFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.taste_frame, fg).addToBackStack(null).commit();
    }

    private void attemptSignin(){
        idText.setError(null);
        nameText.setError(null);
        pwdText.setError(null);

        String id = idText.getText().toString();
        String name = nameText.getText().toString();
        String pwd = pwdText.getText().toString();
        String pwd_ver = pwdText_ver.getText().toString();

        SecurityUtil securityUtil = new SecurityUtil();

        String enc_pwd = securityUtil.encryptSHA256(pwd);

        boolean cancel = false;

        // 비밀번호 유효성 검사
        if (pwd.isEmpty()) {
            MyToast.s(getApplicationContext(), R.string.login_warning);
            cancel = true;
        } else if(!isPasswordValid(pwd)) {
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
        } else if(!isIdValid(id)) {
            MyToast.s(getApplicationContext(), R.string.id_length_warning);
            cancel = true;
        }


        // 닉네임 유효성 검사
        if (name.isEmpty()) {
            MyToast.s(getApplicationContext(), R.string.nickname_warning);
            cancel = true;
        }

        if (!cancel) {
            startJoin(new JoinData(id, name, enc_pwd));
        }
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE_BODY", "RESPONSE_BODY_IS_NOT_NULL");
                    JoinResponse result = response.body();
                    MyToast.s(getApplicationContext(), result.getMessage());

                    // 200: 회원가입 성공 시 받는 코드
                    if (result.getCode() == 200) {

                        SecurityUtil securityUtil = new SecurityUtil();
                        String enc_pwd = securityUtil.encryptSHA256(pwdText.getText().toString().trim());

                        ((MyApp) getApplication()).setUser_id(idText.getText().toString().trim());
                        ((MyApp) getApplication()).setUser_pw(enc_pwd);
                        ((MyApp) getApplication()).setSavedData(true);
                        ((MyApp) getApplication()).setUser_name(nameText.getText().toString().trim());

                        setTaste();

                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        Log.d("SigninActivity", "Set result_ok and go back to loginActivity");
                        finish();
                    }
                } else{
                    Log.e("RESPONSE_BODY", "RESPONSE_BODY_IS_NULL");
                    MyToast.s(getApplicationContext(), R.string.signin_error);
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(SigninActivity.this, R.string.signin_error, Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }


    private void setTaste() {
        String user_id = idText.getText().toString().trim();
        taste_selected = ((MyApp) getApplication()).getGenre_selected();
        UserTasteData data;

        for (int i = 0; i < taste_selected.length; i++) {
            if (taste_selected[i] == true) {
                data = new UserTasteData(user_id, genre_list[i]);
                Log.d("SigninActivity", "setTaste: " + user_id + genre_list[i]);
                service.setTaste(data).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200) {
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        }

    }

    private boolean isIdValid(String id) {
        return id.length() >= 5;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 5;
    }

    private boolean isPasswordConsistent(String pw, String pw_ver) {
        if (pw.equals(pw_ver))
            return true;
        else
            return false;
    }
}
