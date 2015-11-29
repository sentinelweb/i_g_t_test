package uk.co.sentinelweb.igttest;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Application level object
 *
 * Created by robert on 28/11/15.
 */
public class GameApplication extends Application {

    public static GameApplication staticInstance = null;

    // not used: just to show injection
    @Inject
    SharedPreferences gamePreferences;

    GameApplicationComponent component;

    @Inject
    public GameApplication() {
        staticInstance = this;
    }

    public static GameApplication getStaticInstance() {
        return staticInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = GameApplicationComponent.Initializer.init(this);
        component.inject(this);
    }

    public GameApplicationComponent getComponent() {
        return component;
    }
}
