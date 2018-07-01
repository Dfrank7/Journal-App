package com.example.dfrank.journalapp.journalView;

import android.database.sqlite.SQLiteDatabase;

import com.example.dfrank.journalapp.database.JournalLoader;
import com.example.dfrank.journalapp.model.Journal;

public class JournalPresenter implements JournalContract.Presenter {
    private JournalContract.View view;

    JournalPresenter(JournalContract.View view){
        this.view = view;
    }
    @Override
    public void start() {

    }

    @Override
    public void addJournal(String args) {

    }

    @Override
    public void deleteJournal(Journal journal, SQLiteDatabase db) {
        JournalLoader.deleteJournal(journal, db);
    }
}
