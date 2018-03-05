package in.fisicodietclinic.fisico.adapters;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.fisicodietclinic.fisico.BlogActivity;
import in.fisicodietclinic.fisico.R;
import in.fisicodietclinic.fisico.models.Blog;


public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> {

    private ArrayList<Blog> blogList;
    private Context mCtx;


    public BlogAdapter(ArrayList<Blog> blogList, Context mCtx) {
        this.blogList = blogList;
        this.mCtx = mCtx;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView text_date;
        public TextView text_title;
        public TextView shareBlog;
        public ImageView img;
        public LinearLayout read_more;
        public MyViewHolder(View view) {
            super(view);
            text_date = (TextView) view.findViewById(R.id.text_date);
            text_title= (TextView) view.findViewById(R.id.text_title);
            shareBlog = (TextView) view.findViewById(R.id.shareBlog);
            img = (ImageView) view.findViewById(R.id.img);
            read_more = (LinearLayout) view.findViewById(R.id.read_more);
        }
    }


    @Override
    public BlogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_list_row, parent, false);

        return new BlogAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BlogAdapter.MyViewHolder holder, final int position) {


        Blog blog = blogList.get(position);
        Log.d("My tag",blog.getDate());
        holder.text_date.setText(blog.getDate());
        holder.text_title.setText(blog.getTitle());
        holder.shareBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,blogList.get(position).getLink());
                mCtx.startActivity(Intent.createChooser(share,"Share Link"));
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Blog b = blogList.get(position);
                openChrome(b.getLink());
            }
        });

        holder.read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Blog b = blogList.get(position);
                openChrome(b.getLink());
            }
        });
    }

    public  void openChrome(String url)
    {
        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(url));
            mCtx.startActivity(i);
        }
        catch(ActivityNotFoundException e) {
            // Chrome is not installed
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mCtx.startActivity(i);
        }
    }

    @Override
    public int getItemCount()
    {
        return blogList.size();
    }



}