package com.example.dfrank.journalapp.addJournal;


import android.database.sqlite.SQLiteDatabase;


import com.example.dfrank.journalapp.model.Journal;

public class AddJournalContract {

    interface Presenter  {
        void start();
        void addJournalToDb(SQLiteDatabase db, Journal journal);
        void updateJournal(SQLiteDatabase db, Journal journal);
    }

    interface View  {
        void onJournalInsertedToDb(Journal journal);
        void onJournalUpdated();
        void moveToNextStep();
    }
}
