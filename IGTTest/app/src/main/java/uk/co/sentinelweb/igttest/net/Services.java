package uk.co.sentinelweb.igttest.net;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import uk.co.sentinelweb.igttest.Const;
import uk.co.sentinelweb.igttest.GameApplication;
import uk.co.sentinelweb.igttest.net.services.GameService;

/**
 * This will be instantiated as a singleton by dagger
 * Created by robert on 28/11/15.
 */
public class Services {

    public static final String BASE_URL = "https://dl.dropboxusercontent.com/u/49130683/";
    private static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB
    public static final long CACHE_TIME_SECONDS = DateUtils.HOUR_IN_MILLIS / 1000;

    private Cache cache = null;
    private Retrofit retroFit = null;

    public Services() {
        retroFit = buildRetrofit();
    }

    public Services(Application app) {
        buildCache(app);
        retroFit = buildRetrofit();
    }

    public GameService getGameService() {
        return retroFit.create(GameService.class);
    }

    @NonNull
    private Retrofit buildRetrofit() {
        // TODO setup executors
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        if (cache != null) {
            builder.client(buildClient(cache));
        }

        return builder.build();
    }

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

    private OkHttpClient buildClient(Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.interceptors().add(interceptor);

        if (cache != null) okHttpClient.setCache(cache);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);

        // This will add cache control header to the response so okHttp can cache it
        okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);

        return okHttpClient;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                   // .header("Cache-Control", String.format("public, only-if-cached, max-age=%d, max-stale=%d", CACHE_TIME_SECONDS,  0))
                    .header("Cache-Control", String.format("public,  max-age=%d", CACHE_TIME_SECONDS))
                    .removeHeader("Pragma")
                    .removeHeader("pragma")
                    .build();
        }
    };

}
