package uk.co.sentinelweb.igttest;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.sentinelweb.igttest.net.Services;

/**
 * Module providing application level dependencies
 * Created by robert on 28/11/15.
 */
@Module
public class GameApplicationModule {
    private Application mApp;
    
    public GameApplicationModule(Application app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public SharedPreferences provideGamePreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApp);
    }

    @Provides
    @Singleton
    public Services provideServices() {
        return new Services(mApp);
    }

}
