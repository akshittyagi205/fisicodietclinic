package in.fisicodietclinic.fisico.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import in.fisicodietclinic.fisico.R;
import in.fisicodietclinic.fisico.TimeLineViewHolder;
import in.fisicodietclinic.fisico.models.Diet;


public class diet_adapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<Diet> dietList;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);

        }
    }


    public diet_adapter(List<Diet> dietList) {
        this.dietList = dietList;
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.diet_row, null);

        return new TimeLineViewHolder(view, viewType);
    }



    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }
    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        Diet diet = dietList.get(position);
        holder.title.setText(diet.getTitle());
        holder.genre.setText(diet.getDetails());
        holder.year.setText(diet.getYear());
    }

    @Override
    public int getItemCount() {
        return dietList.size();
    }
}