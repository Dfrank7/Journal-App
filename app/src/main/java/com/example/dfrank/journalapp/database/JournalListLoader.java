package com.example.dfrank.journalapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.dfrank.journalapp.model.Journal;

import java.util.ArrayList;

public class JournalListLoader extends WrappedAsyncTaskLoader<ArrayList<Journal>> {
    String selection;

    public JournalListLoader(@NonNull Context context) {
        super(context);
    }

    public JournalListLoader(Context context, String selection){
        super(context);
        this.selection = selection;

    }


    @Nullable
    @Override
    public ArrayList<Journal> loadInBackground() {
        JournalDBHelper dbHelper = new JournalDBHelper(getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ArrayList<Journal> journals = dbHelper.getJournalList(database);
        database.close();
        return journals;
    }
}
