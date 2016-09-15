package com.example.alex.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public String address = "192.168.0.27";
    private Socket client;
    private PrintWriter printwriter;
    private String messsage = "no text";
    List<String> pc_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pc_address = new ArrayList<>();


        ///textField = (EditText) findViewById(R.id.editText); // reference to the text field

        ScanLocalDevices devicesTask = new ScanLocalDevices();
        devicesTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //parallel execution with commanding task
    }

    public void space(View view) {
        messsage = "space";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    public void altright(View view) {
        messsage = "altright";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    public void altleft(View view) {
        messsage = "altleft";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    public void left(View view) {
        messsage = "left";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    public void right(View view) {
        messsage = "right";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    public void esc(View view) {
        messsage = "esc";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    public void volumeup(View view) {
        messsage = "volume up";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    public void volumedown(View view) {
        messsage = "volume down";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }

    public void mute(View view) {
        messsage = "mute";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }
    public void doubletap(View view) {
        messsage = "double tap";
        SendMessage sendMessageTask = new SendMessage();
        sendMessageTask.execute();
    }
    public void getIPs(View view){
        ScanLocalDevices devicesTask = new ScanLocalDevices();
        devicesTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //parallel execution with commanding task
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, pc_address);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }


    private class SendMessage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                client = new Socket(address, 4444); // connect to the server
                printwriter = new PrintWriter(client.getOutputStream(), true);
                printwriter.write(messsage); // write the message to output stream

                printwriter.flush();
                printwriter.close();
                client.close(); // closing the connection

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class ScanLocalDevices extends AsyncTask<Void, Void, Void> {
        String[] private_subnet = {"192.168.0", "192.168.1"};

        String IPSCAN_TAG = "IP Scan: ";

        @Override
        protected Void doInBackground(Void... params) {
            checkHosts(private_subnet);
            return null;
        }

        private void checkHosts(String[] subnet) {
            int timeout = 50;
            for(int j = 0; j < subnet.length; j++ ){
                for (int i=1;i<255;i++){
                    String host_ip=subnet[j] + "." + i;
                    //Log.e(IPSCAN_TAG, host_ip);
                    try {
                        if (InetAddress.getByName(host_ip).isReachable(timeout)){
                            Log.d(IPSCAN_TAG, host_ip + " is reachable");
                            //InetAddress inetAddress = InetAddress.getByName(host_ip);
                            //String host_name = inetAddress.getHostName();
                            pc_address.add(host_ip); //+ " - "+ host_name);
                        }
                    } catch (IOException e) {
                        Log.e(IPSCAN_TAG, "some error");
                    }
                }
            }
            Log.e(IPSCAN_TAG, "Scan finished");
        }
    }
}
