package com.capstone.readers.Genre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.capstone.readers.R;

public class Daily_life extends Fragment {

    public static Daily_life newInstance(){
        return new Daily_life();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_life, container, false);
    }
}
