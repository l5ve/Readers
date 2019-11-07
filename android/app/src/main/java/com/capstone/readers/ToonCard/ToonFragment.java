package com.capstone.readers.ToonCard;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ToonFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ToonCard> myDataset;

    private RadioGroup sort_group;
    private RadioButton sort_title;
    private RadioButton sort_update;
    private RadioButton sort_platform;


}
