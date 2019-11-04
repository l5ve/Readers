package com.capstone.readers.mypage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.capstone.readers.LoginActivity;
import com.capstone.readers.R;
import com.capstone.readers.RetrofitClient;
import com.capstone.readers.ServiceApi;
import com.capstone.readers.item.LoginResponse;
import com.capstone.readers.item.MemoData;
import com.capstone.readers.item.MemoResponse;
import com.capstone.readers.lib.MyLog;
import com.capstone.readers.lib.MyToast;
import com.google.android.material.tabs.TabLayout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageMemoFragment extends Fragment {
    private RecyclerView memo_list;
    private TextView noDataText;
    private RadioGroup sort_group;
    private RadioButton sort_new;
    private RadioButton sort_old;

    private String user_id;
    private ServiceApi service;
    private SharedPreferences appData;

    public static MypageMemoFragment newInstance(){
        return new MypageMemoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_mypagememo, container, false);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        user_id = appData.getString("ID", "");

        return fv;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        memo_list = (RecyclerView) view.findViewById(R.id.memo_list);
        noDataText = (TextView) view.findViewById(R.id.memo_no_data);

        sort_new = (RadioButton) view.findViewById(R.id.memo_sort_new);
        sort_old = (RadioButton) view.findViewById(R.id.memo_sort_old);
        sort_group = (RadioGroup) view.findViewById(R.id.memo_sort_group);


    }

    private void listInfo(MemoData data) {
        service.memoGet(data).enqueue(new Callback<MemoResponse>() {
            @Override
            public void onResponse(Call<MemoResponse> call, Response<MemoResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("MEMO_RESPONSE_BODY", "MEMO_RESPONSE_BODY_IS_NOT_NULL");
                    MemoResponse result = response.body();

                    if (result.getCode() == 200) {


                    }
                } else{
                    ResponseBody errorBody = response.errorBody();
                    Log.e("RESPONSE_BODY", "RESPONSE_BODY_IS_NULL");
                    MyToast.s(getContext(), R.string.server_error_message);
                }
            }


            @Override
            public void onFailure(Call<MemoResponse> call, Throwable t) {
                MyToast.s(getContext(), R.string.no_internet_connectivity);
                Log.e("메모 통신 에러 발생", t.getMessage());
            }
        });


    }
}
