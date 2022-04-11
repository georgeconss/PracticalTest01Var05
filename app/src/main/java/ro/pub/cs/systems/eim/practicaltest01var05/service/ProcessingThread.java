package ro.pub.cs.systems.eim.practicaltest01var05.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

import ro.pub.cs.systems.eim.practicaltest01var05.general.Constants;

public class ProcessingThread extends Thread {
    private Context context;
    private String text;
    private int numPresses;

    private boolean isRunning = false;

    public ProcessingThread(Context context, String text, int numPresses) {
        this.context = context;
        this.text = text;
        this.numPresses = numPresses;
    }

    public ProcessingThread() {
        this.context = null;
    }

    @Override
    public void run() {
       for (int i = 0; i < numPresses; ++i) {
            sendMessage();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage() {
        Log.d("ana", "sendMessage() " + text);
        Intent intent = new Intent();
        intent.setAction(Constants.SERVICE_ACTION);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, text);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}
