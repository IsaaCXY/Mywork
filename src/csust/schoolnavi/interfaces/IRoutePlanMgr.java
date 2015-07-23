package csust.schoolnavi.interfaces;

import android.view.View;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;

import java.util.List;
import java.util.Map;

/**
 * Created by 7YHong on 2015/7/6.
 */
public interface IRoutePlanMgr {
    void SearchDriving(PlanNode stNode, PlanNode enNode, OnTaskDoneListener listener);

    void SearchWalking(PlanNode stNode, PlanNode enNode, OnTaskDoneListener listener);

    void SearchTransit(PlanNode stNode, PlanNode enNode, OnTaskDoneListener listener);

    List<RouteLine> getSearchResult();

    int getType();
}
