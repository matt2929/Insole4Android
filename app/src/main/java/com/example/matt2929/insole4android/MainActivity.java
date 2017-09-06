package com.example.matt2929.insole4android;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    final static int REQUEST_ENABLE_BT = 1;
    ListView listView;
    Button refresh;
    BluetoothAdapter mBluetoothAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerViews();
        checkBluetoothPermission();
    }
    public void onRefreshClick(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayList<String> AllDevices=new ArrayList<>();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {

                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                AllDevices.add("Name: "+deviceName+" Mac: "+deviceHardwareAddress);
            }
        }
        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, AllDevices));
    }

    public void checkBluetoothPermission(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
    public void registerViews(){
        listView=(ListView) findViewById(R.id.listView);
        refresh=(Button)findViewById(R.id.refreshButton);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefreshClick();
            }
        });
    }
}
