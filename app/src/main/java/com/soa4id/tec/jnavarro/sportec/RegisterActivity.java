package com.soa4id.tec.jnavarro.sportec;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import credentials.CredentialsHelper;
import network.API;
import soaImage.ImageConverter;


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
    private String[] mListItems;
    private boolean[] mCheckedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mSportOptions = new String[3];
        mSportOptions[0] = "Sport";
        mSportOptions[1] = "Sport1";
        mSportOptions[2] = "Sport2";
        mCheckedItems = new boolean[mSportOptions.length];




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

                /*try{
                    renderSportOptions();
                }catch (Exception e){
                    Log.i("JSON",e.getCause().toString());
                }*/

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
        ImageConverter converter = new ImageConverter();
        String dataStr = converter.BitMapToString(this.mProfilePicture);
        String message = getResources().getString(R.string.message_phototaken);
        Snackbar.make(
                this.mView,
                message, /*String.valueOf(this.mProfilePicture.getGenerationId())*/
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
    public void renderSportOptions(String[] options){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        final boolean[] checkedArray = new boolean[options.length];
        final List<String> optList = Arrays.asList(options);
        builder.setTitle("Title");
        builder.setMultiChoiceItems(options, checkedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedArray[which] = isChecked;
                String currentItem = optList.get(which);

            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("JSON", String.valueOf(checkedArray));
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("JSON", "Cancel");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private class GetSportAsyncTask extends AsyncTask<String,Void, Boolean> {
        private Context mContext;
        private ProgressDialog mProgress;
        private JsonObject mJson;

        /**
         *
         * @param context
         */
        public GetSportAsyncTask(Context context){
            this.mContext = context;
        }

        /**
         * Fetch the  sports
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(final String... params) {
            mJson = new JsonObject();
            Ion.with(mContext)
                .load(API.SPORTS)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                    if (e != null){

                    }else{
                        List<String> resultSports;
                        for (int i = 0; i < result.size(); i ++){
                            String sportName = result.get(i).getAsString();
                        }

                    }
                    Log.i("JSON SPORTS",result.toString());//TODO: Delete this on production
                    mProgress.dismiss();
                    }
                });
            return null;
        }

        /**
         * Load the dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(this.mContext);
            mProgress.setMessage(mContext.getResources().getString(R.string.message_fetchsports));
            mProgress.setIndeterminate(true);
            mProgress.show();

        }

    }


}

