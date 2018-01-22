package in.fisicodietclinic.fisico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.fisicodietclinic.fisico.R;
import in.fisicodietclinic.fisico.adapters.GridAdapter;
import in.fisicodietclinic.fisico.models.Dates;

public class DateSelection extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridAdapter adapter;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private List<Dates> dateList;
    String response_string;
    SharedPreferences sharedpreferences;
    String username;
    String formatedMonth,formattedDay,day,formatedYear;

    ProgressDialog dialog;
   // String url = "https://medimedi.herokuapp.com/api/login/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);
        dialog=new ProgressDialog(DateSelection.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initCollapsingToolbar();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Select Date");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("user_name","manya" );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        sendR("http://arogyam.herokuapp.com/dates/");

        try {
            Glide.with(this).load(R.drawable.bgdates).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void sendR(String url_string)
    {

        dialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_string,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        dateList=new ArrayList<>();
                        try{
                            response_string = response;
                            JSONArray array=new JSONArray(response);
                            JSONObject object=null;
                            Log.d("yourtag",response);
                            for(int i=0;i<array.length();i++) {
                                object=array.getJSONObject(i);
                                String dates  = object.getString("date");

                                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
                                try {
                                    Date date = sdf.parse(dates);
                                    formatedMonth = new SimpleDateFormat("MM").format(date);

                                     formattedDay = new SimpleDateFormat("dd").format(date);

                                    formatedYear =new SimpleDateFormat("yy").format(date);

                                     day= new SimpleDateFormat("EEEE").format(date);
                                    //System.out.println(simpleDateformat.format(now));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Dates datesOBJECT = new Dates(formattedDay+"/"+formatedMonth+"/"+ formatedYear,day);
                                dateList.add(datesOBJECT);




                            }

                            //adapter = new GridAdapter(getApplicationContext(), dateList);
                        }catch (JSONException e){
                            Log.d("mytag",e.toString());
                        }
                        adapter = new GridAdapter(getApplicationContext(),dateList);
                        recyclerView.setAdapter(adapter);
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("usernam","abc");

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }



}