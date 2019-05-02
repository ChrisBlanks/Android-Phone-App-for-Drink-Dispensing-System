package com.example.cabla.drinkmachinephoneapp;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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

    boolean isLowLevel = false;
    boolean hasAlerted = false;

    public InventoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<String> inventory_info = getArguments().getStringArrayList("inventory");
        boolean no_status_data = getArguments().getBoolean("no_data");
        Log.i("CHRISTEST","Inventory "+String.valueOf(no_status_data));

        final View table_view = inflater.inflate(R.layout.fragment_inventory, container, false);
        TableLayout table = table_view.findViewById(R.id.inventory_table);

        ArrayList<ArrayList<String>> display_data = new ArrayList<ArrayList<String>>();
        if (no_status_data == true) {
            display_data = packDummyData();
            Log.i("CHRISTEST","No data in inventory fragment");
        } else if (inventory_info != null) {
            display_data = packRealData(inventory_info);
        }else{
            display_data = packDummyData();
            Log.i("CHRISTEST","No inventory data");
        }

        int view_index = 1;
        int row_index = 1;
        int num_of_text_views_per_row = 3;
        int num_of_text_views = display_data.size() * num_of_text_views_per_row;

        TextView[] data_text_views = new TextView[num_of_text_views];
        TableRow[] row_inserts = new TableRow[display_data.size()];

        for (ArrayList<String> o : display_data) {
            row_inserts[row_index - 1] = new TableRow(getContext());
            for (String str : o) {
                if (view_index > num_of_text_views) {
                    break;
                }
                data_text_views[view_index - 1] = new TextView(getContext());
                data_text_views[view_index - 1].setGravity(Gravity.CENTER);

                data_text_views[view_index - 1].setText(str);
                data_text_views[view_index - 1].setId(view_index);
                data_text_views[view_index - 1].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                row_inserts[row_index - 1].addView(data_text_views[view_index - 1]);
                view_index++;
            }
            table.addView(row_inserts[row_index - 1]);
            row_index++;
        }
        if (isLowLevel && !hasAlerted) {
            notificationForLowLevel();
            hasAlerted = true;
        }

        return table_view;
    }


    public void notificationForLowLevel() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "default");
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


    public ArrayList<ArrayList<String>> packDummyData() {
        ArrayList<ArrayList<String>> data_array = new ArrayList<ArrayList<String>>();

        ArrayList<String> data = new ArrayList<>();
        data.add("light rum");
        data.add("700 ml");
        data.add("1000 ml");
        data_array.add(data);

        ArrayList<String> data2 = new ArrayList<>();
        data2.add("vodka");
        data2.add("400 ml");
        data2.add("1000 ml");
        data_array.add(data2);

        ArrayList<String> data3 = new ArrayList<>();
        data3.add("Tequila");
        data3.add("600 ml");
        data3.add("1000 ml");
        data_array.add(data3);

        ArrayList<String> data4 = new ArrayList<>();
        data4.add("Simple Syrup");
        data4.add("800 ml");
        data4.add("1000 ml");
        data_array.add(data4);

        ArrayList<String> data5 = new ArrayList<>();
        data5.add("Soda Water");
        data5.add("1500 ml");
        data5.add("2000 ml");
        data_array.add(data5);

        ArrayList<String> data6 = new ArrayList<>();
        data6.add("Triple Sec");
        data6.add("300 ml");
        data6.add("500 ml");
        data_array.add(data6);

        ArrayList<String> data7 = new ArrayList<>();
        data7.add("Lemon Juice");
        data7.add("200 ml");
        data7.add("300 ml");
        data_array.add(data7);

        ArrayList<String> data8 = new ArrayList<>();
        data8.add("Orange Juice");
        data8.add("500 ml");
        data8.add("800 ml");
        data_array.add(data8);

        ArrayList<String> data9 = new ArrayList<>();
        data9.add("Cranberry Juice");
        data9.add("800 ml");
        data9.add("1000 ml");
        data_array.add(data9);

        isLowLevel = true;
        return data_array;
    }

    public ArrayList<ArrayList<String>> packRealData(ArrayList<String> inventory_data) {
        ArrayList<ArrayList<String>> data_array = new ArrayList<ArrayList<String>>();
        ArrayList<String> data = new ArrayList<>();
        Log.i("inventory", inventory_data.toString());
        for (String str : inventory_data) {
            String[] item_parts = str.trim().split(" ");
            data.add(item_parts[0]);
            data.add(item_parts[1]);
            data.add(item_parts[2]);
            double ratio_of_leftover = Integer.valueOf(item_parts[1])/Integer.valueOf(item_parts[2]);

            //sends a notification if any drink is below 50% fluid level
            if(ratio_of_leftover < 0.5){
                isLowLevel = true;
            }
            data_array.add(new ArrayList<String>(data));
            data.clear();
        }
        return data_array;
    }

}