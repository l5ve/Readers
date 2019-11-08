package com.capstone.readers.Genre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.capstone.readers.R;

public class Story extends Fragment {

    public static Story newInstance(){
        return new Story();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.story, container, false);
    }
}
