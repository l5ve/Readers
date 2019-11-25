package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.readers.MypageBookmark.BookmarkFragment;
import com.capstone.readers.MypageMemo.MemoFragment;
import com.capstone.readers.item.MypageResponse;

/** 3번째 메뉴인 마이페이지 화면을 나타내는 프래그먼트
 *
 */
public class Menu3Fragment extends Fragment {
    private String user_id;
    private String user_name;
    private MypageResponse mProfileData;

    private TextView profile_name;
    private LinearLayout mypage_subscribe;
    private LinearLayout mypage_bookmark;
    private LinearLayout mypage_memo;
    private ServiceApi service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu3, container, false);

        user_id = ((MyApp) getActivity().getApplication()).getUser_id();
        user_name = ((MyApp) getActivity().getApplication()).getUser_name();

        profile_name = (TextView) fv.findViewById(R.id.profile_name);
        mypage_subscribe = (LinearLayout) fv.findViewById(R.id.mypage_subscribe);
        mypage_bookmark = (LinearLayout) fv.findViewById(R.id.mypage_bookmark);
        mypage_memo = (LinearLayout) fv.findViewById(R.id.mypage_memo);

        profile_name.setText(user_name);

        Fragment fg = Menu3Fragment1.newInstance();
        setChildFragment(fg);

        mypage_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fg = Menu3Fragment1.newInstance();
                setChildFragment(fg);
            }
        });

        mypage_bookmark.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment fg = BookmarkFragment.newInstance();
                setChildFragment(fg);
            }
        });

        mypage_memo.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fg = MemoFragment.newInstance();
                setChildFragment(fg);
            }
        });

//        getData();

        return fv;
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if(!child.isAdded()) {
            childFt.replace(R.id.mypage_fragment, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

//    public void getData() {
//        service = RetrofitClient.getClient().create(ServiceApi.class);
//        UserIdData data = new UserIdData(user_id);
//        service.getMypageData(data).enqueue(new Callback<ArrayList<MypageResponse>>() {
//            @Override
//            public void onResponse(Call<ArrayList<MypageResponse>> call, Response<ArrayList<MypageResponse>> response) {
//                mProfileData = response.body().get(0);
//               }
//
//            @Override
//            public void onFailure(Call<ArrayList<MypageResponse>> call, Throwable t) {
//                Log.e("Menu3Fragment", "getData: " +getString(R.string.toon_server_error));
//                MyToast.s(getContext(), getString(R.string.toon_server_error));
//            }
//        });
//    }
}