package com.example.dfrank.journalapp.journalView;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dfrank.journalapp.MainActivity;
import com.example.dfrank.journalapp.R;
import com.example.dfrank.journalapp.adapter.JournalListAdapter;
import com.example.dfrank.journalapp.addJournal.AddJournalContract;
import com.example.dfrank.journalapp.addJournal.AddJournalFragment;
import com.example.dfrank.journalapp.base.BaseFragment;
import com.example.dfrank.journalapp.database.JournalDBContract;
import com.example.dfrank.journalapp.database.JournalDBHelper;
import com.example.dfrank.journalapp.model.Journal;
import com.example.dfrank.journalapp.database.JournalListLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JournalFragment extends BaseFragment implements JournalContract.View,
        LoaderManager.LoaderCallbacks<ArrayList<Journal>>{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private static final int JOURNAL_LOADER_ID = 0;

    private static String TAG = JournalFragment.class.getSimpleName();
    private JournalListAdapter journalListAdapter;

    public JournalFragment(){
    }
    ArrayList<Journal> journals;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(JOURNAL_LOADER_ID, null,this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_journal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setDrawerIconToHome();

        journalListAdapter = new JournalListAdapter(new ArrayList<Journal>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setAdapter(journalListAdapter);
        getLoaderManager().restartLoader(JOURNAL_LOADER_ID, null, this);

        //Implementing swipe for delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long position = (long)viewHolder.itemView.getTag();
                //Journal journal = journals.get((int) position);
                String stringId = Long.toString(position);
                Uri uri = JournalDBContract.JournalEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();
//                journals.remove(journal);
//                JournalDBHelper dbHelper = new JournalDBHelper(getContext());
//                SQLiteDatabase database = dbHelper.getWritableDatabase();
//                presenter.deleteJournal(journal, database);
                //deleting journal

                getContext().getContentResolver().delete(uri, null, null);
                reloadList();
            }


        }).attachToRecyclerView(recyclerView);
    }

    void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @OnClick(R.id.fab)
    void onClickFab() {
        navigateToFragment(AddJournalFragment.newInstance(null));
    }


    @Override
    public void onDeleteJournal() {
        getLoaderManager().restartLoader(JOURNAL_LOADER_ID, null, this);
    }

    @Override
    public void moveToNextStep() {

    }

    void reloadList(){
        getLoaderManager().restartLoader(JOURNAL_LOADER_ID,null,this);
    }

    @Override
    public Loader<ArrayList<Journal>> onCreateLoader(int id, Bundle args) {
        return new JournalListLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Journal>> loader, ArrayList<Journal> data) {
        journalListAdapter.updateData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Journal>> loader) {

    }

}
