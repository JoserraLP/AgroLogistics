package com.unex.agrologistics.ui.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.unex.agrologistics.DrawerActivity;
import com.unex.agrologistics.R;
import com.unex.agrologistics.data.repository.LogisticCenterRepository;
import com.unex.agrologistics.data.repository.ProducerRepository;
import com.unex.agrologistics.data.repository.ProductRepository;
import com.unex.agrologistics.model.Producer;
import com.unex.agrologistics.model.ProducerEvent;
import com.unex.agrologistics.ui.delivery.viewmodel.ProductsViewModel;
import com.unex.agrologistics.ui.login.LoginActivity;
import com.unex.agrologistics.ui.login.ProducersViewModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // Edit Text for sign up activity
    private EditText signupName;
    private EditText signupEmail;
    private EditText signupPassword;
    private EditText signupConfirmPassword;

    // Producer repository and view model
    private ProducerRepository producerRepository;
    private ProducersViewModel producersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set content view
        setContentView(R.layout.activity_signup);

        // Define the text views
        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupConfirmPassword = findViewById(R.id.signupConfirmPassword);

        // Retrieve the view model and repository
        producersViewModel = new ViewModelProvider(this).get(
                ProducersViewModel.class);
        producerRepository = ProducerRepository.getInstance(getApplication());

        // Define the onClick listener
        findViewById(R.id.buttonSignUp).setOnClickListener(this);

    }

    /**
     * Do Sign Up on the application
     */
    private void signUp() {

        // Retrieve data from input text
        String name = signupName.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String confirmPassword = signupConfirmPassword.getText().toString().trim();

        // Check several conditions on sign up process
        if (name.isEmpty()) {
            signupName.setError("Name is required");
            signupName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            signupEmail.setError("Email is required");
            signupEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Enter a valid email");
            signupEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signupPassword.setError("Password required");
            signupPassword.requestFocus();
            return;
        }

        if (password.length() < 4) {
            signupPassword.setError("Password should be at least 4 character long");
            signupPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)){
            signupPassword.setError("Password must be the same");
            signupPassword.requestFocus();
            return;
        }

        // Check if there exists an user with the provided email, it is not valid
        producersViewModel.getProducerByEmail(email).observe(this, producerItem -> {
            if (producerItem == null) {
                // Producer and its attributes
                Producer producer = new Producer();
                producer.setEmail(email);
                producer.setName(name);
                producer.setPassword(password);

                // Insert new user on API
                producerRepository.postProducer(producer);

                // Insert new user in the local db
                producerRepository.insertProducer(new MutableLiveData<>(producer));

                // Start Login activity
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            } else {
                Toast.makeText(SignUpActivity.this, "Email already registered",
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSignUp) {
            signUp();
        }
    }
}

