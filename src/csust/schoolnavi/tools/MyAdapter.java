package csust.schoolnavi.tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import csust.schoolnavi.R;

/**
 * Created by 7YHong on 2015/7/10.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    DrivingRouteLine routeLine;

    public MyAdapter(Object routeLine){
        this.routeLine=(DrivingRouteLine)routeLine;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txt1,txt2;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple2text,viewGroup,false);
        ViewHolder holder=new ViewHolder(v);
        holder.txt1=(TextView)v.findViewById(R.id.text1);
        holder.txt2=(TextView)v.findViewById(R.id.text2);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.txt1.setText(((DrivingRouteLine.DrivingStep) routeLine.getAllStep().get(i)).getEntrace().getLocation().toString());
        viewHolder.txt2.setText(((DrivingRouteLine.DrivingStep) routeLine.getAllStep().get(i)).getInstructions());
    }

    @Override
    public int getItemCount() {
        return routeLine.getAllStep().size();
    }
}
