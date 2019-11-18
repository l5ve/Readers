package com.capstone.readers.MypageToon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.ToonCard.ToonCard;
import com.capstone.readers.item.ToonResponse;

import java.util.ArrayList;

/* 마이페이지 구독 화면
* 요일별&완결 웹툰 리스트
* */
public class MypageToonFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ToonResponse> list;
    private ArrayList<ToonCard> myDataset;

    private RadioGroup sort_group;
    private RadioButton sort_title;
    private RadioButton sort_update;
    private RadioButton sort_platform;
    private String order_by;
    private String user_id;
    private String day;
    private ServiceApi service;

    private int indicator;
    final int paging = 12;

    public static MypageToonFragment newInstance() {
        return new MypageToonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_toon, container, false);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        order_by = "name";
        indicator = 0;


        return fv;
    }





}
