package com.example.dfrank.journalapp.journalView;

import android.database.sqlite.SQLiteDatabase;

import com.example.dfrank.journalapp.model.Journal;

public class JournalContract {
    interface Presenter  {
        void start();
        void addJournal(String args);
        void deleteJournal(Journal journal, SQLiteDatabase db);
    }

    interface View  {
        void onDeleteJournal();
        void moveToNextStep();
    }
}
