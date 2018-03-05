package in.fisicodietclinic.fisico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import static in.fisicodietclinic.fisico.Splash.MyPREFERENCES;

public class SignUp extends AppCompatActivity {

    EditText name,phone,age,email;
    RadioButton male,female;
    Button signUp;
    String nameTxt,phoneTxt,ageTxt,emailTxt,genderTxt;
    ProgressDialog dialog;
    String url = "https://fisicodietclinic.herokuapp.com/email/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("SignUp");
        name = (EditText) findViewById(R.id.name_edit);
        phone = (EditText) findViewById(R.id.phone_edit);
        age = (EditText) findViewById(R.id.age_edit);
        email = (EditText) findViewById(R.id.email_edit);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        signUp = (Button) findViewById(R.id.signupBtn);
        dialog = new ProgressDialog(this);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameTxt = name.getText().toString().trim();
                phoneTxt = phone.getText().toString().trim();
                ageTxt = age.getText().toString().trim();
                emailTxt = email.getText().toString().trim();
                if(male.isChecked()) {
                    genderTxt = "male";
                }
                else{
                    genderTxt = "female";
                }
                sendR();
            }
        });

    }


    public void sendR()
    {
        dialog.setMessage("Loading...");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                                Intent i = new Intent(SignUp.this,DashBoard.class);
                                startActivity(i);
                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putInt("user_id", 1996);
                                editor.putString("user_name","Guest User");
                                editor.putString("email",emailTxt);
                                editor.commit();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();
                        Toast.makeText(SignUp.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",emailTxt);
                params.put("phno",phoneTxt);
                params.put("name",nameTxt);
                params.put("age",ageTxt);
                params.put("gender",genderTxt);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}