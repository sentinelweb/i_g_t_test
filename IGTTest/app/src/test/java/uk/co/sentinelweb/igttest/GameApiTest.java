package uk.co.sentinelweb.igttest;

import android.test.suitebuilder.annotation.LargeTest;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;
import uk.co.sentinelweb.igttest.model.GameList;
import uk.co.sentinelweb.igttest.net.Services;
import uk.co.sentinelweb.igttest.net.services.GameService;

/**
 * Created by robert on 28/11/15.
 */
public class GameApiTest {

    @Test
    @LargeTest
    public void shouldLoadGames() {
        final GameService gameService = new Services().getGameService();
        final Call<GameList> gameListCall = gameService.listGames("john");
        GameList johnsGames = null;
        try {
            final Response<GameList> response = gameListCall.execute();
            johnsGames = response.body();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertTrue("call failed:"+e.getMessage(), false);
        }
        Assert.assertNotNull("No response",johnsGames);
        Assert.assertNotNull("No games list",johnsGames.getGames());
        Assert.assertTrue("Zero games", johnsGames.getGames().size()>0);
    }

}
