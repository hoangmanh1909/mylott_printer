package com.mbl.lottery.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.Vector;

public class BluetoothServices extends Service {
    private BluetoothAdapter mBluetoothAdapter;
    public static final String B_DEVICE = "MY DEVICE";
    public static final String B_UUID = "00001101-0000-1000-8000-00805f9b34fb";
// 00000000-0000-1000-8000-00805f9b34fb

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_FAIL = 4;


    private ConnectBtThread mConnectThread;
    private static ConnectedBtThread mConnectedThread;

    private static Handler mHandler = null;
    public static int mState = STATE_NONE;
    public static String deviceName;
    public static BluetoothDevice sDevice = null;
    public Vector<Byte> packData = new Vector<>(2048);

//IBinder mIBinder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        mHandler = ((MainActivity) getBaseContext()).getHandler();
        return mBinder;
    }

    public void toast(String mess) {
        //Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        BluetoothServices getService() {
            // Return this instance of LocalService so clients can call public methods
            return BluetoothServices.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String device = intent.getStringExtra("bluetooth_device");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        connectToDevice(device);

        return START_STICKY;
    }

    private synchronized void connectToDevice(String macAddress) {
        try {
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);
            if (mState == STATE_CONNECTING) {
                if (mConnectThread != null) {
                    mConnectThread.cancel();
                    mConnectThread = null;
                }
            }
            if (mConnectedThread != null) {
                mConnectedThread.cancel();
                mConnectedThread = null;
            }
            mConnectThread = new ConnectBtThread(device);
            //toast("connecting");
            mConnectThread.start();
            setState(STATE_CONNECTING);
        } catch (Exception e) {
            setState(STATE_NONE);
            Log.d("connectToDevice", "e.printStackTrace()");
        }
    }

    private void setState(int state) {
        mState = state;
//        if (mHandler != null) {
//            mHandler.obtainMessage();
//        }
    }

    public synchronized void stop() {
        setState(STATE_NONE);
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        stopSelf();
    }

    public static void sendData(String message) {
        if (mConnectedThread != null) {
            mConnectedThread.write(message.getBytes());
            //toast("sent data");
        } else {
            //Toast.makeText(BluetoothServices.this, "Failed to send data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean stopService(Intent name) {
        setState(STATE_NONE);

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mBluetoothAdapter.cancelDiscovery();
        return super.stopService(name);
    }

    private class ConnectBtThread extends Thread {
        private final BluetoothSocket mSocket;
        private final BluetoothDevice mDevice;

        public ConnectBtThread(BluetoothDevice device) {
            mDevice = device;
            BluetoothSocket socket = null;
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(B_UUID));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSocket = socket;

        }

        @Override
        public void run() {
            mBluetoothAdapter.cancelDiscovery();

            try {
                mSocket.connect();
                Log.d("service", "connect thread run method (connected)");

//                SharedPreferences pre = getSharedPreferences("BT_NAME", 0);
//                pre.edit().putString("bluetooth_connected", mDevice.getName()).apply();

                setState(STATE_CONNECTED);
                mConnectedThread = new ConnectedBtThread(mSocket);
                mConnectedThread.start();

            } catch (IOException e) {
                try {
                    mSocket.close();
                    setState(STATE_FAIL);
                    Log.d("service", "connect thread run method ( close function)");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    setState(STATE_FAIL);
                }
                e.printStackTrace();
                setState(STATE_FAIL);
            }
            //connected(mSocket);
            //setState(STATE_CONNECTED);
//            mConnectedThread = new ConnectedBtThread(mSocket);
//            mConnectedThread.start();
        }

        public void cancel() {

            try {
                mSocket.close();
                Log.d("service", "connect thread cancel method");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ConnectedBtThread extends Thread {
        private final BluetoothSocket cSocket;
        private final InputStream inS;
        private final OutputStream outS;

        private byte[] buffer;

        public ConnectedBtThread(BluetoothSocket socket) {
            cSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inS = tmpIn;
            outS = tmpOut;
        }

        @Override
        public void run() {
            buffer = new byte[1024];
            int mByte;
            try {
                mByte = inS.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("service", "connected thread run method");

        }


        public void write(byte[] buff) {
            try {
                outS.write(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void cancel() {
            try {
                cSocket.close();
                Log.d("service", "connected thread cancel method");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void onDestroy() {
        this.stop();
        super.onDestroy();
    }
}
