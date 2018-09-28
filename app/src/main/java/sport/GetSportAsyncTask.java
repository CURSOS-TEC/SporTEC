package sport;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.soa4id.tec.jnavarro.sportec.R;

import network.API;

public class GetSportAsyncTask extends AsyncTask<String,Void, Boolean> {
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
                            //String msg = mContext.getResources().getString(R.string.login_error_querying);
                            //Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
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
