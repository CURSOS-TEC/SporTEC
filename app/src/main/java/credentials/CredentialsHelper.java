package credentials;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CredentialsHelper {

    private Context mContext;

    /**
     * Sets the context
     * @param mContext
     */
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * Logs an user
     * @param email
     * @param password
     * @return
     */
    public int login (String email, String password){
        return 0;
    }

    /**
     * Register an user
     * @param email
     * @param name
     * @param password
     * @param sports
     * @param profilePicture
     * @return
     */
    public int register (String email,
                         String name,
                         String password,
                         ArrayList<String> sports,
                         Bitmap profilePicture){
        return 0;
    }

    //

    /**
     * Saves the session using shared preferences
     * @param context
     * @param email
     * @param password
     * @return The result of the operation
     */
    public void  login(Context context,
                       String email,
                       String password){



        LogInAsyncTask task = new LogInAsyncTask(context);
        task.execute(email,password);

    }
}
