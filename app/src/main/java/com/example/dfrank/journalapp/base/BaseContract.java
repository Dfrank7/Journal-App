package com.example.dfrank.journalapp.base;

public class BaseContract {

    interface view{
        void showloading(String message);
        void showToast(String message);
        void hideLoading();
    }
}
