package com.capstone.readers.Day;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.capstone.readers.R;

public class Thursday extends Fragment {

    public static Thursday newInstance(){
        return new Thursday();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thursday, container, false);
    }
}
