package com.example.cabla.drinkmachinephoneapp;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;


public class LabelFormatter implements AxisValueFormatter {
    private final String[] mLabels;

    public LabelFormatter(String[] labels){
        mLabels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis){
        return mLabels[(int) value];
    }
    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
