package me.rmujica.hackerreader.models;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by rene on 1/10/16.
 */

@Table(database = PostsDatabase.class, allFields = true)
public class Post extends BaseModel {

    @PrimaryKey()
    public String objectID;
    public String title;
    public String url;
    public String author;
    public String story_title;
    public String story_url;
    public String created_at;

    public Post() {

    }

}
