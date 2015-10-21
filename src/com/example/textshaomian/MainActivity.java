package com.example.textshaomian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnKeyListener {
    private EditText etText;
    private TextView tvText;
    private String textcontent;
    private Context mContext;
    private Button btnText;
    KeyguardManager km;
    KeyguardManager.KeyguardLock kl;
    PowerManager pm;
    PowerManager.WakeLock wl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(getApplicationContext(), ListenService.class));
        mContext = getApplicationContext();

        km = (KeyguardManager) mContext
                .getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("unLock");
        // 解锁
        kl.disableKeyguard();
        // 获取电源管理器对象
        pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        // 点亮屏幕
        wl.acquire();
        // 释放
        wl.release();
        btnText = (Button) findViewById(R.id.btn_text);
        btnText.setVisibility(View.GONE);
        btnText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });

        etText = (EditText) findViewById(R.id.et_text);
        tvText = (TextView) findViewById(R.id.tv_text);
        etText.setFocusable(true);
        etText.setFocusableInTouchMode(true);
        etText.requestFocus();
        etText.findFocus();
        etText.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View arg0, int keyCode, KeyEvent event) {
                wl.acquire();
                // 释放
                wl.release();
                String content = etText.getText().toString().trim();
                if (keyCode == KeyEvent.KEYCODE_ENTER && content.length() > 0) {
                    Toast.makeText(MainActivity.this, content,
                            Toast.LENGTH_SHORT).show();
                    etText.setText(content);

                }
                System.out.println(keyCode);
                return false;
            }
        });
        setListeningEquipment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // wl.acquire();
        // wl.release();
        // textcontent = "";
        // textcontent+=event.getCharacters();
        // if (keyCode == KeyEvent.KEYCODE_ENTER ) {
        // Toast.makeText(MainActivity.this, textcontent,
        // Toast.LENGTH_SHORT).show();
        // textcontent="";
        // }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
        wl.acquire();
        wl.release();
        textcontent = "";
        textcontent += arg2.getCharacters();
        if (arg1 == KeyEvent.KEYCODE_ENTER) {
            Toast.makeText(MainActivity.this, textcontent, Toast.LENGTH_SHORT)
                    .show();
            textcontent = "";
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        stopService(new Intent(getApplicationContext(), ListenService.class));
    }

    private void setListeningEquipment() {
        try {

            // 获得外接USB输入设备的信息
            Process p = Runtime.getRuntime()
                    .exec("cat /proc/bus/input/devices");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line = null;
            String textcontent = "";
            while ((line = in.readLine()) != null) {
                String deviceInfo = line.trim();
                // 对获取的每行的设备信息进行过滤，获得自己想要的。
                Pattern ptn = Pattern.compile("\"(.*?)\"");
                Matcher m = ptn.matcher(deviceInfo);
                while (m.find()) {
                    if (m.group().contains("NewLand HidKeyBoard")) {
                        textcontent += "设备成功了";
                    }
                }
            }
            if (textcontent.equals("")) {
                textcontent = "设备连接失败，请检查设备连接";
            }
            //监听后操作输出

            tvText.setText(textcontent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
