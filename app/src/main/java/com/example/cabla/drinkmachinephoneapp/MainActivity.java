package com.example.cabla.drinkmachinephoneapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button login_but;
    EditText username;
    EditText password;
    int attempts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginButton();
    }

    public void LoginButton(){
        //Widgets defined in XML file
        login_but = findViewById(R.id.button1);
        username = findViewById(R.id.user_entry);
        password = findViewById(R.id.pass_entry);

        login_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(username.getText().toString().equals("admin")
                        && password.getText().toString().equals("admin")){
                    //correct password

                    Toast.makeText(MainActivity.this,"Username and Password is correct",
                            Toast.LENGTH_SHORT).show();

                    Intent next_screen = new Intent("com.example.cabla.drinkmachinephoneapp.User");

                    /*DataRequester request = new DataRequester();

                    try{
                        Thread t = new Thread(request);
                        t.start();
                        t.join();

                    }
                    catch (InterruptedException i){
                        Log.d("ERROR","Thread issue");
                    }
                    boolean noData = request.getError();

                    if(noData){
                        next_screen.putExtra("no_data",noData);
                        Log.i("WTF","No data");
                    }

                    next_screen.putExtra("inventory",request.getInventoryData());
                    next_screen.putExtra("sales",request.getSalesData());
                    next_screen.putExtra("status",request.getStatusData());

                    Log.d("ERROR",request.getSalesData().toString());

                    if(request.getInventoryData().isEmpty()){
                        Log.i("CHRIS_EMPTY","inventory");
                    }
                    if(request.getStatusData().isEmpty()){
                        Log.i("CHRIS_EMPTY","status");
                    }
                    if(request.getSalesData().isEmpty()){
                        Log.i("CHRIS_EMPTY","sales");
                    }
                    */
                    startActivity(next_screen);  //goes to tabbed screen
                }else{
                    //incorrect password
                    Toast.makeText(MainActivity.this,"User-name and Password is not correct",
                            Toast.LENGTH_SHORT).show();
                    --attempts;
                    if(attempts==0){
                        login_but.setEnabled(false);
                        Toast.makeText(MainActivity.this,"Failure limit reached. Close app.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        );
    }

}
