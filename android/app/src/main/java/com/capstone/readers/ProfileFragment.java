package com.capstone.readers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstone.readers.item.ChangeNameData;
import com.capstone.readers.item.ChangePwData;
import com.capstone.readers.lib.MyToast;
import com.capstone.readers.security.SecurityUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment {
    private EditText mCurrentPw;
    private EditText mNewPw;
    private EditText mNewPw_ver;
    private Button mChangePw;

    private EditText mNewName;
    private Button mChangeName;

    private String new_enc_pw;
    private SecurityUtil securityUtil;
    private String user_id;
    private ServiceApi service;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_profile, container, false);

        service = RetrofitClient.getClient().create(ServiceApi.class);
        securityUtil = new SecurityUtil();
        user_id = ((MyApp) getActivity().getApplication()).getUser_id();

        mCurrentPw = (EditText) fv.findViewById(R.id.profile_current_pw);
        mNewPw = (EditText) fv.findViewById(R.id.profile_new_pw);
        mNewPw_ver = (EditText) fv.findViewById(R.id.profile_new_pw_ver);
        mChangePw = (Button) fv.findViewById(R.id.profile_change_pw_btn);
        mNewName = (EditText) fv.findViewById(R.id.profile_nickname_input);
        mChangeName = (Button) fv.findViewById(R.id.profile_change_nickname_btn);

        mChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = mCurrentPw.getText().toString().trim();
                if (current.equals("")) {
                    MyToast.s(getContext(), R.string.notice_input_current_pw);
                    return;
                }
                current = securityUtil.encryptSHA256(current);

                // 현재 비밀번호 확인
                if (checkCurrentPw(current)) {
                    String newpw = mNewPw.getText().toString().trim();
                    String newpw_ver = mNewPw_ver.getText().toString().trim();

                    // 새 비밀번호 유효성 검사
                    if (isPasswordValid(newpw)) {
                        if (newpw.equals(newpw_ver)) {
                            new_enc_pw = securityUtil.encryptSHA256(newpw);
                            if (new_enc_pw.equals(current)) {
                                MyToast.s(getContext(), R.string.not_new_pw);
                            }
                            else {
                                changePassword(new_enc_pw);
                            }
                        }
                        // 새 비밀번호가 맞지 않는 경우
                        else {
                            MyToast.s(getContext(), R.string.pw_no_match);
                        }
                    }
                    else  {
                        MyToast.s(getContext(), R.string.new_pw_length_warning);
                    }
                }

                // 현재 비밀번호가 틀린 경우
                else {
                    MyToast.s(getContext(), R.string.wrong_pw);
                }
            }
        });

        mChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = mNewName.getText().toString().trim();
                if(newname.equals("")){
                    MyToast.s(getContext(), R.string.nickname_length_warning);
                }
                else {
                    changeNickname(newname);
                }
            }
        });

        return fv;
    }

    private boolean checkCurrentPw(String enc_pwd) {
        String current_pw = ((MyApp) getActivity().getApplication()).getUser_pw();
        if (current_pw.equals(enc_pwd))
            return true;
        else
            return false;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    // 새 비밀번호를 서버에 보내 등록하는 메소드
    private void changePassword(final String enc_pwd) {
        ChangePwData data = new ChangePwData(user_id, enc_pwd);
        service.changePassword(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.code() == 200) {
                    ((MyApp) getActivity().getApplication()).setUser_pw(enc_pwd);
                    Log.d("ProfileFragment", "changePassword" + getResources().getString(R.string.change_pw_success));
                    mCurrentPw.getText().clear();
                    mNewPw.getText().clear();
                    mNewPw_ver.getText().clear();
                    MyToast.s(getContext(), R.string.change_pw_success);
                }
                else {
                    MyToast.s(getContext(), R.string.change_pw_fail);
                    Log.e("ProfileFragment", "changePassword code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ProfileFragment", "changePassword: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }

    // 새 닉네임을 서버에 보내 등록하느 ㄴ메소드
    private void changeNickname(final String nickname) {
        ChangeNameData data = new ChangeNameData(user_id, nickname);
        service.changeNickname(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.code() == 200) {
                    ((MyApp) getActivity().getApplication()).setUser_name(nickname);
                    Log.d("ProfileFragment", "changeNickname" + getResources().getString(R.string.change_nickname_success));
                    mNewName.getText().clear();
                    MyToast.s(getContext(), R.string.change_nickname_success);
                }
                else {
                    MyToast.s(getContext(), R.string.change_nickname_fail);
                    Log.e("ProfileFragment", "changeNickname code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ProfileFragment", "changeNickname: " + getString(R.string.server_error));
                MyToast.s(getContext(), getString(R.string.server_error));
            }
        });
    }
}
