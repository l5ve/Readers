package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.readers.MypageSubscribe.MypageSubscribeFragment;
import com.capstone.readers.lib.MyLog;
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
        DayList.add("end");

        tabLayout = (TabLayout) fv.findViewById(R.id.mypage_day_tab);

        Fragment fg = MypageSubscribeFragment.newInstance();
        setChildFragment(fg, "mon");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                MyLog.d("Menu3Fragment1", "선택된 탭: " + pos);

                Fragment fg;
                fg = MypageSubscribeFragment.newInstance();
                setChildFragment(fg, DayList.get(pos));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return fv;
    }

    private void setChildFragment(Fragment child, String day) {
        ((MyApp) getActivity().getApplication()).setMypagesubscribetab(day);

        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();


        if(!child.isAdded()) {
            childFt.replace(R.id.frag3_day_container, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }

    }
}
