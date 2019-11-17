package com.capstone.readers.Taste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.R;

import java.util.ArrayList;

public class TasteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> myDataset;

    public static TasteFragment newInstance() {
        return new TasteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_taste, container, false);

        mRecyclerView = (RecyclerView) fv.findViewById(R.id.taste_recyclerview);
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        myDataset.add(getResources().getString(R.string.sentimental));
        myDataset.add(getResources().getString(R.string.humor));
        myDataset.add(getResources().getString(R.string.drama));
        myDataset.add(getResources().getString(R.string.love));
        myDataset.add(getResources().getString(R.string.thriller));
        myDataset.add(getResources().getString(R.string.story));
        myDataset.add(getResources().getString(R.string.sports));
        myDataset.add(getResources().getString(R.string.historical));
        myDataset.add(getResources().getString(R.string.omnibus));
        myDataset.add(getResources().getString(R.string.action));
        myDataset.add(getResources().getString(R.string.daily_life));
        myDataset.add(getResources().getString(R.string.episode));
        myDataset.add(getResources().getString(R.string.fantasy));

        mAdapter = new TasteListAdapter(getContext(), myDataset);
        mRecyclerView.setAdapter(mAdapter);

        return fv;
    }
}
