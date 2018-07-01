package com.example.dfrank.journalapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class JournalDBContract {

    private static final String Content = "content://";
    public static final String Authority = "com.example.dfrank.journalapp";
    public static final String PATH_JOURNALS = "journal";
    public static final String PATH_JOURNAL_WITH_ID = "journal/#";
    public static final Uri Base_Content_Uri = Uri.parse(Content + Authority);

    private JournalDBContract() {}

    public static class JournalEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Base_Content_Uri.buildUpon()
                .appendPath(PATH_JOURNALS).build();

        public static final String TABLE_NAME = "journal";
        public static final String COLUMN_JOURNAL_NAME = "journal_name";
        public static final String COLUMN_JOURNAL_THOUGHT = "journal_thought";
        public static final String COLOMN_JOURNAL_FEELING = "journal_feeling";
    }
}
