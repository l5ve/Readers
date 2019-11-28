package com.capstone.readers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.readers.item.LoginData;
import com.capstone.readers.item.LoginResponse;
import com.capstone.readers.lib.MyToast;
import com.capstone.readers.security.SecurityUtil;

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
    private TextView mFindId;
    private TextView mFindPw;

    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 설정값 불러오기
        load();

        idText = (EditText) findViewById(R.id.idText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        sign_up_Btn = (ImageButton) findViewById(R.id.sign_up_page_Btn);
        mFindId = (TextView) findViewById(R.id.forgot_id);
        mFindPw = (TextView) findViewById(R.id.forgot_pw);

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

        mFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                String[] address = {"readers.cau@gmail.com"};
                email.setType("plain/Text");
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "[아이디 찾기] 제목을 작성해주세요.");
                email.putExtra(Intent.EXTRA_TEXT, "문의 내용을 작성해주세요.\n");
                email.setType("message/rfc822");
                startActivity(email);
            }
        });

        mFindPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent email = new Intent(Intent.ACTION_SEND);
                String[] address = {"readers.cau@gmail.com"};
                email.setType("plain/Text");
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "[비밀번호 찾기] 제목을 작성해주세요.");
                email.putExtra(Intent.EXTRA_TEXT, "문의 내용을 작성해주세요.\n");
                email.setType("message/rfc822");
                startActivity(email);
            }
        });

        // 이전에 로그인 정보를 저장시킨 기록이 있다면
        if (saveLoginData) {
            idText.setText(saved_id);
            pwdText.setText(saved_pw);
            checkBox.setChecked(true);
            startLogin(new LoginData(saved_id, saved_pw));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGNIN) {
            Log.d("회원가입/", "회원가입 성공 후 인텐트 로그인 액티비티에 전달");
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Log.d("LoginActivity", "Get the result from SigninActivity and go to the main activity");

                finish();
            }
        }
    }

    private void attemptLogin(){
        idText.setError(null);
        pwdText.setError(null);

        String id = idText.getText().toString();
        String pwd = pwdText.getText().toString();

        SecurityUtil securityUtil = new SecurityUtil();

        String enc_pwd = securityUtil.encryptSHA256(pwd);

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
            startLogin(new LoginData(id, enc_pwd));
        }

    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    Log.d("RESPONSE_BODY", "RESPONSE_BODY_IS_NOT_NULL");
                    LoginResponse result = response.body();
                    if (result.getCode() != 200)
                        MyToast.l(getApplicationContext(), result.getMessage());

                    if (result.getCode() == 200) {
                        if (checkBox.isChecked()){
                            save(true);
                        } else {
                            save(false);
                        }
                        MyToast.l(getApplicationContext(), result.getMessage());
                        ((MyApp) getApplication()).setUser_name(result.getName().trim());
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
                Log.e(getString(R.string.login_error), t.getMessage());
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
    private void save(boolean save) {
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        SecurityUtil securityUtil = new SecurityUtil();
        String enc_pwd = securityUtil.encryptSHA256(pwdText.getText().toString().trim());

        ((MyApp) getApplication()).setUser_id(idText.getText().toString().trim());
        ((MyApp) getApplication()).setUser_pw(enc_pwd);
        ((MyApp) getApplication()).setSavedData(save);
    }

    // 설정값을 불러오는 함수
    private void load() {
        saveLoginData = ((MyApp) getApplication()).getSavedData();
        saved_id = ((MyApp) getApplication()).getUser_id();
        saved_pw = ((MyApp) getApplication()).getUser_pw();
    }
}