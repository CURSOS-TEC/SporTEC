package com.soa4id.tec.jnavarro.sportec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

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
        mListTeams.add(new TeamItem("id","title","desc","cat","uri","members"));
        mAdapter = new TeamAdapter(mListTeams,this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
