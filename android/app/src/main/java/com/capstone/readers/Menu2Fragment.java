package com.capstone.readers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.capstone.readers.Recommend.RecommendCard;
import com.capstone.readers.item.GenreWeightData;
import com.capstone.readers.item.UserIdData;
import com.capstone.readers.lib.MyToast;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/** 2번째 메뉴인 추천페이지 화면을 나타내는 프래그먼트
 *
 */
public class Menu2Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private String user_id;
    private String user_name;
    private TextView profile_name;
    HorizontalBarChart chart;

    /* 서버 통신을 위한 데이터 */
    private UserIdData uid;
    private ArrayList<GenreWeightData> temp;
    private ArrayList<GenreWeightData> mGenreWeight;
    private ArrayList<RecommendCard> mRecommendations;
    private ServiceApi service;

    /* 그래프 x축 */
    String[] xAxisLabel;

    /* Top1 장르 추천작 */
    private TextView mMessage1;
    private CardView mCardView1;
    private ImageView mImageView1;
    private TextView mPlatform1;
    private TextView mTitle1;
    private TextView mAuthor1;
    private TextView mDescription1;

    /* Top2 장르 추천작 */
    private TextView mMessage2;
    private CardView mCardView2;
    private ImageView mImageView2;
    private TextView mPlatform2;
    private TextView mTitle2;
    private TextView mAuthor2;
    private TextView mDescription2;

    /* Top3 장르 추천작 */
    private TextView mMessage3;
    private CardView mCardView3;
    private ImageView mImageView3;
    private TextView mPlatform3;
    private TextView mTitle3;
    private TextView mAuthor3;
    private TextView mDescription3;

    /* for thumbnail */
    private Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu2, container, false);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        // 서버로 보내기 위한 데이터
        user_id = ((MyApp) getActivity().getApplication()).getUser_id();
        uid = new UserIdData(user_id);
        mGenreWeight = new ArrayList<>();

        // Get Username
        user_name = ((MyApp) getActivity().getApplication()).getUser_name();
        profile_name = (TextView) fv.findViewById(R.id.profile_name);
        profile_name.setText(user_name);

        /** Retrive chart on Fragment*/
        chart = (HorizontalBarChart) fv.findViewById(R.id.chart);

        /* 추천작 레이아웃 설정 */
        mMessage1 = (TextView) fv.findViewById(R.id.rec1_message);
        mCardView1 = (CardView) fv.findViewById(R.id.rec1_cv);
        mImageView1 = (ImageView) fv.findViewById(R.id.rec1_cv_image);
        mPlatform1 = (TextView) fv.findViewById(R.id.rec1_cv_platform);
        mTitle1 = (TextView) fv.findViewById(R.id.rec1_cv_title);
        mAuthor1 = (TextView) fv.findViewById(R.id.rec1_cv_author);
        mDescription1 = (TextView) fv.findViewById(R.id.rec1_cv_desc);

        mMessage2 = (TextView) fv.findViewById(R.id.rec2_message);
        mCardView2 = (CardView) fv.findViewById(R.id.rec2_cv);
        mImageView2 = (ImageView) fv.findViewById(R.id.rec2_cv_image);
        mPlatform2 = (TextView) fv.findViewById(R.id.rec2_cv_platform);
        mTitle2 = (TextView) fv.findViewById(R.id.rec2_cv_title);
        mAuthor2 = (TextView) fv.findViewById(R.id.rec2_cv_author);
        mDescription2 = (TextView) fv.findViewById(R.id.rec2_cv_desc);

        mMessage3 = (TextView) fv.findViewById(R.id.rec3_message);
        mCardView3 = (CardView) fv.findViewById(R.id.rec3_cv);
        mImageView3 = (ImageView) fv.findViewById(R.id.rec3_cv_image);
        mPlatform3 = (TextView) fv.findViewById(R.id.rec3_cv_platform);
        mTitle3 = (TextView) fv.findViewById(R.id.rec3_cv_title);
        mAuthor3 = (TextView) fv.findViewById(R.id.rec3_cv_author);
        mDescription3 = (TextView) fv.findViewById(R.id.rec3_cv_desc);


        // 서버로부터 해당 사용자의 정보를 바탕으로 각 장르 가중치를 받아와 그래프로 나타냄
        getGenreWeight();

        // 서버로부터 Top3 장르에 해당하는 추천 작품을 하나씩 받아와 나타냄
        getRecomendations();

        return fv;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    // 모든 장르의 가중치를 받아옴
    private void getGenreWeight() {
        service.getGenreWeight(uid).enqueue(new Callback<ArrayList<GenreWeightData>>() {
            @Override
            public void onResponse(Call<ArrayList<GenreWeightData>> call, Response<ArrayList<GenreWeightData>> response) {
                temp = response.body();

                for (int i = 0; i < temp.size(); i++) {
                    mGenreWeight.add(temp.get(temp.size() - 1 - i));
                }
                // 가중치를 토대로 그래프 그리기
                setChart();
            }

            @Override
            public void onFailure(Call<ArrayList<GenreWeightData>> call, Throwable t) {
                Log.e("Menu2Fragment", "getGenreWeight: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }

    // 상위 장르 3개에 대해서 1개씩 추천작품을 받아옴
    private void getRecomendations() {
        service.getRecommendations(uid).enqueue(new Callback<ArrayList<RecommendCard>>() {
            @Override
            public void onResponse(Call<ArrayList<RecommendCard>> call, Response<ArrayList<RecommendCard>> response) {
                mRecommendations = response.body();

                // 추천 받은 작품 레이아웃 설정
                setRecommendations();
            }

            @Override
            public void onFailure(Call<ArrayList<RecommendCard>> call, Throwable t) {
                Log.e("Menu2Fragment", "getRecommendations: " + getString(R.string.toon_server_error));
                MyToast.s(getContext(), getString(R.string.toon_server_error));
            }
        });
    }

    private void setRecommendations() {
        mMessage1.setText(xAxisLabel[0] + " " + getResources().getString(R.string.rec_string0) + " " + user_name + getResources().getString(R.string.rec_string0_1));
        setThumbnail(mImageView1, mRecommendations.get(0).getThumbnail());
        mPlatform1.setText(mRecommendations.get(0).getPlatform());
        mTitle1.setText(mRecommendations.get(0).getTitle());
        mAuthor1.setText(mRecommendations.get(0).getAuthor());
        mDescription1.setText(mRecommendations.get(0).getDescription());

        mMessage2.setText(user_name + getResources().getString(R.string.rec_string1_0) + " " + xAxisLabel[1] + " " +getResources().getString(R.string.rec_string1));
        setThumbnail(mImageView2, mRecommendations.get(1).getThumbnail());
        mPlatform2.setText(mRecommendations.get(1).getPlatform());
        mTitle2.setText(mRecommendations.get(1).getTitle());
        mAuthor2.setText(mRecommendations.get(1).getAuthor());
        mDescription2.setText(mRecommendations.get(1).getDescription());

        mMessage3.setText(xAxisLabel[2] + " " + getResources().getString(R.string.rec_string2));
        setThumbnail(mImageView3, mRecommendations.get(2).getThumbnail());
        mPlatform3.setText(mRecommendations.get(2).getPlatform());
        mTitle3.setText(mRecommendations.get(2).getTitle());
        mAuthor3.setText(mRecommendations.get(2).getAuthor());
        mDescription3.setText(mRecommendations.get(2).getDescription());
    }

    /* thumbnail 설정 */
    private void setThumbnail(ImageView mImageView, final String thumbnail_url) {
        Thread mThread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(thumbnail_url);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    // InputStream 값을 가져와 Bitmap으로 변환
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();;

        try {
            mThread.join();
            mImageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* 그래프 설정 */
    private void setChart() {
        /** settings */
        // -1. Axis
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        yAxis.setDrawLabels(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true); //
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);

        // -2. grid property
        chart.setDrawGridBackground(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);


        // -3.
        chart.getDescription().setEnabled(false);
        chart.setNoDataText(getResources().getString(R.string.no_data));  // 효력x
        chart.setNoDataTextColor(Color.parseColor("#281e42"));
        chart.getAxisRight().setEnabled(false);
        chart.setHorizontalFadingEdgeEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDrawValueAboveBar(true); // false면 bar 안으로 값 표시 들어감


        /** set data */

        // -1. Labels
        Log.d("Menu2Fragment", "Heeeeeeeeeeeeeeeeeeeeeeeeeeere, mGenreWeight size: " + mGenreWeight.size());
        xAxisLabel = new String[mGenreWeight.size()];
        //String[] xAxisLabel = new String[] {"감성", "개그", "드라마", "로맨스", "스릴러", "스토리", "스포츠", "시대극", "옴니버스", "액션", "일상", "에피소드", "판타지"};
        for (int i = 0; i < mGenreWeight.size(); i++) {
            xAxisLabel[i] = mGenreWeight.get(i).getGenre_name();
            Log.d("Menu2Fragment", "xAxisLabel[" + i + "]: " + xAxisLabel[i]);
        }

        /**
         ArrayList xAxisLabel = new ArrayList();
         xAxisLabel.add("Mon");
         xAxisLabel.add("Tue");
         ... 이런 식으로도 가능
         */
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        chart.getXAxis().setLabelCount(mGenreWeight.size());
        chart.getXAxis().setCenterAxisLabels(false);
        // -2. Data (Temporary)
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < mGenreWeight.size(); i++) {
            values.add(new BarEntry(i, (float) mGenreWeight.get(i).getWeight()));
        }
//        for (int i = 0; i < 13; i++) {
//            values.add(new BarEntry(i, (float) i+1));
//        }



        BarDataSet set1 = new BarDataSet(values, getResources().getString(R.string.preferred_genre_stats));
        // Setting bar colors
        set1.setColors(new int[] {  Color.parseColor("#281e42"),
                Color.parseColor("#493e5f"),
                Color.parseColor("#6a617c"),
                Color.parseColor("#8e869c"),
                Color.parseColor("#b2adbc"),
                Color.parseColor("#d8d5dd")
        });
        // set1.setColors(ColorTemplate.MATERIAL_COLORS);
        //             or ColorTemplate.VORDIPLOM_COLORS    for 알록달록~~
        BarData data = new BarData(set1);
        /**
         굳이 이 방식 안 써도 되는데 혹시 몰라서 넣어둠

         ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
         dataSets.add(set1);
         BarData data = new BarData(dataSets);
         */

        // 얘는 stack으로 하면 쓰일 것
        // data.setValueFormatter(new StackedValueFormatter(false, "", 1));
        data.setValueTextSize(0f);  // y데이터 값 표시 if 0f == hide
        data.setBarWidth(1f);


        // need to implement click event listener
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // fire up event
                e.getData();
                Log.d("VAL SELECTED",
                        "Value: " + e.getY() + ", xIndex: " + e.getX()
                                + ", DataSet index: " + h.getDataSetIndex());
            }

            @Override
            public void onNothingSelected() {
                Log.d("BAR_CHART_SAMPLE", "nothing selected X is ");
            }
        });

        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        chart.setData(data);
        chart.setFitBars(true);
        chart.animateY(1000);
        chart.invalidate();
    }

}