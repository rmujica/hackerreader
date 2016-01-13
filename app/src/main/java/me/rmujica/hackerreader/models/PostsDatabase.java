package me.rmujica.hackerreader.models;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by rene on 1/13/16.
 */

@Database(name = PostsDatabase.NAME, version = PostsDatabase.VERSION)
public class PostsDatabase {
    public static final int VERSION = 1;
    public static final String NAME = "Posts";
}
