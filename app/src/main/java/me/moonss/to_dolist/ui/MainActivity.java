package me.moonss.to_dolist.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.moonss.to_dolist.R;
import me.moonss.to_dolist.databinding.ActivityMainBinding;
import me.moonss.to_dolist.service.model.Note;
import me.moonss.to_dolist.ui.adapters.ListNotesAdapter;
import me.moonss.to_dolist.utilites.FirebaseUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SWIPE_RIGHT = 8;
    private static final int SWIPE_LEFT = 4;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ActivityMainBinding mBinding;
    private ListNotesAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FireUp the Login Activity if user is not signed in
        if (FirebaseUtils.getUser() == null) startActivity(LoginActivity.get(this));

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar( mBinding.toolbar);

        setupRecycler();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.action_logout) {
            FirebaseUtils.signOut(this);
            startActivity(LoginActivity.get(this));
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecycler() {
        String userMail = FirebaseUtils.getUser().getEmail();
        Query query =
                db.collection("users").
                        document(userMail).
                        collection("notes");
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .setLifecycleOwner(this)
                .build();

        mAdapter = new ListNotesAdapter(options);;
        mBinding.mainContent.recycler.setHasFixedSize(true);
        mBinding.mainContent.recycler.
                setLayoutManager(new LinearLayoutManager(this));
        mBinding.mainContent.recycler.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == SWIPE_RIGHT) {
                    mAdapter.updateAsCompleted(viewHolder.getAdapterPosition());
                } else if (direction == SWIPE_LEFT) {
                    mAdapter.deleteNote(viewHolder.getAdapterPosition());
                }


            }
        }).attachToRecyclerView(mBinding.mainContent.recycler);
    }

    public void startCreateActivity(View view) {
        startActivity(CreateActivity.get(MainActivity.this));
    }

}
