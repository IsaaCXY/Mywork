package csust.schoolnavi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import csust.schoolnavi.fragment.MyMapFragment;

/**
 * Created by 7YHong on 2015/7/5.
 */
public class MainActivity extends FragmentActivity {
    MyMapFragment mapfrag;
    MapView bmap;
    BaiduMap map;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_fragment);
        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(15).build();
        BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms)
                .compassEnabled(false).zoomControlsEnabled(true);
        mapfrag = new MyMapFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.map, mapfrag, "map_fragment").commit();



    }

}