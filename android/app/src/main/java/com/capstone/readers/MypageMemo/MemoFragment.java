package com.capstone.readers.MypageMemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.item.MypageData;
import com.capstone.readers.lib.MyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MemoCard> myDataset;

    private RadioGroup sort_group;
    private RadioButton sort_new;
    private RadioButton sort_old;

    private String user_id;
    private String order_by;
    private ServiceApi service;

    public static MemoFragment newInstance(){
        return new MemoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_mypage, container, false);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        order_by="desc";    // default: 최신순

        user_id = ((MyApp) getActivity().getApplication()).getUser_id();

        // 리사이클러뷰에 표시할 데이터 리스트 생성
        myDataset = new ArrayList<>();

        sort_new = (RadioButton) fv.findViewById(R.id.mypage_sort_new);
        sort_old = (RadioButton) fv.findViewById(R.id.mypage_sort_old);
        sort_group = (RadioGroup) fv.findViewById(R.id.mypage_sort_group);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정
        mRecyclerView = (RecyclerView) fv.findViewById(R.id.mypage_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        sort_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.mypage_sort_new) {
                    order_by = "desc";
                }
                else if (checkedId == R.id.mypage_sort_old) {
                    order_by = "asc";
                }
                myDataset.clear();
                getData();
            }
        });

        getData();

        return fv;
    }

    public void setAdapter() {
        mAdapter = new MemoListAdapter(getContext(), myDataset);
        ((MyApp) getContext().getApplicationContext()).setGlobalAdapter(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getData() {
        MypageData data = new MypageData(user_id, order_by);
        service.getMemoList(data).enqueue(new Callback<ArrayList<MemoCard>>() {
            @Override
            public void onResponse(Call<ArrayList<MemoCard>> call, Response<ArrayList<MemoCard>> response) {
                if (response.code() == 200) {
                    myDataset = response.body();

                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MemoCard>> call, Throwable t) {
                Log.e("MemoFragment", "getData: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });

    }


}
