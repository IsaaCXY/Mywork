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
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import csust.schoolnavi.MyRoutePlanSearch;
import csust.schoolnavi.R;
import csust.schoolnavi.impl.RoutePlanMgr;

import java.util.List;
import java.util.Map;

/**
 * Created by 7YHong on 2015/7/5.
 */
public class MyMapFragment extends Fragment {
    public final int MAIN_SEARCHROUTE = 1;

    private MapView bmap;
    private BaiduMap map;
    private boolean isFirstLoc = true;
    private LocationClient mLocClient;
    private MyLocationListener mLocListener;
    private LocationMode mLocMode;

    private RouteLine route;
    OverlayManager routeOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(15).build();
        BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms)
                .compassEnabled(false).zoomControlsEnabled(true);
        bmap = new MapView(getActivity(), bo);
        map = bmap.getMap();
        //设置位置设定
        mLocMode = LocationMode.NORMAL;
        map.setMyLocationConfigeration(new MyLocationConfiguration(mLocMode, true, null));
        //启动一个Activity
        //startActivityForResult(new Intent(getActivity(), MyRoutePlanSearch.class),50);
        /**
         * 开始获取定位数据
         */
        map.setMyLocationEnabled(true);
        // 定位初始化
        mLocListener = new MyLocationListener();
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(mLocListener);
        LocationClientOption option = new LocationClientOption();
        //option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        option.setNeedDeviceDirect(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        return bmap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                startActivityForResult(i, MAIN_SEARCHROUTE);
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
        if (requestCode != MAIN_SEARCHROUTE) return;
        Log.i("TAG","act result 2");
        if (resultCode != MyRoutePlanSearch.SEARCH_WITHDISP) return;
        Log.i("TAG","act result 3");
        Map searchResult=(Map)RoutePlanMgr.get(getActivity()).getSearchResult();
        List routeLines=(List)searchResult.get("result");
        int type=(Integer)searchResult.get("type");
        route=(RouteLine)routeLines.get(0);
        if (route == null) return;
        Log.i("TAG","act result 4");
        if (route instanceof DrivingRouteLine) {
            Log.i("TAG","act result 5");
            DrivingRouteOverlay overlay = new DrivingRouteOverlay(map);
            routeOverlay = overlay;
            map.setOnMarkerClickListener(overlay);
            overlay.setData((DrivingRouteLine) route);
            overlay.addToMap();
            overlay.zoomToSpan();
        }


    }
}
