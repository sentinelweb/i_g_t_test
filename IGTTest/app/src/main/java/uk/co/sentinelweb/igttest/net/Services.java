package uk.co.sentinelweb.igttest.net;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import uk.co.sentinelweb.igttest.Const;
import uk.co.sentinelweb.igttest.net.services.GameService;

/**
 * This will be instantiated as a singleton by dagger.
 * <p/>
 * This manages the retrofit instance and request cache.
 * <p/>
 * And can bue used o build retrofit service instances. (e.g. {@link #getGameService()})
 * Created by robert on 28/11/15.
 */
public class Services {

    public static final String BASE_URL = "https://dl.dropboxusercontent.com/u/49130683/";
    public static final long CACHE_TIME_SECONDS = DateUtils.HOUR_IN_MILLIS / 1000;

    private static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB
    private Cache cache = null;
    private Retrofit retroFit = null;

    ConnectivityManager connectivityManager = null;

    /**
     * Builds a retrofit instance without a cache
     */
    public Services() {
        retroFit = buildRetrofit();
    }

    /**
     * Builds a retrofit instance with a cache
     */
    public Services(Application app) {
        buildCache(app);
        connectivityManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        retroFit = buildRetrofit();
    }

    /**
     * Our games service
     *
     * @return service to call the game list API
     */
    public GameService getGameService() {
        return retroFit.create(GameService.class);
    }

    /**
     * Builds the retrofit instance
     * TODO setup executors
     *
     * @return retrofit instance.
     */
    @NonNull
    private Retrofit buildRetrofit() {
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        if (cache != null) {
            builder.client(buildClient(cache));
        }

        return builder.build();
    }

    /**
     * Creates the cache on the apps external (internal) sd card.
     *
     * @param c context
     */
    private void buildCache(Context c) {
        // Create Cache
        if (cache == null) {
            try {
                cache = new Cache(new File(c.getExternalCacheDir(), "http"), SIZE_OF_CACHE);
            } catch (Exception e) {
                Log.e(Const.LOG, "Could not create Cache!", e);
            }
        }
    }

    /**
     * Builds the okHttp client to use in retrofit.
     *
     * @param cache the cache to use
     * @return client
     */
    private OkHttpClient buildClient(Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.interceptors().add(interceptor);

        if (cache != null) {
            okHttpClient.setCache(cache);
        }
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);

        // Add cache control headers to the response so okHttp can cache it
        okHttpClient.networkInterceptors().add(cacheControlInterceptor);

        return okHttpClient;
    }

    /**
     * This network interceptor re-writes the response headers so okhttp can cache responses.
     * if there is not connectivity we rebuild the request with {@link CacheControl#FORCE_CACHE}
     * It would not be needed if the server headers were set properly.
     */
    private final Interceptor cacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            if (!isConnected(connectivityManager)) {
                // this rebuilds the request to force loading from cache.
                // rebuild the request adding
                Request originalRequest = chain.request();
                Request cacheRequest = new Request.Builder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .headers(originalRequest.headers())
                        .method(originalRequest.method(), originalRequest.body())
                        .url(originalRequest.url())
                        .build();
                return chain.proceed(cacheRequest);
            } else {
                // this rewrites the network response to add cache control headers
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .header("Cache-Control", String.format("private,  max-age=%d", CACHE_TIME_SECONDS))
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * Test for connectivity.
     *
     * @return is connected
     */
    private boolean isConnected(ConnectivityManager cm) {
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

}
