package com.soa4id.tec.jnavarro.sportec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NewsActivity extends AppCompatActivity {
    private JsonObject mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        try{
            String jsonString = getIntent().getStringExtra("article");
            if (jsonString != null){
                Log.i("JSON Article view",jsonString);
                JsonParser parser = new JsonParser();
                mArticle = parser.parse(jsonString).getAsJsonObject();
            }else {
                Log.i("JSON Article view","Null Article");
            }
        }
        catch(Exception e){
            Log.i("JSON Article",e.getMessage());
        }
    }
}
