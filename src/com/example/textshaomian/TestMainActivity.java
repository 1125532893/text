package com.example.textshaomian;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestMainActivity extends Activity {
    private EditText etText;
    private TextView tvText;
    private String textcontent;
    private Context mContext;
    private Button btnText;
    private LocationClient mLocClient;
    private Vibrator mVibrator01 = null;
    public MyLocationListenner myListener = new MyLocationListenner();
    private boolean mIsStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(getApplicationContext(), ListenService.class));
        mContext = getApplicationContext();

        btnText = (Button) findViewById(R.id.btn_text);


        btnText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mIsStart) {
                    setLocationOption();
                    mLocClient.start();
                    btnText.setText("停止");
                    mIsStart = true;

                } else {
                    mLocClient.stop();
                    mIsStart = false;
                    btnText.setText("开始");
                }

            }
        });

        etText = (EditText) findViewById(R.id.et_text);
        tvText = (TextView) findViewById(R.id.tv_text);
        etText.setFocusable(true);
        etText.setFocusableInTouchMode(true);
        etText.requestFocus();
        etText.findFocus();
    }

    private void dingwei() {
        // TODO Auto-generated method stub
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        mVibrator01 = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);

    }

    //设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(false);                //打开gps
        option.setCoorType("bd09ll");        //设置坐标类型
        option.setAddrType("all");        //设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
        option.setScanSpan(0);    //设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
        mLocClient.setLocOption(option);
    }

    /**
     * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\n省：");
                sb.append(location.getProvince());
                sb.append("\n市：");
                sb.append(location.getCity());
                sb.append("\n区/县：");
                sb.append(location.getDistrict());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
            }
            sb.append("\nsdk version : ");
            sb.append(mLocClient.getVersion());
            tvText.setText((sb.toString()));
        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("Poi time : ");
            sb.append(poiLocation.getTime());
            sb.append("\nerror code : ");
            sb.append(poiLocation.getLocType());
            sb.append("\nlatitude : ");
            sb.append(poiLocation.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(poiLocation.getLongitude());
            sb.append("\nradius : ");
            sb.append(poiLocation.getRadius());
            if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(poiLocation.getAddrStr());
            }

            if (poiLocation.hasAddr()) {
                sb.append("\nPoi:");
                sb.append(poiLocation.getAddrStr());
            } else {
                sb.append("noPoi information");
            }
            tvText.setText((sb.toString()));
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        stopService(new Intent(getApplicationContext(), ListenService.class));
    }

}
