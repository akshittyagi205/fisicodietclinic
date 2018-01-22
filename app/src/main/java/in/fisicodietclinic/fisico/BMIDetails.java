package in.fisicodietclinic.fisico;

import android.icu.math.BigDecimal;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class BMIDetails extends AppCompatActivity
{

TextView bmiDetail,bmiCount;
ImageView underweight,normal,overweight,obese,severely_obese;
    String height,weight;
    Float bmi_count;
    int STATUS=0;
    String detail;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_detail );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            weight = extras.getString("weight");
            height = extras.getString("height");

            Log.d("lol",weight);
            // and get whatever type user account id is
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Height and Weight contains null values",Toast.LENGTH_LONG).show();
        }
        underweight = (ImageView) findViewById(R.id.image_underweight);
        normal = (ImageView) findViewById(R.id.image_normal);
        overweight = (ImageView) findViewById(R.id.image_overweight);
        obese = (ImageView) findViewById(R.id.image_obese);
        severely_obese = (ImageView) findViewById(R.id.image_severely);
        bmiDetail = (TextView) findViewById(R.id.bmi_detail);
        bmiCount = (TextView) findViewById(R.id.bmi_count);

        bmi_count = Float.parseFloat(weight)/(Float.parseFloat(height)* Float.parseFloat(height));
       try {
           Float count = round(bmi_count,2);
           bmiCount.setText(bmi_count+"");

       }
       catch(Exception e)
       {
           bmiCount.setText(bmi_count+"");

       }



            setSTATUS();



    }

    public  void  setSTATUS()
    {
        if(bmi_count < 18.5)
        {
            STATUS = 1;
            detail = " UNDERWEIGHT !! A BMI in this range is considered underweight, which " +
                    "means you could be at risk for chronic health issues. A low percentage of body " +
                    "fat can prevent your body from absorbing essential vitamins and minerals.";

            bmiDetail.setText(detail);
            underweight.setVisibility(View.VISIBLE);
        }
        else if( bmi_count >= 18.5 && bmi_count <= 24.99)
        {
            detail = " NORMAL !! A BMI in this range is considered a healthy weight, which " +
                    "means your risk of weight-related " +
                    "conditions like type 2 diabetes, heart disease and high blood pressure is generally lower.";

            bmiDetail.setText(detail);
            normal.setVisibility(View.VISIBLE);
        }
        else if(bmi_count >=25 && bmi_count<=29.99 )
        {
             detail = "OVERWEIGHT !!  A BMI in this range is considered overweight, which means " +
                    "your risk for health conditions, such as type 2 diabetes, heart disease" +
                    " and high blood pressure, increases. A waist circumference measurement can give an additional" +
                    " assessment of body composition because excess belly fat is another predictor for disease risk.";

            bmiDetail.setText(detail);
            overweight.setVisibility(View.VISIBLE);
        }
        else if(bmi_count >=30 && bmi_count <=35 )
        {
            detail = " OBESE!!  A BMI in this range is considered obese, which puts you at the highest " +
                    "risk for diseases and health conditions such as heart disease, stroke, high blood pressure " +
                    "and some cancers. Obesity also increases the risk of developing type 2 diabetes and high " +
                    "cholesterol.";

            bmiDetail.setText(detail);
            obese.setVisibility(View.VISIBLE);
        }

        else
        {
            detail = " SEVERELY OBESE!!  A BMI in this range is considered obese, which puts you at the highest " +
                    "risk for diseases and health conditions such as heart disease, stroke, high blood pressure " +
                    "and some cancers. Obesity also increases the risk of developing type 2 diabetes and high " +
                    "cholesterol.";

            bmiDetail.setText(detail);
            severely_obese.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}