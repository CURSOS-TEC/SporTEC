package credentials;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class CredentialsHelper {



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

    /**
     * Saves the session using shared preferences
     * @param email
     * @param name
     * @return
     */
    private int  saveSessionState(String email,
                                  String name){
        return 0;
    }
}
