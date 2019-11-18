package com.capstone.readers.MypageBookmark;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.readers.ServiceApi;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<BookmarkCard> myDataset;

    private RadioGroup sort_group;
    private RadioButton sort_new;
    private RadioButton sort_old;

    private String user_id;
    private String order_by;
    private ServiceApi service;

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }



}
