package csust.schoolnavi.present;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import csust.schoolnavi.impl.RoutePlanMgr;
import csust.schoolnavi.interfaces.IMapFrag;

import java.util.List;

/**
 * Created by 7YHong on 2015/7/23.
 */
public class MapPresent {

    IMapFrag mapFrag;

    public MapPresent(IMapFrag mapFrag) {
        this.mapFrag = mapFrag;
    }

    public void dispCurrentOverlay() {
        mapFrag.clear();
        RouteLine routeLine = RoutePlanMgr.get().getSearchResult().get(0);
        int type = RoutePlanMgr.get().getType();
        switch (type) {
            case RoutePlanMgr.TYPE_DRIVING:
                mapFrag.addRoute((DrivingRouteLine) routeLine);
                break;
            case RoutePlanMgr.TYPE_WALKING:
                mapFrag.addRoute((WalkingRouteLine) routeLine);
                break;
            case RoutePlanMgr.TYPE_TRANSIT:
                mapFrag.addRoute((TransitRouteLine) routeLine);
                break;

        }
    }
}
