package com.example.welcome.a3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PrintBill extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_bill);
        TextView trn=findViewById(R.id.transno);
        Bundle extras = this.getIntent().getExtras();
        int transno1=extras.getInt("transno");
        trn.setText("#"+Integer.toString(transno1));


        findViewById(R.id.gohome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
            }
        });

    }
}
