package com.example.dfrank.journalapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.dfrank.journalapp.model.Journal;

import java.util.ArrayList;

public class JournalLoader {

    public static String[] projection = {
            BaseColumns._ID,
            JournalDBContract.JournalEntry.COLUMN_JOURNAL_NAME,
            JournalDBContract.JournalEntry.COLUMN_JOURNAL_THOUGHT,
            JournalDBContract.JournalEntry.COLOMN_JOURNAL_FEELING
    };

    public static Journal getInfoJournalWithId(int rowId, SQLiteDatabase db){
        String selection = JournalDBContract.JournalEntry._ID + " = " + String.valueOf(rowId);
        return getAllJournalsFromCursor(getJournalCursor(selection,db)).get(0);
    }

    public static int updateJournal( Journal journal, SQLiteDatabase db) {
        ContentValues contentValues = JournalLoader.getContentValuesForJournal(journal);
        String selection = JournalDBContract.JournalEntry._ID + " = " + String.valueOf(journal.journaId);
        return db.update(
                JournalDBContract.JournalEntry.TABLE_NAME,
                contentValues,
                selection,
                null);
    }

    public static ContentValues getContentValuesForJournal(Journal journal) {
        ContentValues values = new ContentValues();
        values.put(JournalDBContract.JournalEntry.COLUMN_JOURNAL_NAME, journal.journalTitle);
        values.put(JournalDBContract.JournalEntry.COLUMN_JOURNAL_THOUGHT, journal.journalThoughts);
        values.put(JournalDBContract.JournalEntry.COLOMN_JOURNAL_FEELING, journal.journalFeeling);;
        return values;
    }

    public static long addJournal(Journal journal, SQLiteDatabase db) {
        ContentValues values = JournalLoader.getContentValuesForJournal(journal);
        return db.insert(JournalDBContract.JournalEntry.TABLE_NAME, null, values);
    }


    public static long deleteJournal(Journal journal, SQLiteDatabase db){
        String selection = JournalDBContract.JournalEntry._ID + " = " + String.valueOf(journal.journaId);
        return db.delete(JournalDBContract.JournalEntry.TABLE_NAME,
                selection,
                null);
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

                journalArrayList.add(new Journal(cursor.getInt(idColumn),
                        cursor.getString(titleColumn),
                        cursor.getString(thoughtColumn),
                        cursor.getString(feelingColumn)));
            }
        }
        cursor.close();

        // return journal list
        return journalArrayList;
    }

    public static Cursor getJournalCursor(String selection,SQLiteDatabase db){
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
}
