package com.capstone.readers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Wednesday extends Fragment {

    public static Wednesday newInstance(){
        return new Wednesday();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wednesday, container, false);
    }
}
