package me.rmujica.hackerreader.inject.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rene on 1/10/16.
 */

@Module
public class AppModule {

    Application application;
    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

}
