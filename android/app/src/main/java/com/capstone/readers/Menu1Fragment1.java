package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.capstone.readers.ToonCard.ToonFragment;
import com.capstone.readers.ToonCard.ToonListAdapter;
import com.capstone.readers.lib.MyLog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/** 1-1 홈화면의 요일별 정렬 화면을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment1 extends Fragment {
    private TabLayout tabLayout;
    private ArrayList<String> DayList;

    public static Menu1Fragment1 newInstance(){
        return new Menu1Fragment1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment1_menu1, container, false);
        DayList = new ArrayList<String>();
        DayList.add("mon");
        DayList.add("tue");
        DayList.add("wed");
        DayList.add("thu");
        DayList.add("fri");
        DayList.add("sat");
        DayList.add("sun");

        // Initialize the tablayout
        tabLayout = (TabLayout) fv.findViewById(R.id.webtoon_day_tab);

        // 기본 화면으로 월요일 웹툰
        Fragment fg = ToonFragment.newInstance();
        setChildFragment(ToonFragment.newInstance(), "mon");

        // 탭의 선택 상태가 변경될 때 호출되는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                MyLog.d("Menu1Fragment", "선택된 요일 탭: " +pos);

                Fragment fg;
                fg = ToonFragment.newInstance();
                setChildFragment(fg, DayList.get(pos));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });

        return fv;
    }

    private void setChildFragment(Fragment child, String day) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        ((MyApp) getActivity().getApplication()).setDayTab();
        ((MyApp) getActivity().getApplication()).setDay(day);

        if(!child.isAdded()) {
            childFt.replace(R.id.frag1_day_container, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}
