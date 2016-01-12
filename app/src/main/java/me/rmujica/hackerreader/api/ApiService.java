package me.rmujica.hackerreader.api;

import me.rmujica.hackerreader.models.Envelope;
import me.rmujica.hackerreader.models.Post;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by rene on 1/10/16.
 */

public interface ApiService {

    // the query search could be asked as a parameter. hardcoded for now.
    @GET("search_by_date?query=android&tags=story")
    Observable<Envelope<Post>> getPosts();

}
