package com.alexkmbk.intentfilter;

import android.content.Context;
import android.widget.Toast;

import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;

public class ZplPrinter {

    public String log;
    private Context context;
    private Connection thePrinterConn;

    public ZplPrinter(Context context){
        this.context = context;
        this.log = "";
    }

    public boolean sendZplOverBluetooth(final String theBtMacAddress, String zplData) {

        try {
            if (Connect(theBtMacAddress)) {
                log = log + "Getting printer instance... \r\n";
                ZebraPrinter printer = ZebraPrinterFactory.getInstance(thePrinterConn);
                printer.sendCommand(zplData);
            }

        } catch (Exception e) {
            // Handle communications error here.
            log = log + e.getMessage();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Boolean Connect(String theBtMacAddress) {
        try {
            if (thePrinterConn == null || !thePrinterConn.isConnected()) {

                log = log + "Connecting to the printer...\r\n";
                Toast.makeText(context, "Connecting to the printer...", Toast.LENGTH_SHORT).show();

                thePrinterConn = new BluetoothConnection(theBtMacAddress);

                // Open the connection - physical connection is established here.
                thePrinterConn.open();

                log = log + "Printer connected\r\n";
                Toast.makeText(context, "Printer connected", Toast.LENGTH_SHORT).show();

                return true;
            }
        } catch (Exception e) {
            log = log + e.getMessage() + "\r\n";
            log = log + "Connecting to the printer...\r\n";
            Toast.makeText(context, "Connecting to the printer...", Toast.LENGTH_SHORT).show();
            try {
                thePrinterConn = new BluetoothConnection(theBtMacAddress);
                thePrinterConn.open();
                log = log + "Printer connected\r\n";
                Toast.makeText(context, "Printer connected", Toast.LENGTH_SHORT).show();
            } catch (Exception e2) {
                log = log + e.getMessage() + "\r\n";
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        return false;
    }
}
