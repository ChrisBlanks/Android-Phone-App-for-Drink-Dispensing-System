package com.example.cabla.drinkmachinephoneapp;

import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment {

    int dummy_data = 1;

    Boolean initialDisplay = true;
    String [] default_values = {"11/18/2018","11/17/2018"};
    String [] file_values = {"11/18/2018","11/17/2018"}; //these dates will need to come from file
    //maybe only load the latest 5-8 dates

    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_frag = inflater.inflate(R.layout.fragment_sales2, container, false);
        BarChart sales_chart = view_frag.findViewById(R.id.sales_bar_chart);
        sales_chart.invalidate();
        TextView rev_label = view_frag.findViewById(R.id.total_revenue_label);


        TextView rev_val = view_frag.findViewById(R.id.total_revenue_value);

        Spinner day_of_sales_spinner = view_frag.findViewById(R.id.day_of_sales_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item,default_values);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_of_sales_spinner.setAdapter(adapter);

        setupCallbacks(day_of_sales_spinner,view_frag,sales_chart,rev_val);
        return view_frag ;
    }

    public void setupCallbacks(Spinner spinner, View view, final BarChart sales_chart,final TextView total){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(initialDisplay){
                    initialDisplay = false; //onItemSelected is called when everything is first initialized
                    // so this flag ignores that first call, but allows any call after
                }else{
                    sales_chart.invalidate();

                    // String total_revenue = container[val];
                    //total.setText(total_revenue);
                    total.setText("$45");

                    // get how many drinks Float axis_size = (float)(container.length() +1 );
                    Float axis_size = (float)7 ;

                    //axis configuration
                    XAxis x_axis = sales_chart.getXAxis();
                    x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    x_axis.setTextSize(10f);
                    x_axis.setAxisMinValue(0f);
                    x_axis.setAxisMaxValue(axis_size);
                    x_axis.setTextSize(3);

                    YAxis left_axis = sales_chart.getAxisLeft();
                    YAxis right_axis = sales_chart.getAxisRight();

                    left_axis.setDrawZeroLine(true);
                    left_axis.setAxisMinValue(0f);
                    right_axis.setDrawLabels(false);
                    sales_chart.getAxisRight().setEnabled(false);

                    //use a for loop instead that packs revenue data into array list
                    /*
                        container;
                        ArrayList<BarEntry> bar_chart_entries = new ArrayList<>();
                        int revenue = 0;
                        for(int i=(int)0; i < container.length();i++) {
                            revenue = container[1][i]   //make first element the total sales of a drink
                            bar_chart_entries.add(new BarEntry(i,revenue))
                        }
                     */
                    ArrayList<BarEntry> bar_chart_entries = new ArrayList<>();

                    if(dummy_data ==1) {
                        bar_chart_entries.add(new BarEntry(1, 5));
                        bar_chart_entries.add(new BarEntry(2, 6));
                        bar_chart_entries.add(new BarEntry(3, 7));
                        bar_chart_entries.add(new BarEntry(4, 8));
                        bar_chart_entries.add(new BarEntry(5, 9));
                        bar_chart_entries.add(new BarEntry(6, 10));
                    }
                    BarDataSet data_for_display = new BarDataSet(bar_chart_entries,
                            "Revenue from drink menu");
                    data_for_display.setColors(ColorTemplate.JOYFUL_COLORS);

                    //use a for loop instead that packs revenue data into array list
                    /*
                        container;
                        String[] drink_names = new String[container.length()];
                        for(int i=(int)0; i < container.length();i++) {
                            drink_names[i] = container[i];
                        }
                    */

                    String[] drink_names = new String[7];
                    if(dummy_data ==1) {
                        drink_names[0] = "";
                        drink_names[1] = "Cuba Libre";
                        drink_names[2] = "Daiquiri";
                        drink_names[3] = "Screwdriver";
                        drink_names[4] = "Tequila";
                        drink_names[5] = "Vodka";
                        drink_names[6] = "Vodka & Cranberry";
                    }

                    //needs to be an arraylist object before put on X-axis
                    final ArrayList<String> xEntrys = new ArrayList<>();
                    for(int i = 0; i < drink_names.length; i++){
                        xEntrys.add(drink_names[i]);
                    }

                    //put drink names on X-axis
                    x_axis.setValueFormatter(new AxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return xEntrys.get((int)value % xEntrys.size());
                        }

                        @Override
                        public int getDecimalDigits() {
                            return 0;
                        }
                    });

                    BarData data = new BarData(data_for_display);
                    data.setBarWidth(0.5f);

                    sales_chart.setScaleXEnabled(false);

                    //sales_chart.setScaleYEnabled(false);
                    sales_chart.setPinchZoom(false);
                    sales_chart.setDrawGridBackground(false);

                    sales_chart.setDescription("Daily Drink Sales");
                    sales_chart.setDescriptionPosition(150,30);
                    sales_chart.getLegend().setEnabled(false);
                    sales_chart.setData(data);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {} //needed for proper use
        });
    }

}
