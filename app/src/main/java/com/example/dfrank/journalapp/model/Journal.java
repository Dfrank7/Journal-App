package com.example.dfrank.journalapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Journal implements Parcelable {
    public String journalTitle;
    public String journalThoughts;
    public String journalFeeling;
    public long journaId;

    public Journal( long journaId,String journalTitle, String journalThoughts, String journalFeeling) {
        this.journaId = journaId;
        this.journalTitle = journalTitle;
        this.journalThoughts = journalThoughts;
        this.journalFeeling = journalFeeling;
    }

    public Journal(String journalTitle, String journalThoughts, String journalFeeling) {
        this.journalTitle = journalTitle;
        this.journalThoughts = journalThoughts;
        this.journalFeeling = journalFeeling;
    }


    protected Journal(Parcel in){
        journaId = in.readInt();
        journalTitle = in.readString();
        journalThoughts = in.readString();
        journalFeeling = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(journaId);
        dest.writeString(journalTitle);
        dest.writeString(journalThoughts);
        dest.writeString(journalFeeling);
    }

    public static final Creator<Journal> CREATOR = new Creator<Journal>() {
        @Override
        public Journal createFromParcel(Parcel in) {
            return new Journal(in);
        }

        @Override
        public Journal[] newArray(int size) {
            return new Journal[size];
        }
    };

    public long getJournaId() {
        return journaId;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public String getJournalThoughts() {
        return journalThoughts;
    }

    public String getJournalFeeling() {
        return journalFeeling;
    }

    public static Creator<Journal> getCREATOR() {
        return CREATOR;
    }


}
