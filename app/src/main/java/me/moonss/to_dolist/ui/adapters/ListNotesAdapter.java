package me.moonss.to_dolist.ui.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.moonss.to_dolist.databinding.ItemNoteBinding;
import me.moonss.to_dolist.service.model.Note;

public class ListNotesAdapter
        extends FirestoreRecyclerAdapter<Note, ListNotesAdapter.NotesViewHolder> {
    private static final String TAG = ListNotesAdapter.class.getSimpleName();

    public ListNotesAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemNoteBinding noteBinding =
                ItemNoteBinding.inflate(inflater, parent, false);
        return new NotesViewHolder(noteBinding);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull NotesViewHolder holder, int position, @NonNull Note note) {
        holder.bind(note);
    }

    //Method to delete a note from the FireUIRecycler
    public void deleteNote(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    //Method to update a note as completed
    public void updateAsCompleted(int position) {
        getSnapshots().getSnapshot(position).getReference().update("finished", true);
    }


    class NotesViewHolder extends RecyclerView.ViewHolder {
        private final ItemNoteBinding mItemBinding;

        public NotesViewHolder(ItemNoteBinding itemBinding) {
            super(itemBinding.getRoot());
            mItemBinding = itemBinding;
        }

        //helper method bind views for onBindViewHolder
        void bind(Note note) {
            mItemBinding.noteText.setText(note.getNote());
            if (note.isFinished()) {
                mItemBinding.noteText.setPaintFlags(
                        mItemBinding.noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

    }
}
