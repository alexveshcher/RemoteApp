package com.example.alex.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends Activity {

    public String address = "192.168.1.9";
    private Socket client;
    private PrintWriter printwriter;
    private String messsage = "no text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///textField = (EditText) findViewById(R.id.editText); // reference to the text field
//        space = (Button) findViewById(R.id.space); // reference to the send space
//        altleft = (Button) findViewById(R.id.altleft);
//        altright = (Button) findViewById(R.id.altright);

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

        String IPSCAN_TAG = "IP Scan: ";

        @Override
        protected Void doInBackground(Void... params) {
            checkHosts("192.168.1");
            return null;
        }

        public void checkHosts(String subnet) {
            int timeout = 50;
            for (int i=1;i<255;i++){
                String host=subnet + "." + i;
                try {
                    if (InetAddress.getByName(host).isReachable(timeout)){
                        Log.d(IPSCAN_TAG, host + " is reachable");
                    }
                } catch (IOException e) {
                    Log.e(IPSCAN_TAG, "some error");
                }
            }
            Log.e(IPSCAN_TAG, "Scan finished");
        }
    }
}
