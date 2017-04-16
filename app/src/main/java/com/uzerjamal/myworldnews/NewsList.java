package com.uzerjamal.myworldnews;

public class NewsList {

    private String title;
    private String imageUrl;

    public NewsList(String cTitle, String cImageUrl){
        title=cTitle;
        imageUrl=cImageUrl;
    }

    public String GetTitle(){
        return title;
    }

    public String GetImageUrl(){
        return imageUrl;
    }
}
