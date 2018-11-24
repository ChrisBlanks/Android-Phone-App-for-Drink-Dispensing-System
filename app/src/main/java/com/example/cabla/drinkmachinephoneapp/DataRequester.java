package com.example.cabla.drinkmachinephoneapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class DataRequester implements Runnable {

    private volatile ArrayList<String> status_store = new ArrayList<>();
    private volatile ArrayList<String> inventory_store = new ArrayList<>();
    private volatile ArrayList<String> sales_store = new ArrayList<>();
    private volatile boolean noConnection = false;

    @Override
    public void run(){
        retrieveFiles();
    }

    public boolean getError(){
        return noConnection;
    }

    public ArrayList<String> getStatusData(){
        return status_store;
    }

    public ArrayList<String> getInventoryData(){
        return inventory_store;
    }

    public ArrayList<String> getSalesData(){
        return sales_store;
    }

    public void retrieveFiles(){
        URL file_location;
        BufferedReader buffer;
        try {
            file_location = new URL("http://10.0.0.35:8000/shared_data_2018-11-22.txt");
            Log.i("CHRIS1",file_location.getFile());

            buffer = new BufferedReader(new InputStreamReader(file_location.openStream()));
            String line;

            boolean statusSaveKey = false;
            boolean inventorySaveKey = false;
            boolean salesSaveKey = false;


            while((line = buffer.readLine())!= null) {
                if (statusSaveKey && !line.contains("INVENTORY")) {
                    Log.i("CHRIS",line);
                    status_store.add(line);
                }
                if (inventorySaveKey && !line.contains("SALES")) {
                    Log.i("CHRIS",line);
                    inventory_store.add(line);
                }
                if (salesSaveKey) {
                    Log.i("CHRIS",line);
                    sales_store.add(line);
                }

                if (line.contains("STATUS")) {
                    statusSaveKey = true;
                }
                if (line.contains("INVENTORY")) {
                    statusSaveKey = false;
                    inventorySaveKey = true;
                }
                if (line.contains("SALES")) {
                    inventorySaveKey = false;
                    salesSaveKey = true;
                }
            }

            Log.i("CHRIS_STATUS",status_store.toString());
            Log.i("CHRIS_INVENTORY",inventory_store.toString());
            Log.i("CHRIS_SALES",sales_store.toString());

            buffer.close();
        }
        catch (MalformedURLException bad_url) {
            Log.i("CHRIS", "bad url used;" + bad_url);
            noConnection = true;
        }
        catch (IOException io_err) {
            Log.i("CHRIS", "IO error?" + io_err.toString());
            noConnection = true;
        }
        catch (Error er){
            noConnection = true;
        }
    }
}
