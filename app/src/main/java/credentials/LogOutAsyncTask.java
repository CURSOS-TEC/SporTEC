package credentials;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.soa4id.tec.jnavarro.sportec.R;

import storage.DBHelper;

public class LogOutAsyncTask  extends AsyncTask<String,Void, Boolean> {
    private Context mContext;
    private AlertDialog mProgress;


    /**
     * Constructor
     * @param context
     */
    public LogOutAsyncTask (Context context){
        mContext = context;
    }

    /**
     *
     * @param strings
     * @return
     */
    @Override
    protected Boolean doInBackground(String... strings) {
        Log.i("Log Out", "on Do in background");//TODO: Delete this on production
        try{
            DBHelper helper = new DBHelper(mContext);
            helper.removeCredentials();
            mProgress.dismiss();


        }catch (Exception e){
            Log.i("Log Out", e.getMessage());//TODO: Delete this on production

        }

        return null;
    }


    /**
     * Init the progress
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("Log Out", "on Pre Execute");//TODO: Delete this on production
        try{
            if(mContext == null){
                throw  new Exception("Soa Error mContext is null");

            }
            AlertDialog.Builder alertDialog  = new AlertDialog.Builder(this.mContext);
            this.mProgress =alertDialog.create();
            mProgress.setTitle(mContext.getResources().getString(R.string.logout_dialog));
            mProgress.show();


        }
        catch (Exception e) {
            Log.i("Log Out 1", e.getMessage());
        }

    }

    /**
     * Returns the result of the login
     * @param presultop
     */
    @Override
    protected void onPostExecute(Boolean presultop) {
        super.onPostExecute(presultop);
        Log.i("Log Out", "on Post Execute");//TODO: Delete this on production
    }

}
