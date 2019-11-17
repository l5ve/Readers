package com.capstone.readers.Block;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.lib.MyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ServiceApi service;
    private List<BlockCard> list;
    private ArrayList<BlockCard> myDataset;
    private String user_id;


    public static BlockFragment newInstance() {
        return new BlockFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_manageblock, container, false);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        myDataset = new ArrayList<>();

        user_id = ((MyApp) getActivity().getApplication()).getUser_id();

        mRecyclerView = (RecyclerView) fv.findViewById(R.id.block_list);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getBlockList();

        return fv;
    }

    public void setAdapter() {
        mAdapter = new BlockListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getBlockList() {
        service.getBlockList(user_id).enqueue(new Callback<ArrayList<BlockCard>>() {
            @Override
            public void onResponse(Call<ArrayList<BlockCard>> call, Response<ArrayList<BlockCard>> response) {
                list = response.body();

                for (int i = 0; i < list.size(); i++) {
                    myDataset.add(list.get(i));
                }

                setAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<BlockCard>> call, Throwable t) {
                Log.e("BlockFragment", "getBlockList: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }
}
