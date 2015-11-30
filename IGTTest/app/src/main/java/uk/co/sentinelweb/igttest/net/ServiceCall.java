package uk.co.sentinelweb.igttest.net;

import android.util.Log;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;
import uk.co.sentinelweb.igttest.Const;

/**
 * Executes a service call.
 * <p/>
 * Extra error handling can be added here.
 * Created by robert on 28/11/15.
 */
public class ServiceCall<T> {

    public T call(Call<T> call) {
        T result = null;
        try {
            final Response<T> response = call.execute();
            result = response.body();
        } catch (IOException e) {
            Log.e(Const.LOG, "Service call failed.", e);
        }
        return result;
    }

}
