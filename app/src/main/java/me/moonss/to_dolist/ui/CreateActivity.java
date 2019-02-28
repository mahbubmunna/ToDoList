package me.moonss.to_dolist.ui;

import androidx.appcompat.app.AppCompatActivity;
import me.moonss.to_dolist.R;
import me.moonss.to_dolist.databinding.ActivityCreateBinding;
import me.moonss.to_dolist.service.model.Note;
import me.moonss.to_dolist.service.repository.NoteRepo;
import me.moonss.to_dolist.utilites.FirebaseUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {
    private ActivityCreateBinding mBinding;
    private EditText editText;

    public static Intent get(Context context) {
        return new Intent(context, CreateActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        editText = findViewById(R.id.edit_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_accept, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_accept:
                save();
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {

        String noteValue = editText.getText().toString();
        Note note = new Note(noteValue);
        String userEmail = FirebaseUtils.getUser().getEmail();
        NoteRepo.getInstance().addNote(userEmail, note);
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT)
                .show();
    }
}
