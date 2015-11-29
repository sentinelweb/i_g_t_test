package uk.co.sentinelweb.igttest.loader;


import android.content.Context;
import android.os.SystemClock;
import android.support.v4.content.AsyncTaskLoader;
import android.text.format.DateUtils;

public abstract class LoaderParent<T> extends AsyncTaskLoader<T> {
    private T data;
    private long lastLoaded = 0;

    public LoaderParent(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
            return;
        }

        if (data != null) {
            deliverResult(data);
            lastLoaded = SystemClock.uptimeMillis();
            return;
        }

        forceLoad();
    }

    @Override
    public void deliverResult(final T data) {
        this.data = data;
        super.deliverResult(data);
    }

    public void deliverCachedResult() {
        if (data != null) {
            super.deliverResult(data);
        } else {
            throw new RuntimeException("No data cached");
        }
    }

    public boolean isExpired(final int sec) {
        return SystemClock.uptimeMillis() > lastLoaded + sec * DateUtils.SECOND_IN_MILLIS;
    }
}
