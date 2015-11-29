package uk.co.sentinelweb.igttest.net.services;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

/**
 * Executes a service call
 * Created by robert on 28/11/15.
 */
public class ServiceCall<T> {
    public T call(Call<T> call) {
        T gameList = null;
        try {
            final Response<T> response = call.execute();
            gameList = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameList;
    }
}
