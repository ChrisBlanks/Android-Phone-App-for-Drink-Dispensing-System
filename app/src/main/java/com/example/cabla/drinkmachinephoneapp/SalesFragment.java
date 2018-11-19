package com.example.cabla.drinkmachinephoneapp;

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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment {

    Boolean initialDisplay = true;
    String [] default_values = {"11/18/2018","11/17/2018"};
    String [] file_values = {"11/18/2018","11/17/2018"};

    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_frag = inflater.inflate(R.layout.fragment_sales2, container, false);
        BarChart sales_chart = view_frag.findViewById(R.id.sales_bar_chart);

        TextView rev_label = view_frag.findViewById(R.id.total_revenue_label);


        TextView rev_val = view_frag.findViewById(R.id.total_revenue_value);
        rev_val.setText("$20");

        Spinner day_of_sales_spinner = view_frag.findViewById(R.id.day_of_sales_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item,default_values);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_of_sales_spinner.setAdapter(adapter);

        setupCallbacks(day_of_sales_spinner,view_frag,sales_chart);
        return view_frag ;
    }

    public void setupCallbacks(Spinner spinner, View view, final BarChart sales_chart){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(initialDisplay){
                    initialDisplay = false; //onItemSelected is called when everything is first initialized
                    // so this flag ignores that first call, but allows any call after
                }else{
                    ArrayList<BarEntry> bar_chart_entries = new ArrayList<>();

                    //use a for loop instead that packs revenue data into array list
                    bar_chart_entries.add(new BarEntry(2.5f,0));
                    bar_chart_entries.add(new BarEntry(4f,1));
                    bar_chart_entries.add(new BarEntry(5f,2));
                    bar_chart_entries.add(new BarEntry(10f,3));
                    bar_chart_entries.add(new BarEntry(0f,4));
                    bar_chart_entries.add(new BarEntry(3f,5));

                    BarDataSet data_for_display = new BarDataSet(bar_chart_entries,
                            "Revenue from drink menu");

                    // length() on list of drink names

                    String[] drink_names = new String[6];

                    //use a for loop instead that packs revenue data into array list
                    drink_names[0] = "Cuba Libre";
                    drink_names[1] = "Daiquiri";
                    drink_names[2] = "Screwdriver";
                    drink_names[3] = "Tequila";
                    drink_names[4] = "Vodka";
                    drink_names[5] = "Vodka & Cranberry";

                    BarData data = new BarData(data_for_display);
                    //sales_chart.getXAxis().setValueFormatter(new LabelFormatter(drink_names));
                    data_for_display.setColors(ColorTemplate.COLORFUL_COLORS);
                    sales_chart.setData(data);
                    sales_chart.setDescription("Daily Drink Sales");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

}
