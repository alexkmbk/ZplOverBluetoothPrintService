package com.alexkmbk.intentfilter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.alexkmbk.zploverbluetooth.R;

public class MainActivity extends AppCompatActivity {

    ZplPrinter zplPrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText text =  findViewById(R.id.MacAddressEditText);
        text.setText("00:03:7a:6c:6e:4d");
        zplPrinter = new ZplPrinter(this);
    }

    //@Override
    public void onClick(View view) {

        EditText text = findViewById(R.id.Log);
        EditText MacAddress = findViewById(R.id.MacAddressEditText);
        zplPrinter.sendZplOverBluetooth(MacAddress.getText().toString(), "^XA^FO20,20^A0N,25,25^FDThis is a ZPL test.^FS^XZ");
        text.setText(zplPrinter.log);
    }

}
