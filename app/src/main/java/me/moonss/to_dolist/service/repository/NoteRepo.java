package me.moonss.to_dolist.service.repository;


import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import me.moonss.to_dolist.service.model.Note;

public class NoteRepo {
    private static NoteRepo ourInstance;
    CollectionReference mRef;
    private static final Object LOCK = new Object();
    private static final String TAG = NoteRepo.class.getSimpleName();

    public static NoteRepo getInstance() {
        if (ourInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Note Repo Created");
                ourInstance = new NoteRepo();
            }
        }
        return ourInstance;
    }

    private NoteRepo() {
        mRef = FirebaseFirestore.getInstance().collection("users");
    }

    public void addNote(String user, Note note) {
        mRef.document(user).collection("notes").add(note);
    }
}
