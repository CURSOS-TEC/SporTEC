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
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import credentials.CredentialsHelper;
import credentials.LogInAsyncTask;
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
    private String mProfilePic;
    private ArrayList<String> mUserSports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mSportOptions = new String[3];
        mSportOptions[0] = "Sport";
        mSportOptions[1] = "Sport1";
        mSportOptions[2] = "Sport2";
        mCheckedItems = new boolean[mSportOptions.length];

        this.mUserSports = new ArrayList<String>();


        this.mCredentialsHelper = new CredentialsHelper();
        this.mSportOptions = getResources().getStringArray(R.array.sports_local_options);
        this.mCheckSports = new Boolean[this.mSportOptions.length];

        this.mEditTextName = findViewById(R.id.register_activity_fragment_name);
        this.mEditTextEmail = findViewById(R.id.register_activity_fragment_email);
        this.mEditTextPassword = findViewById(R.id.register_activity_fragment_password);
        this.mEditTextPasswordConfirm =findViewById(R.id.register_activity_fragment_password_confirm);
        this.mButtonSummit = findViewById(R.id.register_activity_signin_summit);
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
                signIn();
            }
        });

        this.mButtonSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSportAsyncTask task = new GetSportAsyncTask();
                task.execute("");
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
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(this.mProfilePicture,100,100,false);
        ImageConverter converter = new ImageConverter();
        this.mProfilePic = converter.BitMapToString(scaledBitmap);
        Log.i("JSON register",mProfilePic);
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
        //Create the user object.

        JsonObject newUser = new JsonObject();
        String name= this.mEditTextName.getText().toString();
        String email = this.mEditTextEmail.getText().toString();
        String password = this.mEditTextPassword.getText().toString();
        String passwordConfirm = this.mEditTextPasswordConfirm.getText().toString();

        if( !(name.equals(""))
            && !(email.equals(""))
            && !(password.equals(""))
            && !(passwordConfirm.equals(""))){

            if(password.equals(passwordConfirm)){
                try{
                    if(this.mProfilePic != null){
                        newUser.addProperty("name",name);
                        newUser.addProperty("email",email);
                        newUser.addProperty("password",password);
                        newUser.addProperty("username",email);//TODO: Change to a real username
                        newUser.addProperty("photo",this.mProfilePic);
                        //newUser.addProperty("photo","test");
                        JsonArray jArray = new JsonArray();
                        for (int i = 0; i < mUserSports.size(); i++){
                            JsonPrimitive primitive = new JsonPrimitive(mUserSports.get(i).toString());
                            jArray.add(primitive);
                        }
                        newUser.add("sportsPreferred",jArray);

                        RegisterAsyncTask task = new RegisterAsyncTask(newUser);
                        task.execute("");

                        Log.i("JSON User summit", newUser.toString());//:TODO Delete this on production

                    }else{
                        Snackbar.make(getCurrentFocus(),"Foto de Perfil no seleccionada",Snackbar.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ee) {
                    Log.i("JSON ERROR", ee.getMessage());//:TODO Delete this on production
                }
            }else {
                Snackbar.make(getCurrentFocus(),"Contraseñas deben de ser iguales",Snackbar.LENGTH_SHORT).show();
                this.mEditTextPassword.setText("");
                this.mEditTextPasswordConfirm.setText("");
            }
        }else{
            Snackbar.make(getCurrentFocus(),"Campos incompletos",Snackbar.LENGTH_SHORT).show();
        }

    }

    /**
     * Gets the sport options to select
     */
    public void renderSportOptions(final String[] options){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        final boolean[] checkedArray = new boolean[options.length];
        final List<String> optList = Arrays.asList(options);
        mUserSports.clear();
        builder.setTitle("Deportes");
        builder.setMultiChoiceItems(options, checkedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedArray[which] = isChecked;
                if(isChecked){

                    mUserSports.add(options[which]);

                }else{
                    mUserSports.remove(options[which]);
                }
                Log.i("JSON SPORTS Check", options[which] + " :  " + Boolean.toString(isChecked));//TODO: Delete this on production
                Log.i("JSON SPORTS User List", mUserSports.toString());//TODO: Delete this on production


            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("JSON SPORTS Selected", mUserSports.toString());//TODO: Delete this on production
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("JSON", "Cancel");//TODO: Delete this on production
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * Class that renders the sport options
     */
    private class GetSportAsyncTask extends AsyncTask<String,Void, Boolean> {
        private ProgressDialog mProgress;
        private JsonObject mJson;

        /**
         *
         */
        public GetSportAsyncTask(){
        }

        /**
         * Fetch the  sports
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(final String... params) {
            Log.i("JSON SPORTS","Do in background");//TODO: Delete this on production
            mJson = new JsonObject();
            try{
                Ion.with(RegisterActivity.this)
                    .load(API.SPORTS)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            if (e != null){
                                Log.i("JSON SPORTS  ION ERROR",e.getMessage());//TODO: Delete this on production
                            }else{
                                Log.i("JSON SPORTS  IN RESULT",result.toString());//TODO: Delete this on production
                                try{
                                    ArrayList<String> stringArrayList = new ArrayList<String>();
                                    for (JsonElement sportIterator : result){
                                        JsonObject sport = sportIterator.getAsJsonObject();
                                        String sportName = sport.get("name").getAsString();
                                        stringArrayList.add(sportName);
                                    }
                                    String [] stringArray = stringArrayList.toArray(new String[stringArrayList.size()]);
                                    try{
                                        renderSportOptions(stringArray);
                                    }catch (Exception ee){
                                        Log.i("JSON",ee.getCause().toString());
                                    }
                                    Log.i("JSON SPORTS String[] ",stringArrayList.toString());//TODO: Delete this on production
                                }
                                catch(Exception ex){
                                    Log.i("JSON SPORTS Error ",ex.getMessage());//TODO: Delete this on production
                                }
                            }
                            //Log.i("JSON SPORTS",result.toString());//TODO: Delete this on production
                            mProgress.dismiss();
                        }
                    });
            }
            catch(Exception e){
                Log.i("JSON SPORTS ERROR",e.getMessage());//TODO: Delete this on production
            }
            return null;
        }

        /**
         * Load the dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(RegisterActivity.this);
            mProgress.setMessage(RegisterActivity.this.getResources().getString(R.string.message_fetchsports));
            mProgress.setIndeterminate(true);
            mProgress.show();
        }

    }


    /**
     * Class that registers an user
     */
    private class RegisterAsyncTask extends AsyncTask<String,Void, Boolean> {
        private ProgressDialog mProgress;
        private JsonObject mJson;

        /**
         * Constructor
         */
        public RegisterAsyncTask(JsonObject mJson){
            this.mJson = mJson;
        }

        /**
         * Fetch the  sports
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(final String... params) {
            try{
                Ion.with(RegisterActivity.this)
                        .load(API.REGISTER)
                        .setJsonObjectBody(mJson)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                Log.i("JSON POST", "Try to register: "+mJson.toString());
                                Log.i("JSON POST",result.toString());
                                mProgress.dismiss();
                            }
                        });
            }
            catch(Exception e){

            }
            return null;
        }

        /**
         * Load the dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(RegisterActivity.this);
            mProgress.setMessage(RegisterActivity.this.getResources().getString(R.string.message_fetchsports));
            mProgress.setIndeterminate(true);
            mProgress.show();
        }

    }


}

