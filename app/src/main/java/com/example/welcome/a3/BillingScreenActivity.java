/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.welcome.a3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * reads barcodes.
 */
public class BillingScreenActivity extends Activity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView item_name;
    private TextView item_price;
    private TextView barcodeValue;
    ImageView Add2Cart;
    ImageView Reportmis;
    DatabaseHelper MDBH;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    int count=-1;
    String[] thisIsAStringArray = new String[100];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billingscreen);

        statusMessage = (TextView)findViewById(R.id.status_message);
        barcodeValue = (TextView) findViewById(R.id.barcode_value);
        item_name=(TextView) findViewById(R.id.ItemName);
        item_price=(TextView) findViewById(R.id.ItemPrice);

        try {
            MDBH = new DatabaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Add2Cart = (ImageView) findViewById(R.id.Add2Cart);
        Reportmis = findViewById(R.id.Reportmis);
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);
        findViewById(R.id.read_barcode).setOnClickListener(this);


        ImageView imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>=0) {
                    Intent i = new Intent(getApplicationContext(), CheckoutRecycle.class);
                    i.putExtra("itemdesc", thisIsAStringArray);
                    i.putExtra("count", count);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Cart is empty",Toast.LENGTH_LONG).show();
                }
            }
        });


        Add2Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!barcodeValue.getText().toString().isEmpty())
                {
                Cursor res;
                res = MDBH.getProdData(barcodeValue.getText().toString());
                if(res.getCount() == 0) {
                    statusMessage.setText("Nothing found");
                    item_name.setText("");
                    item_price.setText("");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    count++;
                    thisIsAStringArray[count] = barcodeValue.getText().toString();
                    count++;
                    thisIsAStringArray[count] = res.getString(0);
                    count++;
                    thisIsAStringArray[count] = res.getString(1);
                    Toast.makeText(getApplicationContext(),res.getString(0)+" added to cart",Toast.LENGTH_LONG).show();
                }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Item Scanned",Toast.LENGTH_LONG).show();
                }
            }
        });

        Reportmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeValue.setText("");
                item_name.setText("");
                item_price.setText("");
                Toast.makeText(getApplicationContext(),"SKU has been reported to the Admin",Toast.LENGTH_LONG).show();
            }
        });

        barcodeValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!barcodeValue.getText().toString().isEmpty())
                {
                Cursor res;

                res = MDBH.getProdData(barcodeValue.getText().toString());
                if(res.getCount() == 0) {
                    statusMessage.setText("Nothing found");
                    item_name.setText("");
                    item_price.setText("");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    item_name.setText(res.getString(0));
                    item_price.setText(res.getString(1));
                }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();

        Toast.makeText(getApplicationContext(),"Please Scan Barcode",Toast.LENGTH_LONG).show();
    }

}
