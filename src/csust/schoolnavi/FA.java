package csust.schoolnavi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

public class FA extends FragmentActivity {
    // ??λ?????????Activity??
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
         * ????????λ????

        //mBaiduMap.setMyLocationEnabled(true);
        //Maps.getInstance().getMap().setMyLocationEnabled(true);
        ((Maps)getApplication()).getMap().setMyLocationEnabled(true);
        // ??λ?????
        mLocListener=new MyLocationListener();
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(mLocListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// ??gps
        option.setCoorType("bd09ll"); // ????????????
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();*/

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // ??λ?????????Activity??
        mLocClient.stop();
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // mapfrag view ????????????????λ??
            /*if (location == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // ??????????????????????????????0-360
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
