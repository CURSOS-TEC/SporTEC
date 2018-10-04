package com.soa4id.tec.jnavarro.sportec;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import soaImage.ImageConverter;

public class NewsActivity extends AppCompatActivity {
    private JsonObject mArticle;
    private ImageView mPhoto;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        this.mPhoto = findViewById(R.id.news_activity_image);
        this.mTitle = findViewById(R.id.news_activity_title);
        this.mDescription = findViewById(R.id.news_activity_description);
        this.mCategory = findViewById(R.id.news_activity_category);

        try{
            String jsonString = getIntent().getStringExtra("article");
            if (jsonString != null){
                Log.i("JSON Article view",jsonString);
                JsonParser parser = new JsonParser();
                mArticle = parser.parse(jsonString).getAsJsonObject();
                this.mTitle.setText(mArticle.get("title").getAsString());
                this.mDescription.setText(mArticle.get("description").getAsString());
                this.mCategory.setText(mArticle.get("category").getAsString());
                Log.i("JSON Picasso",mArticle.get("photoUri").getAsString().replace("\"",""));
                Picasso.get()
                        .load(mArticle.get("photoUri").getAsString().replace("\"",""))
                        .resize(100, 100)
                        .centerCrop()
                        .into(mPhoto);
            }else {
                Log.i("JSON Article view","Null Article");
            }
        }
        catch(Exception e){
            Log.i("JSON Article error",e.getMessage());
        }
    }
}
