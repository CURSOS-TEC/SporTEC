package credentials;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.soa4id.tec.jnavarro.sportec.MainActivity;
import com.soa4id.tec.jnavarro.sportec.R;

import java.net.URL;

import network.API;
import storage.DBHelper;

public class LogInAsyncTask extends AsyncTask<String,Void, Boolean> {
    private Context mContext;
    private ProgressDialog mProgress;
    private JsonObject mJson;
    private JsonObject resultop;

    /**
     * Separate this interaction to the principal activity
     * @param context
     */
    public LogInAsyncTask( Context context) {
        this.mContext = context;
        this.resultop = new JsonObject();
    }

    /**
     * Makes the login
     * @param credentials
     * @return
     */
    @Override
    protected Boolean doInBackground(final String... credentials) {
        final DBHelper helper = new DBHelper(mContext);
        mJson = new JsonObject();
        mJson.addProperty("email",credentials[0]);
        mJson.addProperty("password",credentials[1]);
        Log.i("JSON",credentials[0]);//TODO: Delete this on production
        Log.i("JSON",credentials[1]);//TODO: Delete this on production
        Log.i("JSON", API.LOGIN);//TODO: Delete this on production
        Ion.with(mContext)
                .load(API.LOGIN)
                .setJsonObjectBody(mJson)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null){
                            String msg = mContext.getResources().getString(R.string.login_error_querying);
                            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                        }
                        Log.i("JSON ",result.toString());//TODO: Delete this on production
                        if(result.getAsJsonObject("error") != null){
                            Log.i("JSON ERROR ","DETECTED");//TODO: Delete this on production
                            String msg = result.getAsJsonObject("error").get("message").toString();
                            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                        }else{
                            Log.i("JSON OK ",result.get("userId").toString());//TODO: Delete this on production

                            final String userId = result.get("userId").toString().replace("\"","");
                            final String access_token = result.get("id").toString().replace("\"","");

                            String uri =  API.USER_ID.concat(userId).concat("?access_token=").concat(access_token);
                            Log.i("JSON OK token", uri);//TODO: Delete this on production
                            Ion.with(mContext)
                                .load(uri)
                                .setHeader("Authorization", access_token)
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {
                                        Log.i("JSON USER", result.toString());
                                        //user
                                        String userName =result.get("name").toString().replace("\"","");
                                        //username
                                        String email = credentials[0];
                                        //email
                                        String image = result.get("photo").toString().replace("\"","");;
                                        //photo
                                        String sports = result.get("sportsPreferred").toString();
                                        helper.removeCredentials();
                                        helper.addUserCredentials(userId,userName,email,image,access_token);
                                        Toast.makeText(mContext,credentials[0],Toast.LENGTH_SHORT).show();
                                        Log.i("JSON OK token", access_token);//TODO: Delete this on production

                                        mProgress.dismiss();
                                    }
                                });
                        }
                        mProgress.dismiss();
                    }
                });
        Log.i("JSON", resultop.toString());//TODO: Delete this on production
        return null;
    }

    /**
     * Init the progress
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress = new ProgressDialog(this.mContext);
        mProgress.setMessage(mContext.getResources().getString(R.string.login_dialog));
        mProgress.setIndeterminate(true);
        mProgress.show();
    }

    /**
     * Returns the result of the login
     * @param presultop
     */
    @Override
    protected void onPostExecute(Boolean presultop) {
        super.onPostExecute(presultop);
        Log.i("JSON", resultop.toString());//TODO: Delete this on production
    }
}
