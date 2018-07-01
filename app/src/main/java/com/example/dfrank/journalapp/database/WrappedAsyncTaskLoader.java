package com.example.dfrank.journalapp.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public abstract class WrappedAsyncTaskLoader<D> extends AsyncTaskLoader<D> {
    private D mData;

    public WrappedAsyncTaskLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    public void deliverResult(@Nullable D data) {
        if (!isReset()) {
            this.mData = data;
            super.deliverResult(data);
        } else {
            // An asynchronous query came in while the loader is stopped
        }
    }

    @Override
    protected void onStartLoading() {
        if (this.mData != null) {
            deliverResult(this.mData);
        } else if (takeContentChanged() || this.mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        this.mData = null;
    }
}
