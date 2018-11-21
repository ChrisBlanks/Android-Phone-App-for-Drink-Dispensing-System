package com.example.cabla.drinkmachinephoneapp;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {

    int isDummyData = 1 ;
    boolean isLowLevel = true ;

    public InventoryFragment() {
        // Required empty public constructor
    }

    public void checkForLowLevel(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(),"default");
        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }

        mBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setTicker("Hearty365")
                .setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                .setContentTitle("High Priority")
                .setContentText("Running low on inventory")
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }


    public ArrayList<ArrayList<String>> packDummyData(){
        ArrayList<ArrayList<String>> data_array = new ArrayList<ArrayList<String>>();
        ArrayList<String> data = new ArrayList<>();
        data.add("rum and coke");
        data.add("700 ml");
        data.add("1000 ml");
        data_array.add(data);

        ArrayList<String> data2 = new ArrayList<>();
        data2.add("vodka");
        data2.add("500 ml");
        data2.add("1000 ml");
        data_array.add(data2);

        ArrayList<String> data3 = new ArrayList<>();
        data3.add("Tequila");
        data3.add("500 ml");
        data3.add("1000 ml");
        data_array.add(data3);

        return data_array;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View table_view = inflater.inflate(R.layout.fragment_inventory, container, false);
        TableLayout table = table_view.findViewById(R.id.inventory_table);

        ArrayList<ArrayList<String>> display_data = new ArrayList<ArrayList<String>>();
        if(isDummyData == 1){
            display_data = packDummyData();
        }else{
            //set display_data to values retrieved from files
        }

        int view_index = 1;
        int row_index = 1;
        int num_of_text_views_per_row = 3;
        int num_of_text_views = display_data.size() * num_of_text_views_per_row;

        TextView[] data_text_views = new TextView[num_of_text_views];
        TableRow[] row_inserts = new TableRow[display_data.size()];

        for(ArrayList<String> o: display_data) {
            row_inserts[row_index-1] = new TableRow(getContext());
            for(String str: o){
                data_text_views[view_index-1] = new TextView(getContext());
                data_text_views[view_index-1].setGravity(Gravity.CENTER);

                data_text_views[view_index-1].setText(str);
                data_text_views[view_index-1].setId(view_index);
                data_text_views[view_index-1].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                row_inserts[row_index-1].addView(data_text_views[view_index-1]);
                view_index++;
            }
            table.addView(row_inserts[row_index-1]);
            row_index++;
        }
        if(isLowLevel) { checkForLowLevel(); }
        return table_view;
    }

}
