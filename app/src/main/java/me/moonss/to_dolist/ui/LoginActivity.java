package me.moonss.to_dolist.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.moonss.to_dolist.R;
import me.moonss.to_dolist.utilites.FirebaseUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100; //code for checking right onActivityResult

    public static Intent get(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireSignUp();

    }

    //Start Login Activity (Firebase AuthUI)
    private void fireSignUp() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder()
                        .build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .setLogo(R.drawable.to_d0_list_black_144dp)
                        .build(),
                RC_SIGN_IN);
    }


    //Getting Login information from AuthUi Login
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                String user  = FirebaseUtils.getUser().getDisplayName();
                Toast.makeText(
                        getApplicationContext(),
                        "Logged in, user: " +user,
                        Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        }
    }
}
