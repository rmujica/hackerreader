package me.rmujica.hackerreader.inject.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by rene on 1/10/16.
 */

@Module
public class NetModule {

    String endpoint;

    public NetModule(String endpoint) {
        this.endpoint = endpoint;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(endpoint)
                .build();

        return retrofit;
    }

}
