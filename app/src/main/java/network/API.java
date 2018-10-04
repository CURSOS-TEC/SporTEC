package network;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
}
