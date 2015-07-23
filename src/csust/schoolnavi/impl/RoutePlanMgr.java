package csust.schoolnavi.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.*;
import csust.schoolnavi.interfaces.OnTaskDoneListener;

import java.util.List;

/**
 * Created by 7YHong on 2015/7/6.
 * 真正的搜索请求是由它发出的
 * 搜索得到的结果也暂存在这里
 * 结果的保存格式为保存它的List<XXRouteLine>
 */
public class RoutePlanMgr implements csust.schoolnavi.interfaces.IRoutePlanMgr,OnGetRoutePlanResultListener {
    public static final int TYPE_DRIVING=1;
    public static final int TYPE_WALKING=2;
    public static final int TYPE_TRANSIT=3;

    private static RoutePlanMgr ourInstance = new RoutePlanMgr();

    List routelines;
    int type;

    RoutePlanSearch mSearch;
    //Map<String,Object> searchResult;//List<XXRouteLine>对象向上转型为Object    同时包含路径规划的各种信息
    OnTaskDoneListener listener;

    private RoutePlanMgr() {
        mSearch=RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }

    public static RoutePlanMgr get(){
        return ourInstance;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //Toast.makeText(c, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            routelines=result.getRouteLines();
            type=TYPE_WALKING;
            listener.onTaskDone(null);
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //Toast.makeText(c, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            routelines=result.getRouteLines();
            type=TYPE_TRANSIT;
            listener.onTaskDone(null);
        }
    }

    /**
     *
     * @param result List<DrivingRouteLine>
     * 如果搜索结果有错误，则在页面进行提示而不进行回调
     * 只有当结果正确，才将结果放入并进行回调
     */
    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //Toast.makeText(c, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            routelines=result.getRouteLines();
            type=TYPE_DRIVING;
            listener.onTaskDone(null);
        }
        Log.i("RoutePlanManager","GetDrivingRouteResult ed!");
    }


    /**
     *
     * @param stNode
     * @param enNode
     * @param listener 回调接口将返回List<XXRouteLine>类型的Object
     *
     */
    @Override
    public void SearchDriving(PlanNode stNode, PlanNode enNode, OnTaskDoneListener listener) {
        routelines=null;    //搜索之前先清空一下
        this.listener=listener;
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    @Override
    public void SearchWalking(PlanNode stNode, PlanNode enNode, OnTaskDoneListener listener) {
        routelines=null;    //搜索之前先清空一下
        this.listener=listener;
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    @Override
    public void SearchTransit(PlanNode stNode, PlanNode enNode, OnTaskDoneListener listener) {
        routelines=null;    //搜索之前先清空一下
        this.listener=listener;
        mSearch.transitSearch((new TransitRoutePlanOption())
                .from(stNode)
                .city("北京")
                .to(enNode));
    }


    @Override
    public List<RouteLine> getSearchResult() {
        return routelines;
    }

    @Override
    public int getType() {
        return type;
    }

}
