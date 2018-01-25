package in.fisicodietclinic.fisico;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.fisicodietclinic.fisico.adapters.BlogAdapter;
import in.fisicodietclinic.fisico.adapters.ClientStoryAdapter;
import in.fisicodietclinic.fisico.models.Blog;
import in.fisicodietclinic.fisico.models.Story;

public class CustomerStories extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyDpyQ3AZjUPrnW6PhXEANo9EHcic_Y3Jhw";
    public static final String CHANNEL_ID = "UCT9eLubRV6nolY-N_Z_sk-w";
    String VIDEO_ID="-yXBiDHuX_c",mTitle="FISICO DIET CLINIC INTRO VIDEO";
    YouTubePlayer mPlayer;
    TextView title;
    Toolbar toolbar;
    ArrayList<Story> stories;
    RecyclerView recyclerView;
    ClientStoryAdapter clientStoryAdapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_story);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        title = (TextView) findViewById(R.id.titleVideo);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        stories = new ArrayList<>();
        stories.add(new Story("TESTIMONIAL OF RAKHI SHARMA","https://i.ytimg.com/vi/VVQMkfVkxMY/default.jpg","VVQMkfVkxMY"));
        stories.add(new Story("TESTIMONIAL OF HIMANI TANVAR","https://i.ytimg.com/vi/MBfQMcST6ms/default.jpg","MBfQMcST6ms"));
        stories.add(new Story("TESTIMONIAL PPT OF VAISHNAVI","https://i.ytimg.com/vi/hWRwcpMRsLk/default.jpg","hWRwcpMRsLk"));
        stories.add(new Story("TESTIMONIAL VIDEO OF V SUDARSHAN","https://i.ytimg.com/vi/ReKc7YofkNw/default.jpg","ReKc7YofkNw"));
        stories.add(new Story("TESTIMONIAL OF HEENA","https://i.ytimg.com/vi/bnEcXq2ZxCo/default.jpg","bnEcXq2ZxCo"));
        stories.add(new Story("TESTIMONIAL OF SAUMYA SACHDEVA","https://i.ytimg.com/vi/eJW0gMJdNy0/default.jpg","eJW0gMJdNy0"));
        stories.add(new Story("TESTIMONIAL OF SONAL MAKKER","https://i.ytimg.com/vi/SxQAGFtqUQw/default.jpg","SxQAGFtqUQw"));
        //sendR("https://www.googleapis.com/youtube/v3/search?key="+API_KEY+"&channelId="+CHANNEL_ID+"&part=snippet,id&order=date&maxResults=20");
        clientStoryAdapter = new ClientStoryAdapter(stories,CustomerStories.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CustomerStories.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(clientStoryAdapter);
        clientStoryAdapter.notifyDataSetChanged();
        dialog.cancel();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Story s = stories.get(position);
                mPlayer.loadVideo(s.getVideoLink());
                title.setText(s.getTitle());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        /** Initializing YouTube Player View **/
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        mPlayer = player;
        /** Start buffering **/
        if (!wasRestored) {
            player.loadVideo(VIDEO_ID);
            title.setText(mTitle);
        }
    }

    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };

    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };


//
//    public void sendR(String url)
//    {
//        dialog.setMessage("Fetching Latest Stories...");
//        dialog.show();
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        stories=new ArrayList<>();
//                        try {
//                            JSONObject ob = new JSONObject(response);
//                            Log.d("Response",response);
//                            JSONArray items = ob.getJSONArray("items");
//                            for (int i=0;i<items.length();i++) {
//                                JSONObject object = items.getJSONObject(i);
//                                JSONObject details = object.getJSONObject("snippet");
//                                String videoID = object.getJSONObject("id").getString("videoId");
//                                String title = details.getString("title");
//                                Log.d("Title",title);
//                                String imageLink = details.getJSONObject("thumbnails").getJSONObject("default").getString("url");
//                                stories.add(new Story(title,imageLink,videoID));
//                                if(i==0){
//                                    VIDEO_ID=videoID;
//                                    mTitle = title;
//                                }
//                            }
//                            dialog.dismiss();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }
}
