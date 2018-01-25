package in.fisicodietclinic.fisico.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import in.fisicodietclinic.fisico.R;
import in.fisicodietclinic.fisico.models.Blog;
import in.fisicodietclinic.fisico.models.Story;


public class ClientStoryAdapter extends RecyclerView.Adapter<ClientStoryAdapter.MyViewHolder> {

    private ArrayList<Story> stories;
    private Context mCtx;


    public ClientStoryAdapter(ArrayList<Story> stories,Context ctx) {
        this.stories = stories;
        this.mCtx=ctx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView title;
        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            title= (TextView) view.findViewById(R.id.title);
        }
    }


    @Override
    public ClientStoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_row, parent, false);

        return new ClientStoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClientStoryAdapter.MyViewHolder holder, int position) {


        Story story = stories.get(position);
        Log.d("My tag",story.getTitle());
            String url = story.getImgLink();
            Picasso.with(mCtx).load(url).into(holder.img);

        holder.title.setText(story.getTitle());
    }

    @Override
    public int getItemCount()
    {
        return stories.size();
    }



}