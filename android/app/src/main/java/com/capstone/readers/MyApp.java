package com.capstone.readers;

import android.app.Application;
import android.os.StrictMode;

/**
 * 앱 전역에서 사용할 수 있는 클래스
 * Application 클래스를 상속하고 있으며,
 * 사용자 정보인 MemberInfoItem을 저장하고 반환하는 역할
 * 이를 통해 앱의 어디서나 사용자 정보에 접근할 수 있다.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // File UriExposedException 문제를 해결하기 위한 코드
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}