package in.fisicodietclinic.fisico;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import in.fisicodietclinic.fisico.adapters.BlogAdapter;
import in.fisicodietclinic.fisico.models.Blog;


public class BlogActivity extends AppCompatActivity{

    private ArrayList<Blog> blogList;
    RecyclerView recyclerView;
    BlogAdapter blogAdapter;
    Blog blog;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_blogs);
        dialog=new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        getSupportActionBar().setTitle("Blog");

        sendR("https://www.googleapis.com/blogger/v3/blogs/6799741378082704735/posts?key=AIzaSyAB7OcaTZFCD0rFXcdLLZm6MqFWMrwN7ag");

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Blog b = blogList.get(position);
//                openChrome(b.getLink());
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
    }



    public void sendR(String url)
    {
        dialog.setMessage("Loading...");
        dialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        blogList=new ArrayList<>();
                        try {
                                JSONObject json = new JSONObject(response);
                                JSONArray array = json.getJSONArray("items");
                                for (int i=0;i<array.length();i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    String date= object.getString("published");
                                    String link = object.getString("url");
                                    String title = object.getString("title");
                                    if(title.equals("")){
                                        title+="BLOG POST BY VIDHI CHAWLA";
                                    }
                                blog = new Blog(title,date,link);
                                blogList.add(blog);
                                Log.d("lolol",title);
                            }
                            dialog.dismiss();
                            blogAdapter = new BlogAdapter(blogList,BlogActivity.this);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(BlogActivity.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(blogAdapter);
                            blogAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

//    public static void sendIntent(Intent i){
//       // startActivity(Intent.createChooser(i,"Share Link"));
//    }

    public  void openChrome(String url)
    {
        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch(ActivityNotFoundException e) {
            // Chrome is not installed
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }
}





