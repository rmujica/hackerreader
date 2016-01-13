package me.rmujica.hackerreader.viewmodels;

import android.databinding.BaseObservable;

import me.rmujica.hackerreader.models.Post;

/**
 * Created by rene on 1/12/16.
 */

public class PostViewModel extends BaseObservable {

    private Post post;

    public PostViewModel(Post post) {
        this.post = post;
    }

    public String getPostAuthor() {
        return post.author;
    }

    public String getPostTitle() {
        return post.title;
    }

    public String getPostURL() {
        return post.url;
    }

    public String getPostMeta() {
        return post.author + " - " + post.url;
    }

}
