# ZplOverBluetoothPrintService
Android service to send ZPL commands to a Bluetooth printer.

The service provides just one intent: **com.alexkmbk.intent.action.sendZPL**

**Extra params:**
  - MacAddress (Mac address of the Bluetooth printer) (optional)
  - ZPLCode (zpl commands string) (optional)
  - Command (optional, only "Connect" command is available for now)
  
**Examples:**

To make a connection (or reconnect) to the printer from 1C:Enterpise for Android:

```bsl
ApplicationRun = New MobileDeviceApplicationRun;
ApplicationRun.Action = "com.alexkmbk.intent.action.sendZPL";
ApplicationRun.AdditionalData.Add("MacAddress", PrinterInfo.MacAddress);
ApplicationRun.AdditionalData.Add("Command", "Connect");
ApplicationRun.Run(False);		     	
```
To send some ZPL commands to the printer from 1C:Enterpise for Android:

```bsl
ApplicationRun = New MobileDeviceApplicationRun;
ApplicationRun.Action = "com.alexkmbk.intent.action.sendZPL";
ApplicationRun.AdditionalData.Add("MacAddress", "00:03:7a:6c:6e:4d");
ApplicationRun.AdditionalData.Add("ZPLCode", "^XA^FO20,20^A0N,25,25^FDThis is a ZPL test.^FS^XZ");
ApplicationRun.Run(False);		     		     	
```
**Note:** It is not necessary to call "Connect" command before printing, as soons as the connection will be established automatically if required. 
The "Connect" command is necessary only if the printer was switched to the hibernation mode after the first connection had been established. 
In this case you have to reconnect to the printer before sending any new ZPL commands.
