package com.example.dfrank.journalapp.adapter;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.dfrank.journalapp.MainActivity;
import com.example.dfrank.journalapp.R;
import com.example.dfrank.journalapp.addJournal.AddJournalFragment;
import com.example.dfrank.journalapp.model.Journal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.viewHolder> {
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable textDrawable;

    ArrayList<Journal> journalArrayList;

    public JournalListAdapter(ArrayList<Journal> journalArrayList) {
        this.journalArrayList = journalArrayList;
    }

    @Override
    public JournalListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journalcard, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(JournalListAdapter.viewHolder holder, int position) {
        String title = journalArrayList.get(position).journalTitle;
        String feeling = journalArrayList.get(position).journalFeeling;
        String thought = journalArrayList.get(position).journalThoughts;
        holder.itemView.setTag(journalArrayList.get(position).journaId);

        setThoughtTitle(title, holder.CircularImage);
        holder.JournalTitle.setText(title);
        holder.JournalThougt.setText(thought);
    }

    @Override
    public int getItemCount() {
        return journalArrayList.size();
    }

    public void updateData(ArrayList<Journal> data) {
        journalArrayList = data;
        notifyDataSetChanged();
    }

    public void setThoughtTitle(String title, ImageView imageView) {
        String letter = "A";

        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();

        // Create a circular icon consisting of  a random background colour and first letter of title
        textDrawable = TextDrawable.builder()
                .buildRound(letter, color);
        imageView.setImageDrawable(textDrawable);
    }

//    public Journal getJournal(){
//        return journalArrayList.get(getAdapterPosition());
//    }

    public class viewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.thumbnail_image)
        ImageView CircularImage;
        @BindView(R.id.recycle_title)
        TextView JournalTitle;
        @BindView(R.id.recycle_thought) TextView JournalThougt;
        public viewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Journal journal = journalArrayList.get(position);
                    FragmentTransaction transaction = ((MainActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, AddJournalFragment.newInstance(journal));
                    transaction.addToBackStack(null).commit();
                }
            });
        }
    }
}
