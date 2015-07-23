package csust.schoolnavi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import csust.schoolnavi.MyRoutePlanSearch;
import csust.schoolnavi.R;
import csust.schoolnavi.impl.RoutePlanMgr;
import csust.schoolnavi.interfaces.IMapFrag;
import csust.schoolnavi.present.MapPresent;

/**
 * Created by 7YHong on 2015/7/5.
 */
public class MyMapFragment extends Fragment implements IMapFrag {
    public final int MAP_SEARCHROUTE = 11;

    private MapView bmap;
    private BaiduMap map;
    private boolean isFirstLoc = true;
    private LocationClient mLocClient;
    private MyLocationListener mLocListener;
    private LocationMode mLocMode;

    MapPresent present;

    private RouteLine route;
    OverlayManager routeOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        present = new MapPresent(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(16).build();
        BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms)
                .compassEnabled(true).zoomControlsEnabled(true);
        bmap = new MapView(getActivity(), bo);
        map = bmap.getMap();
        //设置位置设定
        mLocMode = LocationMode.NORMAL;
        map.setMyLocationConfigeration(new MyLocationConfiguration(mLocMode, true, null));
        map.setMyLocationEnabled(true);
        // 定位初始化
        mLocListener = new MyLocationListener();
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(mLocListener);
        LocationClientOption option = new LocationClientOption();
        //option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);       //获取位置的间隔时间
        option.setNeedDeviceDirect(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        return bmap;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_searchroute: {
                Toast.makeText(getActivity(), "发起路径规划!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), MyRoutePlanSearch.class);
                startActivityForResult(i, MAP_SEARCHROUTE);
            }
            break;
            default:
                Toast.makeText(getActivity(), "暂未绑定事件", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        bmap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        bmap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.setMyLocationEnabled(false);
        bmap.onDestroy();
        mLocClient.stop();
    }

    @Override
    public void addRoute(DrivingRouteLine route) {
        DrivingRouteOverlay overlay = new DrivingRouteOverlay(map);
        routeOverlay = overlay;
        map.setOnMarkerClickListener(overlay);
        overlay.setData(route);
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    @Override
    public void addRoute(WalkingRouteLine route) {
        WalkingRouteOverlay overlay = new WalkingRouteOverlay(map);
        routeOverlay = overlay;
        map.setOnMarkerClickListener(overlay);
        overlay.setData(route);
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    @Override
    public void addRoute(TransitRouteLine route) {
        TransitRouteOverlay overlay = new TransitRouteOverlay(map);
        routeOverlay = overlay;
        map.setOnMarkerClickListener(overlay);
        overlay.setData(route);
        overlay.addToMap();
        overlay.zoomToSpan();

    }


    @Override
    public void clear() {
        map.clear();
    }


    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // mapfrag view 销毁后不在处理新接收的位置
            if (location == null || map == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            map.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                map.animateMapStatus(u);
            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAG","act result 1");
        if (requestCode != MAP_SEARCHROUTE) return;
        Log.i("TAG","act result 2");
        if (resultCode != MyRoutePlanSearch.SEARCH_WITHDISP) return;
        Log.i("TAG", "act result 3");
        present.dispCurrentOverlay();
    }
}
