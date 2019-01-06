package com.example.welcome.a3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText t1=findViewById(R.id.userid);
        final EditText t2=findViewById(R.id.password);
        Button b1=findViewById(R.id.loginbutton);
        TextView tv=findViewById(R.id.forgotpw);
        t1.setText("");
        t2.setText("");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t1.getText().toString().equals("1234") && t2.getText().toString().equals("1234")) {
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        EditText t1=findViewById(R.id.userid);
        EditText t2=findViewById(R.id.password);
        super.onResume();
        t1.setText("");
        t2.setText("");


    }



}

