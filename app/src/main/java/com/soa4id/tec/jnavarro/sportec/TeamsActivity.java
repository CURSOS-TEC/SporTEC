package com.soa4id.tec.jnavarro.sportec;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import network.API;
import sport.NewsItem;
import sport.NewsItemAdapter;
import sport.TeamAdapter;
import sport.TeamItem;

public class TeamsActivity extends AppCompatActivity {
    private JsonObject mUserObject;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<TeamItem> mListTeams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        String jsonString = getIntent().getStringExtra("user");
        Log.i("JSON Teams",jsonString);
        JsonParser parser = new JsonParser();
        this.mUserObject = parser.parse(jsonString).getAsJsonObject();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewSimpleTeams);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListTeams = new ArrayList<>();

        mAdapter = new TeamAdapter(mListTeams,this);
        mRecyclerView.setAdapter(mAdapter);

        try{
            TeamAsyncTask task = new TeamAsyncTask();
            task.execute("");
        }catch(Exception e){

            Log.i("JSON TASK ERROR", e.getMessage());
        }


    }

    /**
     * Class that registers an user
     */
    private class TeamAsyncTask extends AsyncTask<String,Void, Boolean> {
        private ProgressDialog mProgress;
        private JsonArray mJson;


        /**
         * Constructor
         */
        public TeamAsyncTask(){

            mJson = new JsonArray();
        }

        /**
         * Fetch the  sports
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(final String... params) {


            String query_encoded = API.encodeTeamsQuery(mUserObject.get("email").getAsString().replace("\"",""));



            String uri = API.TEAMS+"?filter="+query_encoded;
            //String uri = API.TEAMS;
            try{
                Ion.with(TeamsActivity.this)
                        .load(uri)
                        .asJsonArray()
                        .setCallback(new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(Exception e, JsonArray result) {
                                Log.i("JSON Teams ION",result.toString());

                                for (int i = 0; i < result.size(); i++){
                                    JsonObject object = result.get(i).getAsJsonObject();
                                    mListTeams.add(new TeamItem(
                                            "id",
                                            object.get("title").getAsString(),
                                            object.get("description").getAsString(),
                                            "cat",
                                            object.get("photo").getAsString(),
                                            object.get("members").getAsJsonArray().toString()));

                                }
                                mAdapter = new TeamAdapter(mListTeams,TeamsActivity.this);
                                mRecyclerView.setAdapter(mAdapter);
                                mProgress.dismiss();
                            }
                        });
            }
            catch(Exception e){
                Log.i("JSON TEAMS ERROR",e.getMessage());
            }
            return null;
        }

        /**
         * Load the dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(TeamsActivity.this);
            mProgress.setMessage(TeamsActivity.this.getResources().getString(R.string.message_sports));
            mProgress.setIndeterminate(true);
            mProgress.show();
        }

    }
}


