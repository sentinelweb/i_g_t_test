package uk.co.sentinelweb.igttest;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Inject;

import uk.co.sentinelweb.igttest.model.GameList;
import uk.co.sentinelweb.igttest.model.dummy.DummyGameContent;

/**
 * Created by robert on 28/11/15.
 */
public class GameApplication extends Application {
    // TODO remove
    //public static GameList testGameList = DummyGameContent.generate();
    public static GameApplication staticInstance = null;
    @Inject
    SharedPreferences gamePreferences;

    GameApplicationComponent component;

    @Inject public GameApplication() {
        staticInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = GameApplicationComponent.Initializer.init(this);
        component.inject(this);
    }

    public static GameApplication getStaticInstance() {
        return staticInstance;
    }

    public GameApplicationComponent getComponent() {
        return component;
    }
}
