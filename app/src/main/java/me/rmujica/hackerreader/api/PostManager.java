package me.rmujica.hackerreader.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import javax.inject.Inject;

import me.rmujica.hackerreader.HackerReaderApplication;
import me.rmujica.hackerreader.models.Post;
import me.rmujica.hackerreader.models.Post_Table;
import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by rene on 1/10/16.
 */

public class PostManager {

    @Inject
    Retrofit retrofit;

    private ApiService api;
    private Context context;

    public PostManager(Context context) {
        this.context = context;
        ((HackerReaderApplication) context.getApplicationContext()).getNetComponent().inject(this);
        api = retrofit.create(ApiService.class);
    }

    public Observable<Post> getPosts() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // retrieve existing posts
        List<Post> existing = SQLite.select().from(Post.class)
                .orderBy(Post_Table.created_at, false).queryList();

        // try to get posts from internet
        if (activeNetwork != null && activeNetwork.isConnected()) {
            Observable<Post> fromNet = api.getPosts().subscribeOn(Schedulers.io())
                    .flatMap(e -> Observable.from(e.hits))
                    .filter(p -> !existing.contains(p)) // we are not going to save posts already in db
                    .map(p -> {
                        p.save();
                        return p;
                    });
            return Observable.concat(Observable.from(existing), fromNet)
                    .filter(p -> (p.story_url != null || p.url != null) && !p.hidden);
        }

        // retrieve posts from db
        return Observable.from(existing)
                .filter(p -> (p.story_url != null || p.url != null) && !p.hidden);
    }

}
