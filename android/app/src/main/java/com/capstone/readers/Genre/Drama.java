package com.capstone.readers.Genre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.capstone.readers.R;

public class Drama extends Fragment {

    public static Drama newInstance(){
        return new Drama();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drama, container, false);
    }
}