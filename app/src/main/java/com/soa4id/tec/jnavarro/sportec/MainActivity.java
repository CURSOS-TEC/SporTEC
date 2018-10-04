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

        mDrawer = findViewById(R.id.drawerLayout);
        mDrawerView = findViewById(R.id.drawerView);
        mToolbar = findViewById(R.id.toolbar);
        mGalleryView = findViewById(R.id.galleryView);
        setupDrawer();

        try{
            checkUserLogged();
        }catch (Exception e){
            Log.i("JSON", e.getMessage());
        }




    }

    private void setupDrawer(){
        mDrawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_REQUESTS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_GROUPS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_TERMS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                                                                              mDrawer,
                                                                              mToolbar,
                                                                              R.string.open_drawer,
                                                                              R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    /**
     *Checks if the user is logged
     */
    private void checkUserLogged () {
        DBHelper helper = new DBHelper(MainActivity.this);
        String result = helper.checkUserCredentials();

        if (!(result.equals(""))){
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            /*try{
                GetUserAsyncTask task = new GetUserAsyncTask(MainActivity.this,access_token,userId);
                task.execute("");
            }catch (Exception ee){
                Log.i("JSON ERROR USER",ee.getMessage());
            }*/
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private class GetUserAsyncTask extends AsyncTask<String,Void, Boolean> {
        private Context mContext;
        private String mToken;
        private ProgressDialog mProgress;
        private String mUserId;
        private JsonObject mJson;

        public GetUserAsyncTask(Context context,String token,String userId){
            this.mContext = context;
            this.mToken = token;
            this.mUserId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(this.mContext);
            mProgress.setMessage(mContext.getResources().getString(R.string.login_dialog));
            mProgress.setIndeterminate(true);
            mProgress.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.i("JSON USER", "POST EXEC");//TODO: Delete this on production
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Log.i("JSON USER", "POST DOING");//TODO: Delete this on production
            String uri = String.format(API.USER_ID,mUserId);
            /*Ion.with(mContext)
                    .load(uri)
                    .setHeader("Authorization", mToken)
                    .setJsonObjectBody(mJson)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            Log.i("JSON USER", result.toString());
                            mProgress.dismiss();
                        }
                    });*/
            return null;
        }
    }


}
