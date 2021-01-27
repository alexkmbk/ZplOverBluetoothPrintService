package com.alexkmbk.intentfilter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartPrintService extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String MacAddress = intent.getStringExtra("MacAddress");
        String ZPLCode = intent.getStringExtra("ZPLCode");
        String Command = intent.getStringExtra("Command");

        Intent ServiceIntent = new Intent(this, ActivityPrintZPL.class);
        ServiceIntent.putExtra("MacAddress", MacAddress);
        ServiceIntent.putExtra("ZPLCode", ZPLCode);
        ServiceIntent.putExtra("Command", Command);

        startService(ServiceIntent);

        finish();
    }

}
