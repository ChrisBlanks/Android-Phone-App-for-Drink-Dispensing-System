package com.example.cabla.drinkmachinephoneapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataRequester implements Runnable {

    private volatile ArrayList<String> status_store = new ArrayList<>();
    private volatile ArrayList<String> inventory_store = new ArrayList<>();
    private volatile ArrayList<String> sales_store = new ArrayList<>();
    private volatile ArrayList<String> dates = new ArrayList<>();
    private volatile boolean noConnection = false;
    private volatile boolean isDone = false;
    private String url = "" ;

    DataRequester(String desired_url){
        if(url != null){
            url = desired_url;
        }
        else{
            url = "http://10.0.0.35:8000/shared_data_2018-11-22.txt";
        }
    }

    @Override
    public void run(){
        retrieveFiles();
    }


    public boolean getError(){
        return noConnection;
    }
    public ArrayList<String> getDates(){
        return dates;
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
            file_location = new URL(url);
            Log.i("CHRIS1",file_location.getFile());
            String name =file_location.getFile();
            String regex;
            Matcher m ;
            Date date;

            //retrieve data of file from its name
            try{
                String[] temp_str = name.split("/"); //split file path into location & name
                int counter_t = 0;
                for(String i: temp_str){
                    Log.i("CHRIS_DATE1",temp_str[counter_t]);
                    counter_t++;
                    Log.i("CHRIS_DATE2",i);
                    if(i.contains("shared_data")){
                        regex = "(\\d{4}-\\d{2}-\\d{2})"; //4 digit year, 2 digit month, 2 digit day
                        m = Pattern.compile(regex).matcher(i);
                        if(m.find()) { //if found in substring then reformat into year month day
                            Log.i("CHRIS_DATE3",m.group(1));
                            date = new SimpleDateFormat("yyyy-MM-dd").parse(m.group(1));
                            dates.add(date.toString());
                            Log.i("CHRIS_DATE4",date.toString());
                        }else{
                            Log.i("CHRIS_DATE5","couldn't find");
                        }
                    }
                }
            }
            catch(ParseException e){ Log.i("CHRIS_TEST_REG",e.toString()); }

            HttpURLConnection huc =  (HttpURLConnection)file_location.openConnection() ;
            HttpURLConnection.setFollowRedirects(false);
            int ONE_SEC = 1000;
            huc.setConnectTimeout(3 * ONE_SEC);
            huc.setReadTimeout(3 * ONE_SEC);
            huc.setRequestMethod("GET");
            huc.connect();
            buffer = new BufferedReader(new InputStreamReader(huc.getInputStream()));
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
