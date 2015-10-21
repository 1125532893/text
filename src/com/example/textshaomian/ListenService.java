package com.example.textshaomian;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class ListenService extends Service implements OnKeyboardActionListener, KeyListener {
    private InputManager mInputManager;
    private inputListener mIL = new inputListener();
    private Handler mHandler = new Handler();
    private InputEvent mInputEvent;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInputManager = (InputManager) getApplicationContext().getSystemService(INPUT_SERVICE);
        mInputManager.registerInputDeviceListener(mIL, mHandler);
        System.out.println("oncreate the listenservice");

    }


    @Override
    public void onDestroy() {
        mInputManager.unregisterInputDeviceListener(mIL);
        super.onDestroy();
    }


    private class inputListener implements InputManager.InputDeviceListener {

        public inputListener() {
        }

        @Override
        public void onInputDeviceAdded(int arg0) {
            // TODO Auto-generated method stub
            KeyguardManager km = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
            //解锁  
            kl.disableKeyguard();
            //获取电源管理器对象  
            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
            //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag  
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            //点亮屏幕  
            wl.acquire();
            //释放  
            wl.release();
            System.out.println("1111111111");
        }

        @Override
        public void onInputDeviceChanged(int arg0) {
            // TODO Auto-generated method stub
            KeyguardManager km = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
            //解锁  
            kl.disableKeyguard();
            //获取电源管理器对象  
            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
            //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag  
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
            //点亮屏幕  
            wl.acquire();
            //释放  
            wl.release();
            System.out.println("22222222222");
        }

        @Override
        public void onInputDeviceRemoved(int arg0) {
            // TODO Auto-generated method stub

        }

    }


    @Override
    public void onKey(int arg0, int[] arg1) {
        // TODO Auto-generated method stub
        openScreen();
        System.out.println("333333333");
    }

    @Override
    public void onPress(int arg0) {
        // TODO Auto-generated method stub
        openScreen();
        System.out.println("444444444");
    }

    @Override
    public void onRelease(int arg0) {
        // TODO Auto-generated method stub
        openScreen();
        System.out.println("555555555");
    }

    @Override
    public void onText(CharSequence arg0) {
        // TODO Auto-generated method stub
        openScreen();
        System.out.println("6666666666");
    }

    @Override
    public void swipeDown() {
        // TODO Auto-generated method stub
        openScreen();
        System.out.println("7777777777");
    }

    @Override
    public void swipeLeft() {
        // TODO Auto-generated method stub
        openScreen();
        System.out.println("8888888888");
    }

    @Override
    public void swipeRight() {
        // TODO Auto-generated method stub
        openScreen();
        System.out.println("999999999");
    }

    @Override
    public void swipeUp() {
        // TODO Auto-generated method stub
        openScreen();
        System.out.println("1010101010101010");
    }


//    @Override
//    public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
//     // TODO Auto-generated method stub
//        KeyguardManager km = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);  
//        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");  
//        //解锁  
//        kl.disableKeyguard();  
//        //获取电源管理器对象  
//        PowerManager pm=(PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);  
//        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag  
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");  
//        //点亮屏幕  
//        wl.acquire();  
//        //释放  
//        wl.release();
//        System.out.println("333333333");
//        return false;
//    }

    private void openScreen() {
        KeyguardManager km = (KeyguardManager) getApplicationContext().getSystemService(
                Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        // 解锁
        kl.disableKeyguard();
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(
                Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        // 点亮屏幕
        wl.acquire();
        // 释放
        wl.release();
    }

    @Override
    public void clearMetaKeyState(View arg0, Editable arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getInputType() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean onKeyDown(View arg0, Editable arg1, int arg2, KeyEvent arg3) {
        // TODO Auto-generated method stub

        System.out.println("-----key down ---- " + arg3.getCharacters());
        return false;
    }

    @Override
    public boolean onKeyOther(View arg0, Editable arg1, KeyEvent arg2) {
        // TODO Auto-generated method stub
        System.out.println("-----key other ---- " + arg2.getCharacters());
        return false;
    }

    @Override
    public boolean onKeyUp(View arg0, Editable arg1, int arg2, KeyEvent arg3) {
        // TODO Auto-generated method stub
        System.out.println("-----key up ---- " + arg3.getCharacters());
        return false;
    }
}
