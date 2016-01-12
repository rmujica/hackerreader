package me.rmujica.hackerreader;

import android.app.Application;

import me.rmujica.hackerreader.inject.components.DaggerNetComponent;
import me.rmujica.hackerreader.inject.components.NetComponent;
import me.rmujica.hackerreader.inject.modules.AppModule;
import me.rmujica.hackerreader.inject.modules.NetModule;

/**
 * Created by rene on 1/11/16.
 */

public class HackerReaderApplication extends Application {

    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://hn.algolia.com/api/v1/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return this.netComponent;
    }

}
