package uk.co.sentinelweb.igttest.loader;


import android.content.Context;
import android.os.SystemClock;
import android.support.v4.content.AsyncTaskLoader;
import android.text.format.DateUtils;

public abstract class LoaderParent<T> extends AsyncTaskLoader<T> {
    private T data;

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
            return;
        }

        forceLoad();
    }

    @Override
    public void deliverResult(final T data) {
        this.data = data;
        super.deliverResult(data);
    }

}
