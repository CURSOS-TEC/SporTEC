package network;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

public class API {
    public static String API_BASE = "https://murmuring-fjord-87445.herokuapp.com/api";
    public static String LOGIN =  API_BASE + "/SoaUsers/login";
    public static String REGISTER =  API_BASE + "/SoaUsers";
    public static String USER =  API_BASE + "/SoaUsers";
    public static String SPORTS =  API_BASE + "/Sports";
    public static String USER_ID =  API_BASE + "/SoaUsers/";
    public static String ARTICLES =  API_BASE + "/SportArticles";
    public static String DELETE_USER = USER_ID;
    public static String EDIT_USER = USER_ID;
    public static String TEAMS = API_BASE + "/Teams";

    /**
     * Encode Query method
     * @param userObject
     * @return
     */
    public static String encodeQuery (JsonObject userObject){

        String result = "";
        String[] sports = userObject.get("sports").toString()
                .replace("\"","")
                .replace("\\","")
                .split(",");
        int size = sports.length;
        try{
            JSONObject where = new JSONObject();
            JSONObject or = new JSONObject();
            JSONArray or_array = new JSONArray();
            for (int i = 0; i < size; i++){
                JSONObject category = new JSONObject();
                category.put("category",sports[i].replace("[","").replace("]",""));
                or_array.put(category);
            }
            or.put("or",or_array);
            where.put("where",or);
            Log.i("JSON QUERY array", where.toString());
            result = URLEncoder.encode(where.toString());
        }catch (Exception ee){
            Log.i("JSON QUERY ERROR", ee.getMessage());
        }
        return result;
    };

    public static  String encodeTeamsQuery (String email){
        String result = "";
        /*
        * {"where": { "members": { "inq": ["soauser@mail.com"]}}}*/
        try{
            JSONArray array = new JSONArray();
            array.put(email);

            JSONObject inq = new JSONObject();
            inq.put("inq",array);

            JSONObject members = new JSONObject();
            members.put("members",inq);

            JSONObject where = new JSONObject();
            where.put("where",members);

            Log.i("JSON QUERY",where.toString());
            result = URLEncoder.encode(where.toString());
            Log.i("JSON QUERY",result);
        }catch (Exception e){
            Log.i("JSON QUERY ERROR",e.getMessage());
        }


        return result;
    }
}
