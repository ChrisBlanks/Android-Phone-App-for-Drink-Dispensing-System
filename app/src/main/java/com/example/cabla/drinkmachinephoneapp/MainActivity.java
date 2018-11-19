package com.example.cabla.drinkmachinephoneapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;


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
