package org.androidtutorials.receiver;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This application demonstrates the use of BroadcastReceiver to receive system generated
 * announcements. The app also demonstrates broadcasting and receiving a custom broadcast. In
 * particular the app registers a receiver for Wi-Fi and Bluetooth state changes. When user toggles
 * Wi-Fi or Bluetooth, the app updates the UI to indicate that the broadcast has been received.
 * For custom broadcast, a button is provided , when clicked the app broadcasts a custom message through
 * an intent object. The reception of this broadcast is also displayed on UI.
 */
public class MainActivity extends Activity {

    /**
     * Entry point of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * onResume() callback method is invoked when the activity appears in the foreground. This is an
     * ideal place to register the receivers because when user pull the notification shade to toggle
     * Bluetooth or Wi-Fi, the onPause() callback method is invoked, where these recivers are
     * unregistered. Pressing back button to return to the app will invoke the onResume() callback
     * method and here we register all the receivers again.
     */
    @Override
    protected void onResume() {
        super.onResume();

        /**
         * Create an intent filter with action as ACTION_STATE_CHANGED to receive broadcast when
         * Bluetooth is toggled. Register the receiver using above intent filter.
         */
        IntentFilter intentFilterBT = new IntentFilter();
        intentFilterBT.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(broadcastReceiverBT, intentFilterBT);

        /**
         * Create an intent filter with action as a custom defined string to receive broadcast that
         * have the exact same string in its intent object.
         * Register the receiver using above intent filter.
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageName() + ".uniqueIntentCustom");
        registerReceiver(broadcastReceiver, intentFilter);

        /**
         * Create an intent filter with action as WIFI_STATE_CHANGED_ACTION to receive broadcast when
         * Wi-Fi is toggled.
         * Register the receiver using above intent filter.
         */
        IntentFilter intentFilterWiFi = new IntentFilter();
        intentFilterWiFi.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broadcastReceiverWiFi, intentFilterWiFi);
    }

    /**
     * Instantiate an object of a class that extends BroadcastReceiver Class.
     * The overridden onReceive() method is invoked when a broadcast with matching intent is received.
     * In this case, UI is updated when broad is received.
     */
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(MainActivity.this, "Custom broadcast received: " + action, Toast.LENGTH_SHORT).show();
            TextView textViewCustom = (TextView) findViewById(R.id.textViewCustom);
            textViewCustom.setText("Yes!");
            textViewCustom.setTextColor(Color.GREEN);
        }
    };

    /**
     * Instantiate an object of a class that extends BroadcastReceiver Class.
     * The overridden onReceive() method is invoked when a broadcast with matching intent is received.
     * In this case, UI is updated when broad is received.
     */
    private final BroadcastReceiver broadcastReceiverBT = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(MainActivity.this, "BT toggled: " + action, Toast.LENGTH_SHORT).show();
            TextView textViewBT = (TextView) findViewById(R.id.textViewBT);
            textViewBT.setText("Yes!");
            textViewBT.setTextColor(Color.GREEN);
        }
    };

    /**
     * Instantiate an object of a class that extends BroadcastReceiver Class.
     * The overridden onReceive() method is invoked when a broadcast with matching intent is received.
     * In this case, UI is updated when broad is received.
     */
    private final BroadcastReceiver broadcastReceiverWiFi = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(MainActivity.this, " WiFi toggled: " + action, Toast.LENGTH_SHORT).show();
            TextView textViewWiFi = (TextView) findViewById(R.id.textViewWiFi);
            textViewWiFi.setText("Yes!");
            textViewWiFi.setTextColor(Color.GREEN);
        }
    };

    /**
     * onPause() is callback method invoked when the activity is no longer in foreground. In this case
     * the receivers are unregistered to conserve resources. onPause() method should be kept light
     * weight to keep the UI transition smooth.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverBT);
        unregisterReceiver(broadcastReceiverWiFi);
    }

    /**
     * This is a custom method invoked when "Send custom broadcast" button is clicked. This method
     * instantiates an intent object with action set as a unique string. The intent object is then
     * used to broadcast using sendBroadcast() method.
     */
    public void sendCustomBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".uniqueIntentCustom");
        sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * The clear option in overflow menu will set all the messages to default of "No". This provides an
     * opportunity to user to trigger the provided broadcasts again and observe.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                clearFields();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Custom method invoked when clear option is clicked in the overflow menu.
     */
    public void clearFields() {
        TextView textViewCustom = ((TextView) findViewById(R.id.textViewCustom));
        textViewCustom.setText("No");
        textViewCustom.setTextColor(Color.RED);

        TextView textViewBT = ((TextView) findViewById(R.id.textViewBT));
        textViewBT.setText("No");
        textViewBT.setTextColor(Color.RED);

        TextView textViewWiFi = ((TextView) findViewById(R.id.textViewWiFi));
        textViewWiFi.setText("No");
        textViewWiFi.setTextColor(Color.RED);
    }
}
