package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/** 마이페이지 화면의 요일별 구독 웹툰을 나타내는 프래그먼트
 */
public class Menu3Fragment1 extends Fragment {
    private TabLayout tabLayout;
    private ArrayList<String> DayList;

    public static Menu3Fragment1 newInstance(){
        return new Menu3Fragment1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment1_menu3, container, false);
        DayList = new ArrayList<String>();
        DayList.add("mon");
        DayList.add("tue");
        DayList.add("wed");
        DayList.add("thu");
        DayList.add("fri");
        DayList.add("sat");
        DayList.add("sun");

        tabLayout = (TabLayout) fv.findViewById(R.id.mypage_day_tab);






        return fv;
    }
}
