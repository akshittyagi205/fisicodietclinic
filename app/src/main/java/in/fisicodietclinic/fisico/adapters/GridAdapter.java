package in.fisicodietclinic.fisico.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;




import java.util.List;

import in.fisicodietclinic.fisico.DietActivity;
import in.fisicodietclinic.fisico.R;
import in.fisicodietclinic.fisico.models.Dates;

/**
 * Created by manyamadan on 25/11/17.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {

    private Context mContext;
    private List<Dates> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);

        }
    }


    public GridAdapter(Context mContext, List<Dates> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Dates date = albumList.get(position);
        holder.title.setText(date.getDate());
        holder.count.setText(date.getDay());


        // loading album cover using Glide library

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DietActivity.class);
                //intent.putExtra("date",albumList.get(position).getDate() );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("date",albumList.get(position).getDate());
                mContext.startActivity(intent);
            }
        });

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */


    /**
     * Click listener for popup menu items
     */
   
    @Override
    public int getItemCount() {
        return albumList.size();
    }
}