package in.fisicodietclinic.fisico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.fisicodietclinic.fisico.adapters.diet_adapter;
import in.fisicodietclinic.fisico.models.Diet;

public class DietActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private diet_adapter adapter;
    private List<Diet> dietsList = new ArrayList<>();
    ProgressDialog dialog;
    String username;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String response_string;
    String date_value,formatedMonth,formattedDay,formatedYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_fragment);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        dialog=new ProgressDialog(DietActivity.this);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("user_name","manya" );
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date_value = extras.getString("date");
        }


        Date date = null;
        try {
            date = sdf.parse(date_value);
            formatedMonth = new SimpleDateFormat("MM").format(date);

            formattedDay = new SimpleDateFormat("dd").format(date);

            formatedYear =new SimpleDateFormat("yy").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        String value = String.valueOf(formatedYear);
        String yy = value.substring(value.length() - 2);

        String date_to_be_passed = formattedDay+formatedMonth+formatedYear;
        Log.d("mtag",date_to_be_passed);

        sendR("http://arogyam.herokuapp.com/dietdata/",date_to_be_passed);
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
                            Log.d("mtag",response);
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
                params.put("dayo",date);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
