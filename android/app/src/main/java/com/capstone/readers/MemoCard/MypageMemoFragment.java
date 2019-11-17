package com.capstone.readers.MemoCard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.ServiceApi;

import java.util.ArrayList;

public class MypageMemoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MemoCard> myDataset;

    private TextView noDataText;
    private RadioGroup sort_group;
    private RadioButton sort_new;
    private RadioButton sort_old;

    private String user_id;
    private ServiceApi service;

    public static MypageMemoFragment newInstance(){
        return new MypageMemoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_mypagememo, container, false);

        // service = RetrofitClient.getClient().create(ServiceApi.class);
        user_id = ((MyApp) getActivity().getApplication()).getUser_id();

        noDataText = (TextView) fv.findViewById(R.id.memo_no_data);
        sort_new = (RadioButton) fv.findViewById(R.id.memo_sort_new);
        sort_old = (RadioButton) fv.findViewById(R.id.memo_sort_old);
        sort_group = (RadioGroup) fv.findViewById(R.id.memo_sort_group);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정
        mRecyclerView = (RecyclerView) fv.findViewById(R.id.memo_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 리사이클러뷰에 표시할 데이터 리스트 생성
        myDataset = new ArrayList<>();

        myDataset.add(new MemoCard("01232", 0, getString(R.string.naver), "제목1", "작가1", "메모메모", "2018-01-02"));
        myDataset.add(new MemoCard("13244", 0, getString(R.string.daum), "제목2", "작가2", "메모메모2", "2019-01-02"));
        myDataset.add(new MemoCard("25432", 0, getString(R.string.lezhin), "제목3", "작가3", "메모메모3", "2018-07-15"));

        for(int i = 0; i < myDataset.size(); i++){
            Log.d("MypageMemoFragment", "Dataset(" + i + ") " + myDataset.get(i).img + ", " + myDataset.get(i).memo);
        }

        mAdapter = new MemoListAdapter(getContext(), myDataset, true);
        mRecyclerView.setAdapter(mAdapter);

        sort_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                String result;
                if(checkedId == R.id.memo_sort_new){
                    mAdapter = new MemoListAdapter(getContext(), myDataset, true);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter = new MemoListAdapter(getContext(), myDataset, false);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });

        return fv;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
