package uk.co.sentinelweb.igttest.loader;

import dagger.Subcomponent;
import uk.co.sentinelweb.igttest.GameApplication;
import uk.co.sentinelweb.igttest.net.services.GameService;

/**
 * Created by robert on 28/11/15.
 */
@LoaderScope
@Subcomponent(
        modules = {
                GameListLoaderModule.class
        }
)
public interface GameListLoaderComponent {

    public GameService provideGameService();

    public void inject(GameListLoader loader);

    final class Initializer {
        private Initializer() {
        } // No instances.

        public static void inject(GameApplication app, GameListLoader loader) {
            app.getComponent().plus(new GameListLoaderModule()).inject(loader);
        }
    }
}
