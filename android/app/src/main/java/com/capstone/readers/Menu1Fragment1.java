package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.capstone.readers.Day.Friday;
import com.capstone.readers.Day.Monday;
import com.capstone.readers.Day.Saturday;
import com.capstone.readers.Day.Sunday;
import com.capstone.readers.Day.Thursday;
import com.capstone.readers.Day.Tuesday;
import com.capstone.readers.Day.Wednesday;
import com.capstone.readers.lib.MyLog;
import com.google.android.material.tabs.TabLayout;

/** 1-1 홈화면의 요일별 정렬 화면을 나타내는 프래그먼트
 *
 */
public class Menu1Fragment1 extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        viewPager = (ViewPager) fv.findViewById(R.id.webtoon_day_pager);

        // Create daytabpageradapter adapter
        DayTabPagerAdapter pagerAdapter = new DayTabPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        // ViewPager에서 페이지의 상태가 변경될 때 페이지 변경 이벤트를
        // TabLayout에 전달하여 탭의 선택 상태를 동기화해주는 역할
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // 탭의 선택 상태가 변경될 때 호출되는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                MyLog.d("선택된 요일 프래그먼트: " +pos);
                viewPager.setCurrentItem(tab.getPosition());
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

    View.OnClickListener movePageListener = new View.OnClickListener(){
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            viewPager.setCurrentItem(tag);
        }
    };

    public class DayTabPagerAdapter extends FragmentStatePagerAdapter {
        private int tabCount;

        public DayTabPagerAdapter(FragmentManager fm, int tabCount){
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            // Returning the current tabs
            switch (position) {
                case 0:
                    Monday monday = new Monday();
                    return monday;
                case 1:
                    Tuesday tuesday = new Tuesday();
                    return tuesday;
                case 2:
                    Wednesday wednesday = new Wednesday();
                    return wednesday;
                case 3:
                    Thursday thursday = new Thursday();
                    return thursday;
                case 4:
                   Friday friday = new Friday();
                    return friday;
                case 5:
                    Saturday saturday = new Saturday();
                    return saturday;
                case 6:
                    Sunday sunday = new Sunday();
                    return sunday;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
