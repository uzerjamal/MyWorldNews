package com.uzerjamal.myworldnews;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsList>>{

    private String BASE_URL = "http://content.guardianapis.com/search?api-key=25808f9d-a876-482f-8a3a-2763f99a20c3";

    private NewsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsListAdapter(this,new ArrayList<NewsList>());
        newsListView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,this);
    }

    @Override
    public Loader<List<NewsList>> onCreateLoader(int i, Bundle bundle){
        Uri baseUri=Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-blocks","all");

        return new NewsListLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsList>> loader, List<NewsList> news){
        if(news!=null && !news.isEmpty()){
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsList>> loader){
        mAdapter.clear();
    }
}
