package com.example.kilkbid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {

    private static final String PREFS_NAME = "kilkbid_prefs";
    private static final String PREF_EMAIL = "pref_email";
    private static final String PREF_PASSWORD_HASH = "pref_password_hash";
    private static final String PREF_REMEMBER = "pref_remember";

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private CheckBox rememberCheckBox;
    private ImageView showPasswordToggle;
    private TextView signupText;

    private AppDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Bind UI
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);
        rememberCheckBox = findViewById(R.id.rememberCheckBox);
        showPasswordToggle = findViewById(R.id.showPasswordToggle);

        // DB
        db = AppDatabase.getInstance(this);
        userDao = db.userDao();

        // Password toggle
        if (showPasswordToggle != null) {
            showPasswordToggle.setOnClickListener(v -> {
                if (passwordInput.getTransformationMethod() != null) {
                    passwordInput.setTransformationMethod(null);
                    showPasswordToggle.setImageResource(R.drawable.ic_eye_open);
                } else {
                    passwordInput.setTransformationMethod(new PasswordTransformationMethod());
                    showPasswordToggle.setImageResource(R.drawable.ic_eye_closed);
                }
                passwordInput.setSelection(passwordInput.length());
            });
        }

        // Login click
        loginButton.setOnClickListener(v -> loginUser());

        // Signup link
        if (signupText != null) {
            signupText.setOnClickListener(v -> {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            });
        }

        // Auto-login if remembered
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean remember = prefs.getBoolean(PREF_REMEMBER, false);
        if (remember) {
            String savedEmail = prefs.getString(PREF_EMAIL, null);
            String savedHash = prefs.getString(PREF_PASSWORD_HASH, null);
            if (savedEmail != null && savedHash != null) {
                emailInput.setText(savedEmail);
                attemptLoginWithHash(savedEmail, savedHash);
            }
            if (rememberCheckBox != null) rememberCheckBox.setChecked(true);
        }
    }

    private void loginUser() {
        final String inputEmail = emailInput.getText().toString().trim();
        final String inputPassword = passwordInput.getText().toString().trim();

        if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // DB access in background
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = userDao.getUserByEmail(inputEmail);

            runOnUiThread(() -> {
                if (user == null) {
                    Toast.makeText(Login.this, "Account can't be found", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Compare hashed password
                String hashedInput = HashUtils.sha256(inputPassword);
                if (hashedInput == null) {
                    Toast.makeText(Login.this, "Error hashing password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (hashedInput.equals(user.passwordHash)) {
                    // Save credentials if remember checked
                    if (rememberCheckBox != null && rememberCheckBox.isChecked()) {
                        saveCredentialsToPrefs(user.email, user.passwordHash);
                    }

                    // Redirect
                    Intent intent;
                    if (user.isAdmin) {
                        intent = new Intent(Login.this, MainActivity.class);
                    } else {
                        intent = new Intent(Login.this, MainActivity.class);
                    }
                    intent.putExtra("user_name", user.name);
                    intent.putExtra("user_email", user.email);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void attemptLoginWithHash(final String email, final String storedHash) {
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = userDao.getUserByEmail(email);

            runOnUiThread(() -> {
                if (user != null && user.passwordHash != null && user.passwordHash.equals(storedHash)) {
                    Toast.makeText(Login.this, "Auto-login successful. Welcome " + user.name, Toast.LENGTH_SHORT).show();
                    Intent intent;
                    if (user.isAdmin) {
                        intent = new Intent(Login.this, MainActivity.class);
                    } else {
                        intent = new Intent(Login.this, MainActivity.class);
                    }
                    intent.putExtra("user_name", user.name);
                    intent.putExtra("user_email", user.email);
                    startActivity(intent);
                    finish();
                } else {
                    clearSavedCredentials();
                }
            });
        });
    }

    private void saveCredentialsToPrefs(String email, String passwordHash) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit()
                .putBoolean(PREF_REMEMBER, true)
                .putString(PREF_EMAIL, email)
                .putString(PREF_PASSWORD_HASH, passwordHash)
                .apply();
    }

    private void clearSavedCredentials() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit()
                .putBoolean(PREF_REMEMBER, false)
                .remove(PREF_EMAIL)
                .remove(PREF_PASSWORD_HASH)
                .apply();
        if (rememberCheckBox != null) rememberCheckBox.setChecked(false);
    }
}
