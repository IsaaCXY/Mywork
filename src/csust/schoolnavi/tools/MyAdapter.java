package csust.schoolnavi.tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import csust.schoolnavi.R;
import csust.schoolnavi.impl.RoutePlanMgr;

/**
 * Created by 7YHong on 2015/7/10.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    RouteLine routeLine;

    public MyAdapter(Object routeLine) {
        this.routeLine = (RouteLine) routeLine;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple2text, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        holder.txt1 = (TextView) v.findViewById(R.id.text1);
        holder.txt2 = (TextView) v.findViewById(R.id.text2);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        switch (RoutePlanMgr.get().getType()) {
            case RoutePlanMgr.TYPE_DRIVING: {
                viewHolder.txt1.setText(((DrivingRouteLine.DrivingStep) routeLine.getAllStep().get(i)).getEntrace().getLocation().toString());
                viewHolder.txt2.setText(((DrivingRouteLine.DrivingStep) routeLine.getAllStep().get(i)).getInstructions());
            }
            break;
            case RoutePlanMgr.TYPE_WALKING: {
                viewHolder.txt1.setText(((WalkingRouteLine.WalkingStep) routeLine.getAllStep().get(i)).getEntrace().getLocation().toString());
                viewHolder.txt2.setText(((WalkingRouteLine.WalkingStep) routeLine.getAllStep().get(i)).getInstructions());
            }
            break;
            case RoutePlanMgr.TYPE_TRANSIT: {
                viewHolder.txt1.setText(((TransitRouteLine.TransitStep) routeLine.getAllStep().get(i)).getEntrace().getLocation().toString());
                viewHolder.txt2.setText(((TransitRouteLine.TransitStep) routeLine.getAllStep().get(i)).getInstructions());
            }
            break;
        }

    }

    @Override
    public int getItemCount() {
        return routeLine.getAllStep().size();
    }
}
