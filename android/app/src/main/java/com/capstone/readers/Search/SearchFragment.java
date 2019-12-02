package com.capstone.readers.Search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.item.SearchData;
import com.capstone.readers.lib.MyToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private String word;
    private SearchData data;
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SearchCard> myDataset;
    private ArrayList<DescSearchCard> DescDataset;
    private ServiceApi service;

    private RadioGroup sort_group;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_search, container, false);

        service = RetrofitClient.getClient().create(ServiceApi.class);
        word = ((MyApp) getActivity().getApplication()).getSearch_keyword();
        data = new SearchData(word);

        mTextView = fv.findViewById(R.id.search_result_text);

        myDataset = new ArrayList<>();
        DescDataset = new ArrayList<>();

        /* RecyclerView GridLayoutManager 지정 */
        mRecyclerView = (RecyclerView) fv.findViewById(R.id.search_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        /* 작품명/작가명/작품 설명으로 검색 */
        sort_group = (RadioGroup) fv.findViewById(R.id.search_sort_group);
        sort_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.search_sort_title) {
                    myDataset.clear();
                    getTitleSearchResult();
                }
                else if (checkedId == R.id.search_sort_author) {
                    myDataset.clear();
                    getAuthorSearchResult();
                }
                else if (checkedId == R.id.search_sort_desc) {
                    DescDataset.clear();
                    getDescSearchResult();
                }
            }
        });

        getTitleSearchResult();

        return fv;
    }

    /* RecyclerView adapter 지정 - 작품명, 작가명 검색 */
    public void setAdapter() {
        mAdapter = new SearchListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    /* RecyclerView adapter 지정 - 작품 설명 검색 */
    public void setDescAdapter() {
        mAdapter = new DescSearchListAdapter(getContext(), DescDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getTitleSearchResult() {
        service.getTitleSearchResult(data).enqueue(new Callback<ArrayList<SearchCard>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchCard>> call, Response<ArrayList<SearchCard>> response) {
                if (response.code() == 200) {
                    myDataset = response.body();
                    mTextView.setText(getResources().getString(R.string.search_title) + " " + getResources().getString(R.string.search_result) + "(" + myDataset.size() + ")");
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SearchCard>> call, Throwable t) {
                Log.e("SearchFragment", "getTitleSearchResult: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    private void getAuthorSearchResult() {
        service.getAuthorSearchResult(data).enqueue(new Callback<ArrayList<SearchCard>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchCard>> call, Response<ArrayList<SearchCard>> response) {
                if (response.code() == 200) {
                    myDataset = response.body();
                    mTextView.setText(getResources().getString(R.string.search_author) + " " + getResources().getString(R.string.search_result) + "(" + myDataset.size() + ")");
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SearchCard>> call, Throwable t) {
                Log.e("SearchFragment", "getAuthorSearchResult: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    private void getDescSearchResult() {
        service.getDescSearchResult(data).enqueue(new Callback<ArrayList<DescSearchCard>>() {
            @Override
            public void onResponse(Call<ArrayList<DescSearchCard>> call, Response<ArrayList<DescSearchCard>> response) {
                if (response.code() == 200) {
                    DescDataset = response.body();
                    mTextView.setText(getResources().getString(R.string.search_desc) + " " + getResources().getString(R.string.search_result) + "(" + DescDataset.size() + ")");
                    setDescAdapter();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DescSearchCard>> call, Throwable t) {
                Log.e("SearchFragment", "getDescSearchResult: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }
}
