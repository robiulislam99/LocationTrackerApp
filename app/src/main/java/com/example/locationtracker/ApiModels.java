package com.example.locationtracker;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// API Response Models for JSONPlaceholder API

class Post {
    @SerializedName("userId")
    private int userId;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}

// API Service Interface
interface ApiService {
    @retrofit2.http.GET("posts")
    retrofit2.Call<List<Post>> getPosts();
}