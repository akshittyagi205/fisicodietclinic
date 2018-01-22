package in.fisicodietclinic.fisico;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.fisicodietclinic.fisico.adapters.diet_adapter;
import in.fisicodietclinic.fisico.models.Diet;

public class DietFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs" ;
    private RecyclerView recyclerView;
    private diet_adapter adapter;
    private List<Diet> dietsList = new ArrayList<>();
    ProgressDialog dialog;
    String response_string;
    String username;
    LinearLayout message;
    SharedPreferences sharedpreferences;

    public static DietFragment newInstance() {
        DietFragment fragment = new DietFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.diet_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        message = (LinearLayout) view.findViewById(R.id.message_layout_id);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//toolbar.setTitle("Today's Diet");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Today's Diet");
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("user_name","manya" );
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog=new ProgressDialog(getActivity());
        setHasOptionsMenu(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        String value = String.valueOf(year);
        String yy = value.substring(value.length() - 2);
        Log.d("date",yy+""+month+""+day+"");
        String date=day+""+month+""+yy;

        sendR("http://arogyam.herokuapp.com/dietdata/",date);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.all_diet_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.show) {

            Intent intent = new Intent(getActivity(),DateSelection.class);
            startActivity(intent);

            // do something here
        }
        return super.onOptionsItemSelected(item);
    }



    public void sendR(String url_string, final String date)
    {

        dialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_string,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        dietsList=new ArrayList<>();
                        try{
                            response_string = response;
                            JSONArray array=new JSONArray(response);
                            JSONObject object=null;
                            Log.d("yourtag",response);
                            for(int i=0;i<array.length();i++) {
                                object=array.getJSONObject(i);
                                String one  = object.getString("one");
                                String typeone = object.getString("typeone");
                                String timeone = object.getString("timeone");


                                    Diet pharmacy = new Diet(typeone,one,timeone);
                                    dietsList.add(pharmacy);


                                String two  = object.getString("two");
                                String typetwo = object.getString("typetwo");
                                String timetwo = object.getString("timetwo");
                                if(!two.isEmpty())
                                {
                                     pharmacy = new Diet(typetwo,two,timetwo);
                                    dietsList.add(pharmacy);
                                }

                                String three  = object.getString("three");
                                String typethree = object.getString("typethree");
                                String timethree = object.getString("timethree");
                                if(!three.isEmpty())
                                {
                                     pharmacy = new Diet(typethree,three,timethree);
                                    dietsList.add(pharmacy);
                                }

                                String four  = object.getString("four");
                                String typefour = object.getString("typefour");
                                String timefour = object.getString("timefour");
                                if(!four.isEmpty())
                                {
                                    pharmacy = new Diet(typefour,four,timefour);
                                    dietsList.add(pharmacy);
                                }

                                String five  = object.getString("five");
                                String typefive = object.getString("typefive");
                                String timefive = object.getString("timefive");
                                if(!five.isEmpty())
                                {
                                     pharmacy = new Diet(typefive,five,timefive);
                                    dietsList.add(pharmacy);
                                }

                                String six  = object.getString("six");
                                String typesix = object.getString("typesix");
                                String timesix = object.getString("timesix");
                                if(!six.isEmpty())
                                {
                                     pharmacy = new Diet(typesix,six,timesix);
                                    dietsList.add(pharmacy);
                                }

                                String seven  = object.getString("seven");
                                String typeseven = object.getString("typeseven");
                                String timeseven = object.getString("timeseven");
                                if(!seven.isEmpty())
                                {
                                     pharmacy = new Diet(typeseven,seven,timeseven);
                                    dietsList.add(pharmacy);
                                }

                                String eight  = object.getString("eight");
                                String typeeight = object.getString("typeeight");
                                String timeeight = object.getString("timeeight");
                                if(!eight.isEmpty())
                                {
                                     pharmacy = new Diet(typeeight,eight,timeeight);
                                    dietsList.add(pharmacy);
                                }



                            }

                        }catch (JSONException e){
                            Log.d("mytag",e.toString());
                        }
                        adapter = new diet_adapter(dietsList);
                        recyclerView.setAdapter(adapter);
                        if(dietsList.size()==0)
                        {
                            message.setVisibility(View.VISIBLE);
                        }
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("usernam","abc");
                params.put("dayo",date);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }




}