package uk.co.sentinelweb.igttest.loader;

import android.app.Application;
import android.content.Context;


import javax.inject.Inject;

import retrofit.Call;
import retrofit.Retrofit;
import uk.co.sentinelweb.igttest.GameApplication;
import uk.co.sentinelweb.igttest.model.GameList;
import uk.co.sentinelweb.igttest.net.Services;
import uk.co.sentinelweb.igttest.net.services.GameService;
import uk.co.sentinelweb.igttest.net.services.ServiceCall;

/**
 * Created by robert on 28/11/15.
 */
public class GameListLoader extends LoaderParent<GameList>{

    @Inject
    GameService gameService;
    /**
     * Get the list of games.
     * @param context used to retrieve the application context.
     */
    public GameListLoader(final Context context) {
        super(context);
        GameListLoaderComponent.Initializer.inject((GameApplication) context.getApplicationContext(), this);
    }

    @Override
    public GameList loadInBackground() {
        final Call<GameList> gameListCall = gameService.listGames(null);

        return new ServiceCall<GameList>().call(gameListCall);
    }


}
