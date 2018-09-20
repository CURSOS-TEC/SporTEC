package com.soa4id.tec.jnavarro.sportec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import storage.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkUserLogged();


    }

    /**
     *
     */
    private void checkUserLogged () {
        DBHelper helper = new DBHelper(MainActivity.this);
        String result = helper.checkUserCredentials();
        if (!(result.equals(""))){
            Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
