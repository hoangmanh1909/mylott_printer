package com.mbl.lottery.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.DateTimeUtils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends Service {
    int startMode;       // indicates how to behave if the service is killed
    IBinder binder;      // interface for clients that bind
    boolean allowRebind; // indicates whether onRebind should be used
    public static Date dateTimer;

    @Override
    public void onCreate() {
        // The service is being created
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        String strDateTimeNow = intent.getStringExtra(Constants.KEY_DATE_TIME_NOW);
        dateTimer = DateTimeUtils.convertStringToDateDefault(strDateTimeNow);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                dateTimer = DateUtils.addSeconds(dateTimer, 1);
//                Log.d("Timer",DateTimeUtils.convertDateToString(dateTimer,""));
            }
        };
        long delay = 1000L;
        Timer timer = new Timer("TimerGetDateTimeNow");
        timer.schedule(timerTask, 0, delay);
//        try {
//            while (true) {
//                try {
//                    dateTimer = DateUtils.addSeconds(dateTimer, 1);
//                    //Log.d("Timer",DateTimeUtils.convertDateToString(dateTimer,""));
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }catch (Exception e){}

        return startMode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return allowRebind;
    }

    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed
    }
}
