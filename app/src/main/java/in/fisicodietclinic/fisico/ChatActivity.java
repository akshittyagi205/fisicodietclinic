package in.fisicodietclinic.fisico;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.FloatRange;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

import in.fisicodietclinic.fisico.adapters.ChatAdapter;
import in.fisicodietclinic.fisico.models.ChatModel;

import static in.fisicodietclinic.fisico.DashBoard.MyPREFERENCES;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChatModel chatModel;
    ArrayList<ChatModel> chatList;
    ChatAdapter chatAdapter;
    EditText message;
    String topic,queryTxt;
    FloatingActionButton fab;
    String username;
    String url = "http://fisicodietclinic.herokuapp.com/askquery/";
    String fetch_url="http://fisicodietclinic.herokuapp.com/showquery/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        getSupportActionBar().setTitle("Ask a query");
        //sendR("http://arogyam.rjchoreography.com/wp-json/wp/v2/posts?orderby=date&per_page=4");
        chatList=new ArrayList<>();
//        chatList.add(new ChatModel("Hello,Hello","Customer"));
//        chatList.add(new ChatModel("How are you","Customer"));
//        chatList.add(new ChatModel("I'm Doing good","Doctor"));
//        chatList.add(new ChatModel("How are you?","Doctor"));
        chatAdapter = new ChatAdapter(chatList);
        //chatAdapter.notifyDataSetChanged();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChatActivity.this);
       // mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(chatAdapter);

        chatAdapter.notifyDataSetChanged();
        message = (EditText) findViewById(R.id.editTextMessage);

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("user_name","Unknown" );

        getChats(fetch_url);

        fab = (FloatingActionButton) findViewById(R.id.sendMessage);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chatList.add(new ChatModel(message.getText().toString(),"Customer"));
//                chatAdapter.notifyDataSetChanged();
//                recyclerView.scrollToPosition(chatList.size()-1);
//                message.setText("");
//            }
//        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void showDialog(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatActivity.this);
        // Get the layout inflater
        LayoutInflater linf = LayoutInflater.from(getApplicationContext());
        final View inflator = linf.inflate(R.layout.ask_query_dialog, null);
        // Inflate and set the layout for the dialog

        alertDialog.setView(inflator);

        alertDialog.setPositiveButton("Ask Query", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText topic = (EditText) inflator.findViewById(R.id.input_topic);
                EditText message = (EditText) inflator.findViewById(R.id.input_query);
                if(!(TextUtils.isEmpty(topic.getText().toString().trim())&&TextUtils.isEmpty(message.getText().toString().trim()))){
                    queryTxt = message.getText().toString().trim();
                    ChatActivity.this.topic = topic.getText().toString().trim();
                    int res = sendPostQueryRequest(url);
                    if(res==1){
                        dialogInterface.dismiss();
                        getChats(fetch_url);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Empty fields not allowed!",Toast.LENGTH_SHORT);
                }
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();

    }

    public void getChats(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("mm",response);

                            JSONObject object =  new JSONObject(response);
                            JSONObject jsonObject=null;
                            JSONArray array = object.getJSONArray("objects");
                            chatList.clear();
                            for(int i=array.length()-1;i>=0;i--)
                            {
                                jsonObject= array.getJSONObject(i);
                                String title= jsonObject.getString("topic");
                                String query = jsonObject.getString("query");
                                String answer = jsonObject.getString("answer");
                                chatList.add(new ChatModel(query,"Customer"));
                                if(answer.equals("null")){
                                    chatList.add(new ChatModel("Answer awaited","Doctor"));
                                }
                                else{
                                    chatList.add(new ChatModel(answer,"Doctor"));
                                }
                                Log.d("mm",answer);

                            }
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(chatList.size()-1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("usernam",username);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    public int sendPostQueryRequest(String url){
        int res=1;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();

                        try{

                            if(response.equalsIgnoreCase("done"))
                            {
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"There was some error",Toast.LENGTH_LONG).show();
                            }





                            //adapter = new GridAdapter(getApplicationContext(), dateList);
                        }catch (Exception e){
                            Log.d("mytag",e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user",username);
                params.put("topic",topic);
                params.put("desc",queryTxt);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        return res;
    }
}
