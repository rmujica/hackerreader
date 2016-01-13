package me.rmujica.hackerreader.views.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import me.rmujica.hackerreader.R;
import me.rmujica.hackerreader.api.PostManager;
import me.rmujica.hackerreader.views.adapters.ItemTouchHelperCallback;
import me.rmujica.hackerreader.views.adapters.PostsAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private PostsAdapter adapter;
    private PostManager mgr;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup recyclerview
        RecyclerView list = (RecyclerView) findViewById(R.id.posts);
        list.setHasFixedSize(false);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter  = new PostsAdapter(this, list);
        list.setAdapter(adapter);

        // swipe adapter
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(list);

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setOnRefreshListener(this);

        // get posts
        mgr = new PostManager(this);
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
