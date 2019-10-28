package com.capstone.readers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.readers.item.MemberInfoItem;
import com.capstone.readers.lib.EtcLib;
import com.capstone.readers.lib.MyLog;
import com.capstone.readers.lib.RemoteLib;
import com.capstone.readers.lib.StringLib;
import com.capstone.readers.remote.RemoteService;
import com.capstone.readers.remote.ServiceGenerator;

/**
 * 시작 액티비티이며 이 액티비티에서 사용자 정보 조회 후
 * 메인 액티비티를 실행할 지, 프로필 액티비티를 실행할 지를 결정함.
 */
public class IndexActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context context;
    private SharedPreferences appData;
    private boolean saveLoginData;
    private boolean hasNetwork;

    /**
     * 레이아웃을 설정하고 인터넷에 연결되어 있는지를 확인.
     * 만약 인터넷에 연결되어 있지 않다면 showNoService() 메소드를 호출.
     * @param savedInstanceState 액티비티가 새로 생성되었을 경우, 이전 상태 값을 가지는 객체
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        context = this;
        hasNetwork = true;

        if (!RemoteLib.getInstance().isConnected(context)) {
            hasNetwork = false;
            showNoService();
            return;
        }
    }

    /**
     * 일정 시간(1.2초) 이후에 startTask() 메소드를 호출해서
     * 서버에서 사용자 정보를 조회한다.
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (hasNetwork == false) {

        }
        else {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startTask();

                }
            }, 1200);
        }
    }

    /**
     * 현재 인터넷에 접속할 수 없기 때문에 서비스를 사용할 수 없다는 메시지와
     * 화면 종료 버튼을 보여준다.
     */
    private void showNoService() {
        TextView messageText = (TextView) findViewById(R.id.message);
        messageText.setVisibility(View.VISIBLE);

        Button closeButton = (Button) findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        closeButton.setVisibility(View.VISIBLE);
    }

    /**
     * 현재 폰의 전화번호와 동일한 사용자 정보를 조회할 수 있도록
     * selectMemberInfo() 메소드를 호출함.
     * ***** ======> 계속되는 오류로 일단 바로 main 화면으로 넘어가는 것으로 수정.
     */
    public void startTask() {
        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        // 저장된 로그인 정보가 있다면 메인 액티비티로 이동
        if(saveLoginData) {
            Intent intent = new Intent(IndexActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        // 저장된 로그인 정보가 없다면 로그인 액티비티로 이동
        else {
            // 일단 바로 로그인 화면으로 가는 걸로 바꿈
            Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        // String phone = EtcLib.getInstance().getPhoneNumber(this);

        // selectMemberInfo(phone);
    }

    /**
     * 리트로핏을 활용해서 서버로부터 사용자 정보를 조회한다.
     * 사용자 정보를 조회했다면 setMemberInfoItem() 메소드를 호출하고
     * 그렇지 않다면 goProfileActivity() 메소드를 호출한다.
     * @param phone 폰의 전화번호
     */
    public void selectMemberInfo(String phone) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<MemberInfoItem> call = remoteService.selectMemberInfo(phone);
        call.enqueue(new Callback<MemberInfoItem>() {
            @Override
            public void onResponse(Call<MemberInfoItem> call, Response<MemberInfoItem> response) {
                MemberInfoItem item = response.body();

                if (response.isSuccessful() && !StringLib.getInstance().isBlank(item.name)) {
                    MyLog.d(TAG, "success " + response.body().toString());
                    setMemberInfoItem(item);
                } else {
                    MyLog.d(TAG, "not success");
                    goProfileActivity(item);
                }
            }

            @Override
            public void onFailure(Call<MemberInfoItem> call, Throwable t) {
                MyLog.d(TAG, "no internet connectivity");
                MyLog.d(TAG, "selectMemberInfo here! " + t.toString());
            }
        });
    }

    /**
     * 전달받은 MemberInfoItem을 Application 객체에 저장한다.
     * 그리고 startMain() 메소드를 호출한다.
     *
     * @param item 사용자 정보
     */
    private void setMemberInfoItem(MemberInfoItem item) {
        ((MyApp) getApplicationContext()).setMemberInfoItem(item);

        startMain();
    }

    /**
     * MainActivity를 실행하고 현재 액티비티를 종료한다.
     * 일단은 로그인 화면으로.
     */
    public void startMain() {
        Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    /**
     * 사용자 정보를 조회하지 못했다면 insertMemberPhone() 메소드를 통해
     * 전화번호를 서버에 저장하고 MainActivity를 실행한 후 // ProfileActivity를 실행한다.
     * 그리고 현재 액티비티를 종료한다.
     *
     * @param item 사용자 정보
     */
    private void goProfileActivity(MemberInfoItem item) {
        if (item == null || item.seq <= 0) {
            insertMemberPhone();
        }

        MyLog.d(TAG, "BAAAAAAAAAAAAAAAAAAAAAAAAAAAM");

        Intent intent = new Intent(IndexActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    /**
     * 폰의 전화번호를 서버에 저장한다.
     */
    private void insertMemberPhone() {
        String phone = EtcLib.getInstance().getPhoneNumber(context);
        RemoteService remoteService =
                ServiceGenerator.createService(RemoteService.class);

        Call<String> call = remoteService.insertMemberPhone(phone);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    MyLog.d(TAG, "success insert id " + response.body().toString());
                } else {
                    int statusCode = response.code();

                    ResponseBody errorBody = response.errorBody();

                    MyLog.d(TAG, "fail " + statusCode + errorBody.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLog.d(TAG, "insertMemberPhone: no internet connectivity");
            }
        });
    }
}