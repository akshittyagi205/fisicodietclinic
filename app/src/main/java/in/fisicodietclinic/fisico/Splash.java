package in.fisicodietclinic.fisico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cardinalsolutions.progressindicator.MainActivity;
import in.fisicodietclinic.fisico.utils.TypewriterEffect;

import in.fisicodietclinic.fisico.R;


public class Splash extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    SharedPreferences sharedpreferences;
    Animation animZoomIn;
    ImageView img;
    TypewriterEffect websiteName;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
        img = (ImageView) findViewById(R.id.imgLogo);
        websiteName= (TypewriterEffect) findViewById(R.id.website);

        img.startAnimation(animZoomIn);
        websiteName.setText("");
        websiteName.setCharacterDelay(150);
        websiteName.animateText("www.fisicodietclinic.in");
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final int user_id = sharedpreferences.getInt("user_id", 1995);
        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                // This method will be executed once the timer is over
                // /Start your app main activity
                if(user_id != 1995) {

                    Intent i = new Intent(Splash.this, DashBoard.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(Splash.this, SignIn.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
