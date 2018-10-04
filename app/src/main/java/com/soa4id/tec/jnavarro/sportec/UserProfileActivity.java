package com.soa4id.tec.jnavarro.sportec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import soaImage.ImageConverter;

public class UserProfileActivity extends AppCompatActivity {
    private JsonObject mUserObject;
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private TextView mTextViewEquipos;
    private TextView mTextViewSports;
    private TextView mTextViewDesafios;
    private TextView mUserId;
    private ImageView mProfilePic;
    private Button mEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        String jsonString = getIntent().getStringExtra("user");
        Log.i("JSON Profile",jsonString);
        JsonParser parser = new JsonParser();
        this.mUserObject = parser.parse(jsonString).getAsJsonObject();

        this.mTextViewName = findViewById(R.id.profile_username);
        this.mTextViewEquipos = findViewById(R.id.profile_seguidores);
        this.mTextViewSports= findViewById(R.id.profile_teams);
        this.mTextViewDesafios = findViewById(R.id.profile_desafios);
        this.mTextViewEmail = findViewById(R.id.profile_email_detail);
        this.mUserId = findViewById(R.id.profile_user_id);
        this.mProfilePic = findViewById(R.id.profile_profileImg);
        this.mEditButton =findViewById(R.id.profile_edit_button_);
        this.mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(UserProfileActivity.this,RegisterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user",mUserObject.toString());
                    UserProfileActivity.this.startActivity(intent);
                }
                catch (Exception e){
                    Log.i("Log Out", e.getMessage());
                }
            }
        });

        fillData();


    }

    public void fillData (){
        this.mTextViewName.setText(mUserObject
                                    .get("username")
                                    .getAsString());

        ImageConverter converter  = new ImageConverter();
        this.mTextViewEquipos.setText("1");
        Bitmap bitmap = converter.StringToBitMap(mUserObject.get("uriImage").toString()
                .replace("\"","")
                .replace("\\n",""));
        this.mProfilePic.setImageBitmap(bitmap);
        this.mTextViewEmail.setText(mUserObject.get("email").getAsString());
        this.mUserId.setText(mUserObject.get("userId").toString());

    }
}
