package com.unex.agrologistics.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.unex.agrologistics.DrawerActivity;
import com.unex.agrologistics.R;
import com.unex.agrologistics.data.repository.LogisticCenterRepository;
import com.unex.agrologistics.data.repository.ConsumerRepository;
import com.unex.agrologistics.data.repository.ProductRepository;
import com.unex.agrologistics.ui.signup.SignUpActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // Edit Text for log in activity
    private EditText editTextEmail;
    private EditText editTextPassword;

    // Producer view model
    private ConsumersViewModel consumersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set content view
        setContentView(R.layout.activity_login);

        // Define the text views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        // Define the onClick listener
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Load current users from database
        // Get the ConsumersViewModel
        this.consumersViewModel = ViewModelProviders.of(Objects.requireNonNull(this)).get(ConsumersViewModel.class);

        // Repositories to load info each time an user logs in

        // Load the logistics centers each time the user login
        LogisticCenterRepository logisticCenterRepository = LogisticCenterRepository.getInstance(getApplication());
        logisticCenterRepository.loadLogisticCenters();

        // Load the producers users
        ConsumerRepository producerRepository = ConsumerRepository.getInstance(getApplication());
        producerRepository.loadProducers();

        // Load the products
        ProductRepository productRepository = ProductRepository.getInstance(getApplication());
        productRepository.loadProducts();
    }

    /**
     * Do Log in on the application
     */
    private void userLogin() {

        // Retrieve data from input text
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Check several conditions on log in process
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 4) {
            editTextPassword.setError("Password should be at least 4 character long");
            editTextPassword.requestFocus();
            return;
        }

        // Retrieve preferences to store the logged user
        SharedPreferences preferences = getSharedPreferences("LoggedUser", 0);

        // If the user exists check password
        consumersViewModel.getConsumerByEmail(email).observe(this, consumerItem -> {
            if (consumerItem != null) {

                if (password.equals(consumerItem.getPassword())) {
                    // If password is valid
                    // Store the user logged in the preferences
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name", consumerItem.getName());
                    editor.putString("email", consumerItem.getEmail());
                    editor.putInt("id", consumerItem.getId());
                    editor.apply();

                    // Start Drawer activity
                    Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else
                    Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(LoginActivity.this, "No user", Toast.LENGTH_LONG).show();
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        // Check selected button
        switch (v.getId()) {
            case R.id.buttonLogin:
                // Perform log in
                userLogin();
                break;
            case R.id.textViewRegister:
                // Start Sign up activity
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}

