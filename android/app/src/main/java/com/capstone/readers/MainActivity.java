package com.capstone.readers;

import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.SharedPreferences;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Readers의 핵심 액티비티
 * 아래쪽에 navigation menu를 가진다.
 * 다양한 프래그먼트를 보여주는 컨테이너 역할을 한다.
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private SharedPreferences appData;

    // FrameLayout에 각 메뉴의 Fragment를 바꿔줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 3개의 메뉴에 들어갈 Fragments
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();
    private Menu4Fragment menu4Fragment = new Menu4Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        //첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                switch(menuItem.getItemId()) {
                    // 1번 메뉴: 홈
                    case R.id.nav_menu1: {
                        transaction1.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();

                        break;
                    }
                    // 2번 메뉴: 추천
                    case R.id.nav_menu2: {
                        transaction1.replace(R.id.frame_layout, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }
                    // 3번 메뉴: 마이페이지
                    case R.id.nav_menu3: {
                        transaction1.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }

                    // 4번 메뉴: 설정
                    case R.id.nav_menu4: {
                        transaction1.replace(R.id.frame_layout, menu4Fragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment).commit();
    }
}
