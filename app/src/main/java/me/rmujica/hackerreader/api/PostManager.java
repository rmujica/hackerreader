package me.rmujica.hackerreader.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import javax.inject.Inject;

import me.rmujica.hackerreader.HackerReaderApplication;
import me.rmujica.hackerreader.models.Post;
import me.rmujica.hackerreader.models.Post_Table;
import retrofit2.Retrofit;
import rx.Observable;

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

        if (activeNetwork == null || !activeNetwork.isConnected()) {
            // if no internet connection is detected, should get from database
            List<Post> posts = SQLite.select().from(Post.class)
                    .orderBy(Post_Table.created_at, false).queryList();
            return Observable.from(posts)
                    .filter(p -> p.story_url != null || p.url != null);
        } else {
            return api.getPosts()
                    .flatMap(e -> {
                        // save posts
                        TransactionManager.getInstance()
                                .addTransaction(new SaveModelTransaction<>(ProcessModelInfo
                                        .withModels(e.hits)));
                        return Observable.from(e.hits);
                    })
                    .filter(p -> p.story_url != null || p.url != null);
        }
    }

}
