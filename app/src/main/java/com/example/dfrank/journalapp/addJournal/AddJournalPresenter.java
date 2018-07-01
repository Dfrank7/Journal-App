package com.example.dfrank.journalapp.addJournal;

import android.database.sqlite.SQLiteDatabase;

import com.example.dfrank.journalapp.database.JournalLoader;
import com.example.dfrank.journalapp.model.Journal;

public class AddJournalPresenter implements AddJournalContract.Presenter {

    private AddJournalContract.View view;
    private static final String TAG = AddJournalPresenter.class.getSimpleName();

    AddJournalPresenter(AddJournalContract.View view) {
        this.view = view;
    }
    @Override
    public void start() {

    }

    @Override
    public void addJournalToDb(SQLiteDatabase db, Journal journal) {
        journal.journaId = JournalLoader.addJournal(journal,db);
        view.onJournalInsertedToDb(journal);
    }

    @Override
    public void updateJournal(SQLiteDatabase db, Journal journal) {
        JournalLoader.updateJournal(journal, db);
        view.onJournalUpdated();
    }


}
