package com.example.kilkbid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private EditText etFullName, etEmail, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private TextView tvLoginLink;
    private AppDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // Bind UI
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        // Init DB
        db = AppDatabase.getInstance(this);
        userDao = db.userDao();

        // Button listeners
        btnSignUp.setOnClickListener(v -> handleSignUp());

        // Login link
        tvLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void handleSignUp() {
        String name = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidationUtils.isValidName(name)) {
            Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidationUtils.isValidEmail(email)) {
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidationUtils.isValidPassword(password)) {
            Toast.makeText(this, "Password too weak", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            User existingUser = userDao.getUserByEmail(email);

            if (existingUser != null) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show());
            } else {
                User newUser = new User();
                newUser.name = name;
                newUser.email = email;

                // Save hashed password instead of plain text
                newUser.passwordHash = HashUtils.sha256(password);

                userDao.insertUser(newUser);

                runOnUiThread(() -> {
                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    finish(); // go back to login
                });
            }
        }).start();
    }
}
