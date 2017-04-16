package com.uzerjamal.myworldnews;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import static android.R.attr.x;

public class NewsListAdapter extends ArrayAdapter<NewsList>{

    public NewsListAdapter(Activity context, ArrayList<NewsList> newsList ) {
        super(context,0,newsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;

        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        NewsList currentNewsList = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.NewsTitle);
        titleView.setText(currentNewsList.GetTitle());

        new DownloadImageTask((ImageView) listItemView.findViewById(R.id.NewsImage)).execute(currentNewsList.GetImageUrl());

        return listItemView;
    }

    private class DownloadImageTask extends AsyncTask<String,Void, Bitmap>{
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage){
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... url){
            String urldisplay=url[0];
            Bitmap mIcon = null;
            try{
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }
    }
}


