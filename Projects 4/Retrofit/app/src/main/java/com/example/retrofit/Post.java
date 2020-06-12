package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

public class Post
{
    private int userId;

    private Integer id;

    public Post(int userId, String title, String text)
    {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    private String title;

    // Now 'text' will be used as 'body' value if Gson
    @SerializedName("body")
    private String text;

    public int getUserId()
    {
        return userId;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getText()
    {
        return text;
    }
}

