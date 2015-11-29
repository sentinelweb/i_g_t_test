package uk.co.sentinelweb.igttest.loader;

import dagger.Module;
import dagger.Provides;
import uk.co.sentinelweb.igttest.net.Services;
import uk.co.sentinelweb.igttest.net.services.GameService;

/**
 * Created by robert on 28/11/15.
 */
@LoaderScope
@Module
public class GameListLoaderModule {
    public GameListLoaderModule() {
    }

    @Provides
    @LoaderScope
    public GameService provideGameService(Services services) {
        return services.getGameService();
    }
}
