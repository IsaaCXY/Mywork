package csust.schoolnavi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

public class FA extends FragmentActivity {
    // 定位消息跟随主Activity活动
    LocationClient mLocClient;
    private boolean isFirstLoc=true;
    MyLocationListener mLocListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {/*
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_fragment);
        Frag_Map2 fragmap = new Frag_Map2();
        FragmentManager fragmanager = getSupportFragmentManager();
        fragmanager.beginTransaction().add(R.id.mapfrag, fragmap, "mapfrag").commit();



        /**
         * 开始获取定位数据

        //mBaiduMap.setMyLocationEnabled(true);
        //Maps.getInstance().getMap().setMyLocationEnabled(true);
        ((Maps)getApplication()).getMap().setMyLocationEnabled(true);
        // 定位初始化
        mLocListener=new MyLocationListener();
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(mLocListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();*/

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 定位消息跟随主Activity活动
        mLocClient.stop();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // mapfrag view 销毁后不在处理新接收的位置
            /*if (location == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            ((Maps)getApplication()).getMap().setMyLocationData(locData);
            //mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                ((Maps)getApplication()).getMap().animateMapStatus(u);
            }*/
        }

    }
}
