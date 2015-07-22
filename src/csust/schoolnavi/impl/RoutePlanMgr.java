package csust.schoolnavi.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.*;
import csust.schoolnavi.interfaces.OnTaskDoneListener;
import csust.schoolnavi.interfaces.RoutePM_inter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 7YHong on 2015/7/6.
 */
public class RoutePlanMgr implements RoutePM_inter,OnGetRoutePlanResultListener {
    public static final int TYPE_DRIVING=1;
    public static final int TYPE_WALKING=2;
    public static final int TYPE_TRANSIT=3;

    private static RoutePlanMgr ourInstance = new RoutePlanMgr();
    private static Context c;
    RoutePlanSearch mSearch;
    Map<String,Object> searchResult;//List<XXRouteLine>对象向上转型为Object    同时包含路径规划的各种信息
    OnTaskDoneListener listener;

    private RoutePlanMgr() {
        mSearch=RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        searchResult =new HashMap<String, Object>();
    }

    public static RoutePlanMgr get(Context context){
        c=context;
        return ourInstance;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

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
            Toast.makeText(c, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            Toast.makeText(c, "请确保输入的地点没有歧义!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            //此处在Map中放了一个List<DrivingRouteLine>
            searchResult.put("type",TYPE_DRIVING);
            searchResult.put("result",result.getRouteLines());
            listener.onTaskDone(searchResult);
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
        this.listener=listener;
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
        //Log.i("RoutePlanManager","返回空值");
        //if(result==null) Toast.makeText(c,"返回空值",Toast.LENGTH_SHORT).show();

        //return ((DrivingRouteResult)result).getSearchResult();
    }

    @Override
    public List<TransitRouteLine> SearchTransit(PlanNode stNode, PlanNode enNode) {
        return null;
    }

    @Override
    public List<WalkingRouteLine> SearchWalking(PlanNode stNode, PlanNode enNode) {
        return null;
    }

    @Override
    public Map getSearchResult() {
        return searchResult;
    }

}
