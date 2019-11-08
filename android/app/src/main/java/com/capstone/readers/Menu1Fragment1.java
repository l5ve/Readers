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

import com.capstone.readers.Day.Friday;
import com.capstone.readers.Day.Monday;
import com.capstone.readers.Day.Saturday;
import com.capstone.readers.Day.Sunday;
import com.capstone.readers.Day.Thursday;
import com.capstone.readers.Day.Tuesday;
import com.capstone.readers.Day.Wednesday;
import com.capstone.readers.ToonCard.ToonFragment;
import com.capstone.readers.ToonCard.ToonListAdapter;
import com.capstone.readers.lib.MyLog;
import com.google.android.material.tabs.TabLayout;

/** 1-1 홈화면의 요일별 정렬 화면을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment1 extends Fragment {
    private TabLayout tabLayout;

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

        // Initialize the tablayout and viewpager
        tabLayout = (TabLayout) fv.findViewById(R.id.webtoon_day_tab);
        setChildFragment(ToonFragment.newInstance());

        // 탭의 선택 상태가 변경될 때 호출되는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                MyLog.d("Menu1Fragment", "선택된 요일 프래그먼트: " +pos);

                Fragment fg;
                switch (pos) {
                    case 0:
                        fg = ToonFragment.newInstance();
                        setChildFragment(fg);
                        MyLog.d("Menu1Fragment1", "선택된 탭 " +pos);
                        break;
                    case 1:
                        fg = ToonFragment.newInstance();
                        setChildFragment(fg);
                        MyLog.d("Menu1Fragment1", "선택된 탭 " +pos);
                        break;
                    case 2:
                        fg = ToonFragment.newInstance();
                        setChildFragment(fg);
                        MyLog.d("Menu1Fragment1", "선택된 탭 " +pos);
                        break;
                    case 3:
                        fg = ToonFragment.newInstance();
                        setChildFragment(fg);
                        MyLog.d("Menu1Fragment1", "선택된 탭 " +pos);
                        break;
                    case 4:
                        fg = ToonFragment.newInstance();
                        setChildFragment(fg);
                        MyLog.d("Menu1Fragment1", "선택된 탭 " +pos);
                        break;
                    case 5:
                        fg = ToonFragment.newInstance();
                        setChildFragment(fg);
                        MyLog.d("Menu1Fragment1", "선택된 탭 " +pos);
                        break;
                    case 6:
                        fg = ToonFragment.newInstance();
                        setChildFragment(fg);
                        MyLog.d("Menu1Fragment1", "선택된 탭 " +pos);
                        break;

                }
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

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if(!child.isAdded()) {
            childFt.replace(R.id.frag1_day_container, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}
