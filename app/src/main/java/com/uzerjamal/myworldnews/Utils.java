package com.uzerjamal.myworldnews;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private Utils(){}

    public static List<NewsList> fetchNewsData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        List<NewsList> news = extractNewsData(jsonResponse);
        return news;
    }

    private static URL createUrl(String stringURL){
        URL url=null;
        try{
            url = new URL(stringURL);
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";

        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if (inputStream!=null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<NewsList> extractNewsData(String jsonData){
        if(TextUtils.isEmpty(jsonData))
            return null;

        List<NewsList> news = new ArrayList<>();

        try{
            JSONObject root = new JSONObject(jsonData);
            JSONArray resultArray = root.getJSONObject("response").getJSONArray("results");

            for(int i=0;i<resultArray.length();i++){
                JSONObject resultElement = resultArray.getJSONObject(i);
                JSONObject blocksElement = resultElement.getJSONObject("blocks");
                JSONObject mainElement = blocksElement.getJSONObject("main");
                JSONArray elementsArray = mainElement.getJSONArray("elements");
                JSONObject elementsElement = elementsArray.getJSONObject(0);
                JSONArray assetsArray = elementsElement.getJSONArray("assets");
                JSONObject assetsElement = assetsArray.getJSONObject(1);


                String imageUrl = assetsElement.getString("file");
                String articleTitle = resultElement.getString("webTitle");

                news.add(new NewsList(articleTitle, imageUrl));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        return news;
    }
}
