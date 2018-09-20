package com.soa4id.tec.jnavarro.sportec;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import credentials.CredentialsHelper;
import storage.DBHelper;

public class CredentialsActivity extends AppCompatActivity {

    private AppBarLayout mAppBarLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CredentialViewPagerAdapter mCredentialViewPagerAdapter;
    private DBHelper mDbhelper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);

        this.mAppBarLayout = findViewById(R.id.credential_activity_appbar_layout);
        this.mTabLayout = findViewById(R.id.credential_activity_tab_layout);
        this.mViewPager = findViewById(R.id.credential_activity_view_pager);
        this.mCredentialViewPagerAdapter = new CredentialViewPagerAdapter(getSupportFragmentManager());

        mCredentialViewPagerAdapter.addFragment(new LoginFragment(),
                getResources().getString(R.string.credential_activity_tab_login));
        mCredentialViewPagerAdapter.addFragment(new SigninFragment(),
                getResources().getString(R.string.credential_activity_tab_signin));

        mViewPager.setAdapter(mCredentialViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mDbhelper = new DBHelper(CredentialsActivity.this);

        //alreadyLogged();



    }

    /**
     * Checks if there is a user logged
     */
    private void alreadyLogged(){
        String user = this.mDbhelper.checkUserCredentials();
        if (!(user.equals("null") )){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(CredentialsActivity.this, "User Not logged", Toast.LENGTH_SHORT).show();
        }

    }
}
