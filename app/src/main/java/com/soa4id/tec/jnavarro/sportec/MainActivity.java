package com.soa4id.tec.jnavarro.sportec;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mindorks.placeholderview.PlaceHolderView;

import network.API;
import storage.DBHelper;

public class MainActivity extends AppCompatActivity {
    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private PlaceHolderView mGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Credentials);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        try{
            checkUserLogged();
        }catch (Exception e){
            Log.i("JSON", e.getMessage());
        }
    }


    /**
     *Checks if the user is logged
     */
    private void checkUserLogged () {
        DBHelper helper = new DBHelper(MainActivity.this);
        String result = helper.checkUserCredentials();

        if (!(result.equals(""))){
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
