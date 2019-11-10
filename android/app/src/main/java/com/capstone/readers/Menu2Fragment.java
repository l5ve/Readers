package com.capstone.readers;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/** 2번째 메뉴인 추천페이지 화면을 나타내는 프래그먼트
 *
 */
public class Menu2Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private String name;
    private TextView profile_name;
    private SharedPreferences appData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_menu2, container, false);

        // 설정값 불러오기
        appData = this.getActivity().getSharedPreferences("appData", MODE_PRIVATE);
        load();
        profile_name = (TextView) fv.findViewById(R.id.profile_name);
        profile_name.setText(name);


        /************************* 아래 부터 선호 장르 통계 그래프 표시 */
        // Retrive chart on Fragment
        HorizontalBarChart chart = (HorizontalBarChart) fv.findViewById(R.id.chart);

        chart.setDrawGridBackground(false);
        chart.setDrawValueAboveBar(false);
        chart.getDescription().setEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        chart.getAxisRight().setEnabled(false);

        // set data
        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        for (int i = 0; i < 13; i++) {
            float val = (float) (Math.random());

            if (Math.random() * 100 > 0) {
                values.add(new BarEntry(i, val));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        /**라벨 지정 외않뒈*/
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Thu");
        xAxisLabel.add("Fri");
        xAxisLabel.add("Sat");
        xAxisLabel.add("Sun");

        XAxis xLabels = chart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);


        BarDataSet set1;
        set1 = new BarDataSet(values, "선호 장르 통계");
        set1.setStackLabels(new String[]{"Births", "Divorces", "Marriages"});
        set1.setColors(ColorTemplate.MATERIAL_COLORS);


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueFormatter(new StackedValueFormatter(false, "", 1));
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        chart.setTouchEnabled(false);
        chart.setData(data);
        chart.setFitBars(true);
        chart.animateY(1000);
        chart.invalidate();

        return fv;
    }

    // 설정값을 불러오는 함수
    private void load() {
        name = ((MyApp) getActivity().getApplication()).getUser_name();
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