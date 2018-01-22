package in.fisicodietclinic.fisico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {
    EditText userName, password;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Button proceed;
    String url = "https://arogyam.herokuapp.com/loginapi/";
    String msg,user_var,pass_var;
    int res,user_id;

    String user_name;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        userName = (EditText) findViewById(R.id.userName_edit);
        userName.requestFocus();
        password = (EditText) findViewById(R.id.password_edit);
        proceed = (Button) findViewById(R.id.buttonProceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_var = userName.getText().toString();
                pass_var = password.getText().toString();
//This is bypassed for now
                //Change it with your api
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putInt("user_id", 123);
                editor.putString("user_name","abc");
                editor.commit();
                finish();
                startActivity(new Intent(SignIn.this,DashBoard.class));
                int result = sendR(user_var,pass_var);
                if(result == 1)
                {

                }
            }
        });


    }




    public int sendR(final String username, final String password)
    {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            msg = object.getString("msg");
                            res = object.getInt("res");
                            user_id=object.getInt("user_id");
                            user_name=object.getString("username");
                            Log.d("name",user_name);
                            if(res == 1)
                            {
                                Intent i = new Intent(getApplicationContext(),DashBoard.class);
                                startActivity(i);
                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putInt("user_id", user_id);
                                editor.putString("user_name",user_name);
                                editor.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(SignIn.this,response,Toast.LENGTH_LONG).show();
                        Toast.makeText(SignIn.this,msg+""+res+"",Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignIn.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",username);
                params.put("password",password);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return  res;
    }
}
