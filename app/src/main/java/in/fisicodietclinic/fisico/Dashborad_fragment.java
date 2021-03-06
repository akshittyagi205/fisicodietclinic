package in.fisicodietclinic.fisico;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.fisicodietclinic.fisico.models.Blog;

import static in.fisicodietclinic.fisico.DashBoard.MyPREFERENCES;


public class Dashborad_fragment extends Fragment implements View.OnClickListener{

    ProgressBar progressBar1,progressBar2;
    TextView weightLost,noOfDays,share1,share2,share3,share4;
    GraphView graph;
    private List<Blog> blogList;
    Blog blog;
    ProgressDialog dialog;
    String username;
    CardView cardOne,cardTwo,cardThree,cardFour;
    TextView dateOne,dateTwo,dateThree,dateFour,titleOne,titleTwo,titleThree,titleFour;
    SharedPreferences sharedpreferences;


    private OnFragmentInteractionListener mListener;

    public Dashborad_fragment() {
        // Required empty public constructor
    }


    public static Dashborad_fragment newInstance(String param1, String param2) {
        Dashborad_fragment fragment = new Dashborad_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashborad_fragment, container, false);
        graph = (GraphView) rootView.findViewById(R.id.graph);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("DashBoard");
//

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("user_name","Guest User");
        Log.d("user name",username);
        progressBar1 = (ProgressBar) rootView.findViewById(R.id.circle_progress_bar);
        progressBar2 = (ProgressBar) rootView.findViewById(R.id.circle_progress_bar1);
        weightLost = (TextView) rootView.findViewById(R.id.weightLost);
        noOfDays = (TextView) rootView.findViewById(R.id.noOfDays);
        share1 = (TextView) rootView.findViewById(R.id.shareBlog1);
        share1.setOnClickListener(this);
        share2 = (TextView) rootView.findViewById(R.id.shareBlog2);
        share2.setOnClickListener(this);
        share3 = (TextView) rootView.findViewById(R.id.shareBlog3);
        share3.setOnClickListener(this);
        share4 = (TextView) rootView.findViewById(R.id.shareBlog4);
        share4.setOnClickListener(this);
        //Replace 65 with no of days passed
        int count1=65;
        noOfDays.setText(String.valueOf(count1));
        progressBar1.setProgress(count1);

        //Replace 50 with the percentage of weight lost
        int count2=50;
        progressBar2.setProgress(count2);

        //Replace i with weight lost (kg)
        int i=8;
        weightLost.setText(String.valueOf(i));



        dialog=new ProgressDialog(getActivity());

        cardOne = (CardView) rootView.findViewById(R.id.card_one);
        cardTwo = (CardView) rootView.findViewById(R.id.card_two);
        cardThree = (CardView) rootView.findViewById(R.id.card_three);
        cardFour = (CardView) rootView.findViewById(R.id.card_four);
        dateOne = (TextView) rootView.findViewById(R.id.text_date_one);
        dateTwo = (TextView) rootView.findViewById(R.id.text_date_two);
        dateThree = (TextView) rootView.findViewById(R.id.text_date_three);
        dateFour = (TextView) rootView.findViewById(R.id.text_date_four);
        titleOne = (TextView) rootView.findViewById(R.id.text_title_one);
        titleTwo = (TextView) rootView.findViewById(R.id.text_title_two);
        titleThree = (TextView) rootView.findViewById(R.id.text_title_three);
        titleFour = (TextView) rootView.findViewById(R.id.text_title_four);

        if(!(username.equalsIgnoreCase("Guest user"))) {
            int result = fetchDashboardData("http://fisicodietclinic.herokuapp.com/dashboard/");
            if (result == 1) {
                sendR("https://www.googleapis.com/blogger/v3/blogs/6799741378082704735/posts?key=AIzaSyAB7OcaTZFCD0rFXcdLLZm6MqFWMrwN7ag");
            }
        }
        else{
            dialog.setMessage("Loading");
            dialog.show();
            //Replace 65 with no of days passed
            noOfDays.setText(String.valueOf(0));
            progressBar1.setProgress(0);
            //Replace 50 with the percentage of weight lost
            progressBar2.setProgress(0);
            //Replace i with weight lost (kg)
            weightLost.setText(String.valueOf(0));
            sendR("https://www.googleapis.com/blogger/v3/blogs/6799741378082704735/posts?key=AIzaSyAB7OcaTZFCD0rFXcdLLZm6MqFWMrwN7ag");
            PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[]{
                    new DataPoint(0,0)
            });
            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(0,0)
            });
            graph.getViewport().setScrollable(true); // enables horizontal scrolling
            series1.setColor(getResources().getColor(R.color.colorPrimary));
            graph.addSeries(series1);
            graph.addSeries(series);
            series.setShape(PointsGraphSeries.Shape.POINT);
            graph.getViewport().setScrollable(true); // enables horizontal scrolling
            series.setSize(8.0f);
            series.setColor(getResources().getColor(R.color.colorAccent));
            showDialog();
        }

        cardOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChrome(blogList.get(0).getLink());
            }
        });

        cardFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChrome(blogList.get(3).getLink());
            }
        });

        cardTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChrome(blogList.get(1).getLink());
            }
        });

        cardThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChrome(blogList.get(2).getLink());
            }
        });

        return rootView;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public void showDialog(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        alertDialog.setTitle("Enroll with Fisico")
                .setMessage("Do you want to enroll now?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent it = new Intent("android.intent.action.MAIN");
                            it.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            it.addCategory("android.intent.category.LAUNCHER");
                            it.setData(Uri.parse("http://fisicodietclinic.in/"));
                            startActivity(it);
                        }
                        catch(ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://fisicodietclinic.in/"));
                            startActivity(it);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setIcon(R.mipmap.ic_logo_fisico)
                .show();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        if(view == share1) {
            share.putExtra(Intent.EXTRA_TEXT,blogList.get(0).getLink());
        } else if(view==share2){
            share.putExtra(Intent.EXTRA_TEXT,blogList.get(1).getLink());
        }else if(view == share3){
            share.putExtra(Intent.EXTRA_TEXT,blogList.get(2).getLink());
        }else if(view == share4){
            share.putExtra(Intent.EXTRA_TEXT,blogList.get(3).getLink());
        }
        startActivity(Intent.createChooser(share,"Share Link"));

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



    public int fetchDashboardData(String url)
    {
       dialog.setMessage("Loading");
        dialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        DataPoint[] values;
                        try {
                            int point = 0, count;
                            JSONArray array = new JSONArray(response);
                            Log.d("size", array.length() + "");
                            count = array.length() * 4;
                            Log.d("count1",count+"");
                            for (int i = array.length() - 1; i >= 0; i--) {
                                JSONObject object = array.getJSONObject(i);
                                Log.d("value",object.getString("weektwo"));
                                if (object.getString("weekone").equals("null")) {
                                    count--;
                                    Log.d("count2",count+""+ object.getString("weekone"));

                                }
                                if (object.getString("weektwo").equals("null")) {
                                    count--;
                                    Log.d("count2two",count+""+ object.getString("weektwo"));
                                }
                                if (object.getString("weekthree").equals("null")) {
                                    count--;
                                }
                                if (object.getString("weekfour").equals("null")) {
                                    count--;
                                }
                            }
                            Log.d("count3",count+"");

                            int index=0;
                            values = new DataPoint[count];
                            for (int i=array.length()-1;i>=0;i--) {
                                JSONObject object = array.getJSONObject(i);
                                if(!(object.getString("weekone").equals("null"))){
                                    DataPoint v = new DataPoint(point++,Integer.parseInt(object.getString("weekone")));
                                    values[index++]=v;
                                    Log.d("Point : ",point+Integer.parseInt(object.getString("weekone"))+"");
                                }
                                if(!(object.getString("weektwo").equals("null"))){
                                    DataPoint v = new DataPoint(point++,Integer.parseInt(object.getString("weektwo")));
                                    values[index++]=v;
                                    Log.d("Point : ",point+Integer.parseInt(object.getString("weekone"))+"");
                                }
                                if(!(object.getString("weekthree").equals("null"))){
                                    DataPoint v = new DataPoint(point++,Integer.parseInt(object.getString("weekthree")));
                                    values[index++]=v;
                                    Log.d("Point : ",point+Integer.parseInt(object.getString("weekone"))+"");
                                }
                                if(!(object.getString("weekfour").equals("null"))){
                                    DataPoint v = new DataPoint(point++,Integer.parseInt(object.getString("weekfour")));
                                    values[index++]=v;
                                    Log.d("Point : ",point+Integer.parseInt(object.getString("weekone"))+"");
                                }
                            }

                             graph.getViewport().setXAxisBoundsManual(true);

                             graph.getViewport().setMinX(0);
                             graph.getViewport().setMaxX(20);

                            PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(values);
                            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(values);
                            graph.getViewport().setScrollable(true); // enables horizontal scrolling
                            series1.setColor(getResources().getColor(R.color.colorPrimary));
                            graph.addSeries(series1);
                            graph.addSeries(series);
                            series.setShape(PointsGraphSeries.Shape.POINT);
                            graph.getViewport().setScrollable(true); // enables horizontal scrolling
                            series.setSize(8.0f);
                            series.setColor(getResources().getColor(R.color.colorAccent));
//                            } catch (JSONException e1) {
//                            e1.printStackTrace();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                params.put("usernam",username);


                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        return 1;
    }


    public void sendR(String url)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        blogList=new ArrayList<>();
                        try {

                                Log.d("Response",response);
                            JSONObject json = new JSONObject(response);
                            JSONArray array = json.getJSONArray("items");
                            for (int i=0;i<4;i++) {
                                JSONObject object = array.getJSONObject(i);
                                String date= object.getString("published");
                                String link = object.getString("url");
//                                JSONObject title1 = object.getJSONObject("title");
                                String title = object.getString("title");
                                if(title.equals("")){
                                    title+="BLOG POST BY VIDHI CHAWLA";
                                }
                                blog = new Blog(title,date,link);
                                blogList.add(blog);
                                Log.d("lolol",title);


                            }
                            dialog.dismiss();
                            dateOne.setText(blogList.get(0).getDate());
                            dateTwo.setText(blogList.get(1).getDate());
                            dateThree.setText(blogList.get(2).getDate());
                            dateFour.setText(blogList.get(3).getDate());
                            titleOne.setText(blogList.get(0).getTitle());
                            titleTwo.setText(blogList.get(1).getTitle());
                            titleThree.setText(blogList.get(2).getTitle());
                            titleFour.setText(blogList.get(3).getTitle());




                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){


        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

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
