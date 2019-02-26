package me.moonss.to_dolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import me.moonss.to_dolist.databinding.ActivityMainBinding;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 100; //code for checking right onActivityResult
    private ActivityMainBinding mBinding;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireSignUp();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
                        .setTheme(R.style.AppTheme)
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
                user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(
                        this,
                        "Logged in, user: " +user.getDisplayName(),
                        Toast.LENGTH_SHORT)
                        .show();

            }
        }
    }
}
