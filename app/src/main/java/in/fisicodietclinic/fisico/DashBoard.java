package in.fisicodietclinic.fisico;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Dashborad_fragment.OnFragmentInteractionListener,
        UpdateProgress.OnFragmentInteractionListener {

    Fragment fragment;
    private FragmentManager fragmentManager;
    TextView clientName,clientEmail;
    String username;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        fragmentManager = getSupportFragmentManager();
        fragment= new Dashborad_fragment();


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.dashboard:
                                fragment = new Dashborad_fragment();
                                break;
                            case R.id.diet:
                                fragment = new DietFragment();
                                break;
                            case R.id.update:
                                fragment = new UpdateProgress();
                                break;
                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.container,fragment).commit();
                        return true;
                    }
                });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(username.equalsIgnoreCase("Guest User"))) {
                    startActivity(new Intent(DashBoard.this,ChatActivity.class));
                }else{
//                    Toast.makeText(getApplicationContext(),"You are not enrolled yet!",Toast.LENGTH_SHORT).show();
//                    showDialog();
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "dtvidhi@fisicodietclinic.in" });
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Guest Query");
                    startActivity(intent);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        clientName = (TextView) header.findViewById(R.id.clientame);
        clientEmail = (TextView) header.findViewById(R.id.clientEmail);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        clientName.setText(sharedpreferences.getString("user_name","Unkonwn"));
        username = sharedpreferences.getString("user_name","Unkonwn");
        clientEmail.setText(sharedpreferences.getString("email","Unknown"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    public void showDialog(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashBoard.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            finish();
            startActivity(new Intent(DashBoard.this,SignIn.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_blog) {
            startActivity(new Intent(DashBoard.this,BlogActivity.class));
        } else if (id == R.id.nav_bmi) {
            startActivity(new Intent(DashBoard.this,BMIActivity.class));
        } else if (id == R.id.nav_uploadImage) {
            if(!(username.equalsIgnoreCase("Guest User"))) {
                startActivity(new Intent(DashBoard.this,UploadActivity.class));
            }else{
                Toast.makeText(getApplicationContext(),"You are not enrolled yet!",Toast.LENGTH_SHORT).show();
                showDialog();
            }

        } else if (id == R.id.nav_testimonials) {
            startActivity(new Intent(DashBoard.this,CustomerStories.class));
        } else if (id == R.id.nav_shareAndEarn) {
            startActivity(new Intent(DashBoard.this,ComingSoon.class));
        }
        else if(id == R.id.nav_facebook){
            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/DtVidhiChawla/"));
            startActivity(it);
        } else if(id == R.id.nav_instagram){
            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/dietitianvidhi/"));
            startActivity(it);
        }
        else if(id == R.id.nav_youtube){
            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCT9eLubRV6nolY-N_Z_sk-w"));
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
