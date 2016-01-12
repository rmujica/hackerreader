package me.rmujica.hackerreader.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import me.rmujica.hackerreader.HackerReaderApplication;
import me.rmujica.hackerreader.R;
import me.rmujica.hackerreader.api.ApiManager;
import me.rmujica.hackerreader.api.ApiService;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    /*@Inject
    Retrofit retrofit;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //((HackerReaderApplication) getApplication()).getNetComponent().inject(this);

        /*ApiService api = retrofit.create(ApiService.class);

        api.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> Log.d("cosas", e.hits.toString()));*/

        ApiManager mgr = new ApiManager(this);
        mgr.getStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> Log.d("cosa", p.title));
    }
}
