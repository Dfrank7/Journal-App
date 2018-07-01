package com.example.dfrank.journalapp.addJournal;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dfrank.journalapp.MainActivity;
import com.example.dfrank.journalapp.R;
import com.example.dfrank.journalapp.base.BaseFragment;
import com.example.dfrank.journalapp.database.JournalDBHelper;
import com.example.dfrank.journalapp.model.Journal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddJournalFragment extends BaseFragment implements AddJournalContract.View {
    AddJournalContract.Presenter presenter;
    private static String TAG = AddJournalFragment.class.getSimpleName();
    @BindView(R.id.journal_title) EditText editTextTitle;
    @BindView(R.id.thought) EditText editTextThought;
    @BindView(R.id.feelings) EditText editTextFeeling;
    @BindView(R.id.saveButton)
    Button saveButton;
    private Journal journalToChange;

    public static AddJournalFragment newInstance(Journal journal) {
        Bundle args = new Bundle();
        args.putParcelable("journ_object", journal);
        AddJournalFragment fragment = new AddJournalFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_journal_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((MainActivity) getActivity()).setDrawerIconToHome();
        presenter = new AddJournalPresenter(this);
        final Journal journal = getArguments().getParcelable("journ_object");

        if (getArguments() != null && journal != null) {
            //We want to edit journal
            setToolbarTitle("Edit Journal");
            journalToChange = journal;
            //prepopulate the fields with the details of the journal to edit
            editTextTitle.setText(journal.journalTitle);
            editTextThought.setText(journal.journalThoughts);
            editTextFeeling.setText(journal.journalFeeling);
        } else {
            // We want to add new journal
            setToolbarTitle("Add new Journal");
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEditTexts(editTextTitle)){
                    showToast("Please Enter Journal Title");
                    return;
                }
                if (!validateEditTexts(editTextThought)){
                    showToast("Please Enter Journal Thought");
                    return;
                }
                if (!validateEditTexts(editTextFeeling)){
                    showToast("Please Enter Journal Feeling");
                    return;
                }
                buttonAction();
            }
        });
    }

    private void buttonAction(){
        Journal journal = new Journal(editTextTitle.getText().toString(),
                editTextThought.getText().toString(),
                editTextFeeling.getText().toString());
        JournalDBHelper dbHelper = new JournalDBHelper(getContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if (journalToChange!=null){
            journal.journaId = journalToChange.journaId;
            presenter.updateJournal(database, journal);
        }else {
            presenter.addJournalToDb(database, journal);
        }
        database.close();
    }


    @Override
    public void onJournalInsertedToDb(Journal journal) {
        Log.d(TAG, "OnJournal inserted " + journal);
        Toast.makeText(getContext(), "Journal : " + journal.journalTitle +
                " added", Toast.LENGTH_SHORT).show();
        moveToNextStep();
    }

    @Override
    public void onJournalUpdated() {
        Toast.makeText(getContext(), "Journal updated", Toast.LENGTH_SHORT).show();
        moveToNextStep();
    }

    @Override
    public void moveToNextStep() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public static boolean validateEditTexts(EditText... editTexts) {
        for (EditText newtext : editTexts) {
            if (newtext.getText().toString().trim().length() < 1) {
                newtext.setError("This Form is required");
                return false;
            }
        }
        return true;
    }
}
