package com.uzerjamal.myworldnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsListLoader extends AsyncTaskLoader<List<NewsList>>{

    private String mUrl;

    public NewsListLoader(Context context, String url){
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<NewsList> loadInBackground(){
        if(mUrl==null)
            return null;

        return Utils.fetchNewsData(mUrl);
    }
}
