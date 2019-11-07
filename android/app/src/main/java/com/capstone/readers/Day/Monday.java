package com.capstone.readers.Day;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.capstone.readers.MyApp;
import com.capstone.readers.R;

import org.w3c.dom.Text;

public class Monday extends Fragment {
    private TextView id;
    private TextView pw;
    private TextView saved;
    private TextView name;
    private TextView subs_num;
    private TextView bookmark_num;
    private TextView memo_num;

    private String saved_id;
    private String saved_pw;
    private boolean saveLoginData;
    private String saved_name;
    private int saved_sub;
    private int saved_bm;
    private int saved_memo;

    public static Monday newInstance(){
        return new Monday();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.monday, container, false);

        load();

        id = (TextView) fv.findViewById(R.id.mon_id);
        pw = (TextView) fv.findViewById(R.id.mon_pw);
        saved = (TextView) fv.findViewById(R.id.mon_saved);
        name = (TextView) fv.findViewById(R.id.mon_name);
        subs_num = (TextView) fv.findViewById(R.id.mon_subs_num);
        bookmark_num = (TextView) fv.findViewById(R.id.mon_bookmark_num);
        memo_num = (TextView) fv.findViewById(R.id.mon_memo_num);

        id.setText(saved_id);
        pw.setText(saved_pw);
        saved.setText(String.valueOf(saveLoginData));
        name.setText(saved_name);
        subs_num.setText(Integer.toString(saved_sub));
        bookmark_num.setText(Integer.toString(saved_bm));
        memo_num.setText(Integer.toString(saved_memo));

        return fv;
    }

    // 설정값을 불러오는 함수
    private void load() {
        saved_id = ((MyApp) getActivity().getApplication()).getUser_id();
        saved_pw = ((MyApp) getActivity().getApplication()).getUser_pw();
        saveLoginData = ((MyApp)getActivity().getApplication()).getSavedData();
        saved_name = ((MyApp) getActivity().getApplication()).getUser_name();
        saved_sub = ((MyApp) getActivity().getApplication()).getSubs_num();
        saved_bm = ((MyApp) getActivity().getApplication()).getBookmark_num();
        saved_memo = ((MyApp) getActivity().getApplication()).getMemo_num();
    }
}
