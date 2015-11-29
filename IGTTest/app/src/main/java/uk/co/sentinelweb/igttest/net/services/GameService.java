package uk.co.sentinelweb.igttest.net.services;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import uk.co.sentinelweb.igttest.model.GameList;

public interface GameService {

    // obviously user isn't needed for this example it just to show usage
    @GET("nativeapp-test.json")
    Call<GameList> listGames(@Query("user") String user);
}