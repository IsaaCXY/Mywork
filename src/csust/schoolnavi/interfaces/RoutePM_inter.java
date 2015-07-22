package csust.schoolnavi.interfaces;

import android.view.View;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;

import java.util.List;
import java.util.Map;

/**
 * Created by 7YHong on 2015/7/6.
 */
public interface RoutePM_inter {
    public void SearchDriving(PlanNode stNode, PlanNode enNode, OnTaskDoneListener listener);
    public List<TransitRouteLine> SearchTransit(PlanNode stNode,PlanNode enNode);
    public List<WalkingRouteLine> SearchWalking(PlanNode stNode,PlanNode enNode);
    public Map getSearchResult();
}
