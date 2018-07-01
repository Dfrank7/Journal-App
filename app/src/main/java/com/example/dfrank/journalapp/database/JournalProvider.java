package com.example.dfrank.journalapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class JournalProvider extends ContentProvider {

    JournalDBHelper journalDBHelper;
    Context context;

    public JournalProvider() {

    }

    private static final int JOURNAL = 100;
    private static final int JOURNAL_WITH_ID = 101;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(JournalDBContract.Authority, JournalDBContract.PATH_JOURNALS, JOURNAL);
        uriMatcher.addURI(JournalDBContract.Authority, JournalDBContract.PATH_JOURNAL_WITH_ID, JOURNAL_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        journalDBHelper = new JournalDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = journalDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int id = 0;
        switch (match) {
            case JOURNAL:
                id = database.delete(JournalDBContract.JournalEntry.TABLE_NAME, selection, selectionArgs);
                if (id != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
            case JOURNAL_WITH_ID:
                selection = JournalDBContract.JournalEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                id = database.delete(JournalDBContract.JournalEntry.TABLE_NAME, selection, selectionArgs);
                if (id != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
        }
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
