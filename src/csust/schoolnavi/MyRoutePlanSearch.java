package csust.schoolnavi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.*;
import csust.schoolnavi.impl.RoutePlanMgr;
import csust.schoolnavi.interfaces.OnTaskDoneListener;
import csust.schoolnavi.tools.MyAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by 7YHong on 2015/7/6.
 * 路径规划Activity
 * 要求业务实现类返回List<XXRouteLine>对象
 */
public class MyRoutePlanSearch extends Activity implements OnTaskDoneListener {
    /**
     * 静态变量的编号根据View出现的顺序进行
     */
    public static final int SEARCH_WITHNODISP = 20;
    public static final int SEARCH_WITHDISP = 21;

    PlanNode stNode, enNode;

    EditText start, end;
    List<RouteLine> routeLines;//内含XXRouteLine类元素
    RecyclerView rv;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = getApplicationContext();
        SDKInitializer.initialize(c);
        setContentView(R.layout.activity_routeplan);
        start = (EditText) findViewById(R.id.start);
        end = (EditText) findViewById(R.id.end);
        //lv=(ListView)findViewById(R.id.itemlist);
        rv = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(c);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

    }

    public void SearchBtnProgress(View v) {
        Log.i("TAG", "Button Clicked!");
        routeLines = null;
        stNode = PlanNode.withCityNameAndPlaceName("北京", start.getText().toString());
        enNode = PlanNode.withCityNameAndPlaceName("北京", end.getText().toString());
        switch (v.getId()) {
            case R.id.drive:
                RoutePlanMgr.get().SearchDriving(stNode, enNode, this);
                break;
            case R.id.walk:
                RoutePlanMgr.get().SearchWalking(stNode, enNode, this);
                break;
            case R.id.transit:
                RoutePlanMgr.get().SearchTransit(stNode, enNode, this);
                break;
            default:
                Toast.makeText(c, "按钮现在还不生效", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void backToMap(View v) {
        switch (v.getId()) {
            case R.id.backtomap: {
                if (routeLines == null) finish();
                setResult(SEARCH_WITHDISP);
                finish();
            }
            break;

            default:
                Log.i("BtnError", "UnCorrectBtnID");
                break;
        }
    }


    @Override
    public void onTaskDone(Map result) {
        routeLines = RoutePlanMgr.get().getSearchResult();
        Log.i("OnTaskDone_SearchBack", String.valueOf(routeLines.size()));
        rv.setAdapter(new MyAdapter(routeLines.get(0)));
    }


    /*private class MyAdapter extends BaseAdapter {
        DrivingRouteLine routeLine;
        MyAdapter(Object routeLine){
            //此处直接把提取出来的结果转换成了DrivingRouteLine类型，因为懒
            this.routeLine=(DrivingRouteLine)routeLine;
        }

        @Override
        public int getCount() {
            return routeLine.getAllStep().size();
        }

        @Override
        public Object getItem(int position) {
            return routeLine.getAllStep().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater mInflater=LayoutInflater.from(parent.getContext());
                convertView = mInflater.inflate(R.layout.simple2text, parent,false);

            }
            TextView tv1=(TextView)convertView.findViewById(R.id.text1);
            TextView tv2=(TextView)convertView.findViewById(R.id.text2);
            String text1=null,text2=null;
            Object step=getItem(position);
            if (step instanceof DrivingRouteLine.DrivingStep) {
                text2 = ((DrivingRouteLine.DrivingStep) step).getEntrace().getLocation().toString();
                text1 = ((DrivingRouteLine.DrivingStep) step).getInstructions();
            } else if (step instanceof WalkingRouteLine.WalkingStep) {
                text2 = ((WalkingRouteLine.WalkingStep) step).getEntrace().getLocation().toString();
                text1 = ((WalkingRouteLine.WalkingStep) step).getInstructions();
            } else if (step instanceof TransitRouteLine.TransitStep) {
                text2 = ((TransitRouteLine.TransitStep) step).getEntrace().getLocation().toString();
                text1 = ((TransitRouteLine.TransitStep) step).getInstructions();
            }
            tv1.setText(text1);
            tv2.setText(text2);
            return convertView;
        }
    }*/

}
