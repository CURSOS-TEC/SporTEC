package com.soa4id.tec.jnavarro.sportec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import credentials.CredentialsHelper;
import soaImage.ImageConverter;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView mUserName;
    private TextView mUserEmail;
    private ImageView mUserPhoto;
    private ImageView mUserPhotoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        String jsonString = getIntent().getStringExtra("user");
        Log.i("JSON Home",jsonString);
        JsonParser parser = new JsonParser();
        JsonObject objectUser = parser.parse(jsonString).getAsJsonObject();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        this.mUserEmail = headerView.findViewById(R.id.nav_header_user_email);
        this.mUserName = headerView.findViewById(R.id.nav_header_user_name);
        this.mUserPhoto = headerView.findViewById(R.id.imageView_user);
        this.mUserName.setText(objectUser.get("username").toString().replace("\"",""));
        this.mUserEmail.setText(objectUser.get("email").toString().replace("\"",""));

        ImageConverter converter  = new ImageConverter();

        try{
            Bitmap bitmap = converter.StringToBitMap(objectUser.get("image").toString()
                    .replace("\"","")
                    .replace("\\n",""));
            this.mUserPhoto.setImageBitmap(bitmap);

        }
        catch(Exception e){
            Log.i("JSON image", e.getMessage());
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            Toast.makeText(Home.this,"Login out", Toast.LENGTH_SHORT).show();
            CredentialsHelper helper = new CredentialsHelper();
            helper.setmContext(Home.this);
            helper.logout();
            try{
                Intent intent = new Intent(Home.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Home.this.startActivity(intent);
            }
            catch (Exception e){
                Log.i("Log Out", e.getMessage());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
