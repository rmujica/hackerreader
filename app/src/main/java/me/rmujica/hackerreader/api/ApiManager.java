package me.rmujica.hackerreader.api;

import android.content.Context;

import javax.inject.Inject;

import me.rmujica.hackerreader.HackerReaderApplication;
import me.rmujica.hackerreader.models.Post;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by rene on 1/10/16.
 */

public class ApiManager {

    @Inject
    Retrofit retrofit;

    private ApiService api;

    public ApiManager(Context context) {
        ((HackerReaderApplication) context.getApplicationContext()).getNetComponent().inject(this);
        api = retrofit.create(ApiService.class);
    }

    public Observable<Post> getStories() {
        return api.getPosts().flatMap(e -> Observable.from(e.hits)).filter(p -> p.parentId == null);
    }

}
