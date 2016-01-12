package me.rmujica.hackerreader.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rene on 1/10/16.
 */

public class Post {

    public String title;
    public String url;
    public String author;
    @SerializedName("parent_id")
    public Integer parentId;

}
