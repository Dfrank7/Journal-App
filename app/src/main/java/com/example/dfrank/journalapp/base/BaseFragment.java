package com.example.dfrank.journalapp.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class BaseFragment extends Fragment implements BaseContract.view {

    public ProgressDialog progressDialog;

    public void setToolbarTitle (String title){
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(title);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public void showloading(String message) {
        //if progress dialog is not being used or showing
        if (progressDialog == null){
            progressDialog.setMessage(message);
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        //if progress dialog is showing
        if (progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
