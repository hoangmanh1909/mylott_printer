package com.mbl.lottery.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class TimerThread {
    public static Date timer;
    public TimerThread(String strDateTimeNow) {
        timer = DateTimeUtils.convertStringToDateDefault(strDateTimeNow);
        new Thread(() -> {
            while (true){
                timer = DateUtils.addSeconds(timer, 1);
//                Log.d("Timer",DateTimeUtils.convertDateToString(timer,""));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
