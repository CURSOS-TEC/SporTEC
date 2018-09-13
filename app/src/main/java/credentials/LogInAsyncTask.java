package credentials;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.soa4id.tec.jnavarro.sportec.R;

import network.API;

public class LogInAsyncTask extends AsyncTask<String,Void, JsonObject> {
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
    }

    /**
     * Makes the login
     * @param credentials
     * @return
     */
    @Override
    protected JsonObject doInBackground(final String... credentials) {
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
                        Log.i("JSON ",result.toString());//TODO: Delete this on production
                        mProgress.dismiss();
                        resultop = result;
                        mProgress.dismiss();
                    }
                });
        return resultop;
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
    protected void onPostExecute(JsonObject presultop) {
        super.onPostExecute(presultop);
    }
}
