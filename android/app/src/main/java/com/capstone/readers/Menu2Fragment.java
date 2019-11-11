package com.capstone.readers;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;

/** 2번째 메뉴인 추천페이지 화면을 나타내는 프래그먼트
 *
 */
public class Menu2Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private String name;
    private TextView profile_name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu2, container, false);

        // Get Username
        name = ((MyApp) getActivity().getApplication()).getUser_name();
        profile_name = (TextView) fv.findViewById(R.id.profile_name);
        profile_name.setText(name);


        /************************* 아래 부터 선호 장르 통계 그래프 표시 */

        /** Retrive chart on Fragment*/
        HorizontalBarChart chart = (HorizontalBarChart) fv.findViewById(R.id.chart);

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
        chart.setNoDataText("데이터가 없습니다");  // 효력x
        chart.setNoDataTextColor(Color.parseColor("#281e42"));
        chart.getAxisRight().setEnabled(false);
        chart.setHorizontalFadingEdgeEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDrawValueAboveBar(true); // false면 bar 안으로 값 표시 들어감


        /** set data */

        // -1. Labels
        String[] xAxisLabel = new String[] {"감성", "개그", "드라마", "로맨스", "스릴러", "스토리", "스포츠", "시대극", "옴니버스", "액션", "일상", "에피소드", "판타지"};
        /**
         ArrayList xAxisLabel = new ArrayList();
         xAxisLabel.add("Mon");
         xAxisLabel.add("Tue");
         ... 이런 식으로도 가능
         */
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        chart.getXAxis().setLabelCount(13);
        chart.getXAxis().setCenterAxisLabels(false);
        // -2. Data (Temporary)
        ArrayList values = new ArrayList();
        for (int i = 0; i < 13; i++) {
            values.add(new BarEntry(i, (float) i+1));
        }



        BarDataSet set1 = new BarDataSet(values, "선호 장르 통계");
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

}