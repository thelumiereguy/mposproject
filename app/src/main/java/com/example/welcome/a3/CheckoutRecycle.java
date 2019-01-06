package com.example.welcome.a3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutRecycle extends Activity {

    int transno=0;
    DatabaseHelper MDBH,MDBH1;
    SQLiteDatabase sqliteDataBase;
    double sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_recycle);
        final RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        RecyclerView.Adapter adapter;
        recyclerView=findViewById(R.id.inf);
        TextView textView=findViewById(R.id.datetrans);
        final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Intent i = getIntent();
        String[] arrstring=i.getStringArrayExtra("itemdesc");
        textView.setText(currentDate);
        int desccount=i.getIntExtra("count",0);
        TextView total=findViewById(R.id.totalamount);
        ImageView card,cash;
        card=findViewById(R.id.card);
        cash=findViewById(R.id.cash);
        TextView canceltransaction=findViewById(R.id.canceltransaction);
        TextView transactionno=findViewById(R.id.transactionno);
        try {
            MDBH = new DatabaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ListItems> listItems3;
        listItems3 = new ArrayList<>();

        for(int j=0;j<=desccount-2;j++) {
            listItems3.add(
                    new ListItems(
                            1,
                            arrstring[j],
                            arrstring[j+1],
                            arrstring[j+2]

                    ));
        j=j+2;
        }

        for (int k=2;k<=desccount;k++)
        {
            sum+=Double.parseDouble(arrstring[k]);
            k=k+2;
        }
        transactionno.setText(Integer.toString(1));

        Cursor res;
        res = MDBH.billdata();
        while(res.moveToNext())
        {
            transno=res.getInt(0)+1;
        }
        transactionno.setText(Integer.toString(transno));



        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MDBH1 = new DatabaseHelper(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                boolean success = MDBH1.insertbill(transno,currentDate,sum,2,"1234");
                if(success) {
                    Intent intent = new Intent(getApplicationContext(), PrintBill.class);
                    intent.putExtra("transno", transno);
                    Log.v("transno",Integer.toString(transno));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Joy",Toast.LENGTH_LONG).show();
                }
            }
        });


        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    MDBH1 = new DatabaseHelper(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean success = MDBH1.insertbill(transno,currentDate,sum,1,"cash");
                if(success) {
                    Intent intent = new Intent(getApplicationContext(), PrintBill.class);
                    intent.putExtra("transno", transno);
                    Log.v("transno",Integer.toString(transno));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Joy",Toast.LENGTH_LONG).show();
                }
            }
        });



        total.setText("Rs. "+sum);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MainAdapter(listItems3,this);
        recyclerView.setAdapter(adapter);

        canceltransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
