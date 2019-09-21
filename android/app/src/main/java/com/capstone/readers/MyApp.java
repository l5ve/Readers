package com.capstone.readers;

import android.app.Application;
import android.os.StrictMode;

import com.capstone.readers.item.MemberInfoItem;
import com.capstone.readers.item.WebtoonInfoItem;

/**
 * 앱 전역에서 사용할 수 있는 클래스
 * Application 클래스를 상속하고 있으며,
 * 사용자 정보인 MemberInfoItem을 저장하고 반환하는 역할
 * 이를 통해 앱의 어디서나 사용자 정보에 접근할 수 있다.
 */
public class MyApp extends Application {
    private MemberInfoItem memberInfoItem;
    private WebtoonInfoItem webtoonInfoItem;

    @Override
    public void onCreate() {
        super.onCreate();

        // File UriExposedException 문제를 해결하기 위한 코드
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public MemberInfoItem getMemberInfoItem() {
        if (memberInfoItem == null) memberInfoItem = new MemberInfoItem();

        return memberInfoItem;
    }

    public void setMemberInfoItem(MemberInfoItem item){
        this.memberInfoItem = item;
    }

    public int getMemberSeq() {
        return memberInfoItem.seq;
    }

    public void setWebtoonInfoItem(WebtoonInfoItem webtoonInfoItem) {
        this.webtoonInfoItem = webtoonInfoItem;
    }

    public WebtoonInfoItem getWebtoonInfoItem() {
         return webtoonInfoItem;
    }
}
