package com.soa4id.tec.jnavarro.sportec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import credentials.CredentialsHelper;

public class RegisterActivity extends AppCompatActivity {
    private View mView;
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextPasswordConfirm;
    private Button mButtonSummit;
    private Button   mButtonTakePhoto;
    private Button   mButtonSports;
    private CredentialsHelper mCredentialsHelper;
    private Bitmap mProfilePicture;
    private String[] mSportOptions;
    private Boolean[] mCheckSports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.mCredentialsHelper = new CredentialsHelper();
        this.mSportOptions = getResources().getStringArray(R.array.sports_local_options);
        this.mCheckSports = new Boolean[this.mSportOptions.length];

        this.mEditTextName = findViewById(R.id.signin_fragment_name);
        this.mEditTextEmail = findViewById(R.id.signin_fragment_email);
        this.mEditTextPassword = findViewById(R.id.login_fragment_password);
        this.mEditTextPasswordConfirm =findViewById(R.id.signin_fragment_password_confirm);
        this.mButtonSummit = findViewById(R.id.signin_fragment_summit);
        this.mButtonTakePhoto = findViewById(R.id.signin_fragment_take_photo);
        this.mButtonSports = findViewById(R.id.signin_fragment_select_sports);

        this.mButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView = v;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);

            }
        });

        this.mButtonSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.mButtonSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    /**
     * Manages the result of the interaction between this app and the camera app
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mProfilePicture = (Bitmap)data.getExtras().get("data");
        Snackbar.make(
                this.mView,
                String.valueOf(this.mProfilePicture.getGenerationId()),
                Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Tries to register an user.
     */
    public void signIn(){

    }

    /**
     * Gets the sport options to select
     */
    public void renderSportOptions(){

    }

}
