package com.example.budgetmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.budgetmanagement.databinding.FragmentBarChartBinding;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.HashMap;
import java.util.Map;

public class BarChartFragment extends Fragment {
    private FragmentBarChartBinding binding;
    private ChartManager chartManager;
    private Map<Integer, String> labels;

    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            int time = binding.sortTimeSpinner.getSelectedItemPosition();
            int type = binding.sortTypeSpinner.getSelectedItemPosition();
            chartManager.getBarData(time, type, (lb, data) -> {
                binding.chart.setData(data);
                labels = lb;
                setParameters();
                binding.chart.invalidate();
            });
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBarChartBinding.inflate(getLayoutInflater());
        chartManager = new ChartManager(binding);
        labels = new HashMap<>();
        binding.sortTimeSpinner.setOnItemSelectedListener(spinnerListener);
        binding.sortTypeSpinner.setOnItemSelectedListener(spinnerListener);
        return binding.getRoot();
    }

    private void setParameters() {
        binding.chart.setDrawBarShadow(false);
        binding.chart.setDrawValueAboveBar(true);
        binding.chart.getDescription().setEnabled(false);
        binding.chart.setMaxVisibleValueCount(100);
        binding.chart.setPinchZoom(false);
        binding.chart.setDrawGridBackground(false);

        XAxis xAxis = binding.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(31);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (labels.containsKey((int) value)) {
                    return labels.get((int) value);
                }
                return "";
            }
        });

        YAxis leftAxis = binding.chart.getAxisLeft();
        leftAxis.setLabelCount(8, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        Legend l = binding.chart.getLegend();
        l.setEnabled(false);
    }
}