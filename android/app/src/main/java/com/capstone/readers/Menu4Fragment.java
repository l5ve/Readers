package com.capstone.readers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

public class Menu4Fragment extends Fragment {
    private TextView settingLoginBtn;
    private TextView settingHideBtn;
    private TextView settingNoticeBtn;
    private TextView settingAskBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu4, container, false);

        settingLoginBtn = (TextView) fv.findViewById(R.id.setting_login_btn);
        settingHideBtn = (TextView) fv.findViewById(R.id.setting_hide_btn);
        settingNoticeBtn = (TextView) fv.findViewById(R.id.setting_notice_btn);
        settingAskBtn = (TextView) fv.findViewById(R.id.setting_ask_btn);

        settingLoginBtn.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(ManageLoginFragment.newInstance());
            }
        });

        settingAskBtn.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent email = new Intent(Intent.ACTION_SEND);
                String[] address = {"readers.cau@gmail.com"};
                email.setType("plain/Text");
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "[Ask] 제목을 작성해주세요.");
                email.putExtra(Intent.EXTRA_TEXT, "문의 내용을 작성해주세요.\n");
                email.setType("message/rfc822");
                startActivity(email);
            }
        });
        return fv;
    }

}