package me.rmujica.hackerreader.views.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import javax.inject.Inject;

import me.rmujica.hackerreader.HackerReaderApplication;
import me.rmujica.hackerreader.R;
import me.rmujica.hackerreader.api.ApiManager;
import me.rmujica.hackerreader.api.ApiService;
import me.rmujica.hackerreader.views.adapters.PostsAdapter;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private PostsAdapter adapter;
    private ApiManager mgr;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup recyclerview
        RecyclerView list = (RecyclerView) findViewById(R.id.posts);
        list.setHasFixedSize(false);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter  = new PostsAdapter(this);
        list.setAdapter(adapter);

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this);

        // get posts
        mgr = new ApiManager(this);
        refresh.post(() -> {
            refresh.setRefreshing(true);
            getPosts();
        });
    }

    private void getPosts() {
        mgr.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addPost,
                        e -> Log.d("error", e.getMessage()), // todo: should report error to crashlytics or another service
                        () -> refresh.setRefreshing(false));
    }

    @Override
    public void onRefresh() {
        adapter.clearPosts();
        getPosts();
    }
}
