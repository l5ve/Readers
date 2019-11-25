package com.capstone.readers.Search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SearchCard> myDataset;
    private ServiceApi service;

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
        mTextView = fv.findViewById(R.id.search_result_text);

        myDataset = new ArrayList<>();
        /* RecyclerView GridLayoutManager 지정 */
        mRecyclerView = (RecyclerView) fv.findViewById(R.id.search_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getSearchResult();

        return fv;
    }

    /* RecyclerView adapter 지정 */
    public void setAdapter() {
        mAdapter = new SearchListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getSearchResult() {
        word = ((MyApp) getActivity().getApplication()).getSearch_keyword();
        SearchData data = new SearchData(word);
        service.getSearchResult(data).enqueue(new Callback<ArrayList<SearchCard>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchCard>> call, Response<ArrayList<SearchCard>> response) {
                myDataset = response.body();
                mTextView.setText(getResources().getString(R.string.search_result) + "(" + myDataset.size() + ")");
                setAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<SearchCard>> call, Throwable t) {
                Log.e("SearchFragment", "getSearchResult: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }
}
