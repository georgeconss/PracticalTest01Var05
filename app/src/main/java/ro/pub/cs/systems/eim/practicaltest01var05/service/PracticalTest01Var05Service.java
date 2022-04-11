package ro.pub.cs.systems.eim.practicaltest01var05.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import ro.pub.cs.systems.eim.practicaltest01var05.general.Constants;

public class PracticalTest01Var05Service extends Service {
    ProcessingThread processingThread = null;

    public PracticalTest01Var05Service() {}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String intentString = intent.getStringExtra(Constants.INTENT_STRING);
        int intentPresses = intent.getIntExtra(Constants.INTENT_INT, -1);
        processingThread = new ProcessingThread(this, intentString, intentPresses);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
