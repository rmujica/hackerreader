package me.rmujica.hackerreader.inject.components;

import javax.inject.Singleton;

import dagger.Component;
import me.rmujica.hackerreader.api.ApiManager;
import me.rmujica.hackerreader.inject.modules.AppModule;
import me.rmujica.hackerreader.inject.modules.NetModule;
import me.rmujica.hackerreader.views.activities.MainActivity;

/**
 * Created by rene on 1/11/16.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(MainActivity activity);

    void inject(ApiManager apiManager);
}
