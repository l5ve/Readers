package com.capstone.readers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.readers.lib.RemoteLib;

/**
 * 시작 액티비티이며 이 액티비티에서 사용자 정보 조회 후
 * 메인 액티비티를 실행할 지, 프로필 액티비티를 실행할 지를 결정함.
 */
public class IndexActivity extends AppCompatActivity {
    Context context;
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
     * 일정 시간(0.8초) 이후에 startTask() 메소드를 호출해서
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
            }, 500);
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

    public void startTask() {
        // 설정값 불러오기
        saveLoginData = ((MyApp) getApplication()).getSavedData();
        // 저장된 로그인 정보가 있다면 메인 액티비티로 이동
        if(saveLoginData) {
            Intent intent = new Intent(IndexActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        // 저장된 로그인 정보가 없다면 로그인 액티비티로 이동
        else {
            Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}