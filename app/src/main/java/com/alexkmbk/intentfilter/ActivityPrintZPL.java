package com.alexkmbk.intentfilter;

import android.app.Service;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

public class ActivityPrintZPL extends Service {

    private String Log;
    Connection thePrinterConn;

    public void onCreate() {
        super.onCreate();

    }

    public boolean sendZplOverBluetooth(final String theBtMacAddress, String zplData) {

        try {

            try {
                if (thePrinterConn == null || !thePrinterConn.isConnected()) {
                    thePrinterConn = new BluetoothConnection(theBtMacAddress);
                    // Open the connection - physical connection is established here.
                    thePrinterConn.open();

                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e){
                thePrinterConn = new BluetoothConnection(theBtMacAddress);
                thePrinterConn.open();
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception soundExc) {
                    soundExc.printStackTrace();
                }
            }



            //Log = Log + "Getting printer instance... \r\n";
            ZebraPrinter printer = ZebraPrinterFactory.getInstance(thePrinterConn);
            printer.sendCommand(zplData);

        } catch (Exception e) {
            // Handle communications error here.
            Log = Log + e.getMessage();
            return false;
        }
        return true;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private void Connect(String theBtMacAddress) {
        try {
            Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_LONG).show();
            thePrinterConn = new BluetoothConnection(theBtMacAddress);
            // Open the connection - physical connection is established here.
            thePrinterConn.open();

            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        String MacAddress = intent.getStringExtra("MacAddress");
        String ZPLCode = intent.getStringExtra("ZPLCode");
        String Command = intent.getStringExtra("Command");

        if (Command != null && Command.equals("Connect"))
        {
            Connect(MacAddress);
            return super.onStartCommand(intent, flags, startId);
        }

        Log = "";

        boolean Res = sendZplOverBluetooth(MacAddress, ZPLCode);

        intent.putExtra("Log", Log);
        intent.putExtra("Result", Res);

        return super.onStartCommand(intent, flags, startId);

    }

}
