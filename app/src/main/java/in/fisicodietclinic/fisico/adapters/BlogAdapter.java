package in.fisicodietclinic.fisico.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.fisicodietclinic.fisico.R;
import in.fisicodietclinic.fisico.models.Blog;


public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> {

    private ArrayList<Blog> blogList;


    public BlogAdapter(ArrayList<Blog> blogList) {
        this.blogList = blogList;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView text_date;
        public TextView text_title;
        public MyViewHolder(View view) {
            super(view);
            text_date = (TextView) view.findViewById(R.id.text_date);
            text_title= (TextView) view.findViewById(R.id.text_title);
        }
    }


    @Override
    public BlogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_list_row, parent, false);

        return new BlogAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BlogAdapter.MyViewHolder holder, int position) {


        Blog blog = blogList.get(position);
        Log.d("My tag",blog.getDate());
        holder.text_date.setText(blog.getDate());
        holder.text_title.setText(blog.getTitle());

    }

    @Override
    public int getItemCount()
    {
        return blogList.size();
    }



}