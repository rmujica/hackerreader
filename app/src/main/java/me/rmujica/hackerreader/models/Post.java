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
    public Boolean hidden = false;

    public Post() {

    }

    @Override
    public int hashCode() {
        int result = 41;
        result = 37 * result + objectID.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Post)) {
            return false;
        } else if (o == this) {
            return true;
        }

        Post rhs = (Post) o;
        return objectID.equals(rhs.objectID);
    }

}
