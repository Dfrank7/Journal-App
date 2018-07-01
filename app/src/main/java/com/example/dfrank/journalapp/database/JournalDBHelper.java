package com.example.dfrank.journalapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dfrank.journalapp.model.Journal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JournalDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "journal.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + JournalDBContract.JournalEntry.TABLE_NAME + " (" +
                    JournalDBContract.JournalEntry._ID + " INTEGER PRIMARY KEY," +
                    JournalDBContract.JournalEntry.COLUMN_JOURNAL_NAME + " TEXT," +
                    JournalDBContract.JournalEntry.COLUMN_JOURNAL_THOUGHT + " TEXT," +
                    JournalDBContract.JournalEntry.COLOMN_JOURNAL_FEELING + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + JournalDBContract.JournalEntry.TABLE_NAME;


    public JournalDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        //addDemo(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

//    private void addDemo(SQLiteDatabase db){
//        db.beginTransaction();
//        try {
//            addJournal(db);
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//        }
//    }

//    public void addJournal(SQLiteDatabase database) {
//        database = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(JournalDBContract.JournalEntry.COLUMN_JOURNAL_NAME, "Testing");
//        values.put(JournalDBContract.JournalEntry.COLUMN_JOURNAL_THOUGHT, "I am doing a demo");
//        values.put(JournalDBContract.JournalEntry.COLOMN_JOURNAL_FEELING, "My mood is good bro");
//        database.insert(JournalDBContract.JournalEntry.TABLE_NAME, null, values);
//    }

    public ArrayList<Journal> getJournalList(SQLiteDatabase db){
        return getAllJournalsFromCursor(getJournalCursor(db));
    }

    public static Cursor getJournalCursor(SQLiteDatabase db){
        // array of columns to fetch
        String[] columns = {
                JournalDBContract.JournalEntry._ID,
                JournalDBContract.JournalEntry.COLUMN_JOURNAL_NAME,
                JournalDBContract.JournalEntry.COLUMN_JOURNAL_THOUGHT,
                JournalDBContract.JournalEntry.COLOMN_JOURNAL_FEELING
        };
        Cursor cursor = db.query(JournalDBContract.JournalEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        return cursor;
    }

    public static ArrayList<Journal> getAllJournalsFromCursor(Cursor cursor) {

        ArrayList<Journal> journalArrayList = new ArrayList<>();
        // Traversing through all rows and adding to list
        if (cursor!=null) {

                int idColumn = cursor.getColumnIndex(JournalDBContract.JournalEntry._ID);
                int titleColumn = cursor.getColumnIndex(JournalDBContract.JournalEntry.COLUMN_JOURNAL_NAME);
                int feelingColumn = cursor.getColumnIndex(JournalDBContract.JournalEntry.COLOMN_JOURNAL_FEELING);
                int thoughtColumn = cursor.getColumnIndex(JournalDBContract.JournalEntry.COLUMN_JOURNAL_THOUGHT);

             while (cursor.moveToNext()) {

                journalArrayList.add(new Journal(cursor.getLong(idColumn),
                        cursor.getString(titleColumn),
                        cursor.getString(thoughtColumn),
                        cursor.getString(feelingColumn)));
            }
        }
        cursor.close();

        // return journal list
        return journalArrayList;
    }
}
