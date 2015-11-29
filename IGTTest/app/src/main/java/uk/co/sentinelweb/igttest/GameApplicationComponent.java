package uk.co.sentinelweb.igttest;


import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.sentinelweb.igttest.loader.GameListLoaderComponent;
import uk.co.sentinelweb.igttest.loader.GameListLoaderModule;
import uk.co.sentinelweb.igttest.loader.LoaderScope;
import uk.co.sentinelweb.igttest.net.Services;

/**
 * Component for application.
 * Created by robert on 28/11/15.
 */
@Singleton
@Component(
        modules = {
                GameApplicationModule.class
        }
)
public interface GameApplicationComponent {

    GameApplication provideGameApplication();

    Services provideServices();

    SharedPreferences provideGamePreferences();

    void inject(GameApplication app);

    @LoaderScope
    GameListLoaderComponent plus(GameListLoaderModule gameListLoaderModule);

    final class Initializer {
        private Initializer() {
        } // No instances.

        public static GameApplicationComponent init(GameApplication app) {
            return DaggerGameApplicationComponent.builder()
                    .gameApplicationModule(new GameApplicationModule(app))
                    .build();
        }
    }
}
