package com.unex.agrologistics.ui.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.unex.agrologistics.R;
import com.unex.agrologistics.data.repository.ConsumerRepository;
import com.unex.agrologistics.model.Consumer;
import com.unex.agrologistics.model.Producer;
import com.unex.agrologistics.ui.login.LoginActivity;
import com.unex.agrologistics.ui.login.ConsumersViewModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    // Edit Text for sign up activity
    private EditText signupName;
    private EditText signupEmail;
    private EditText signupPassword;
    private EditText signupConfirmPassword;

    // Producer repository and view model
    private ConsumerRepository consumerRepository;
    private ConsumersViewModel consumersViewModel;

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
        consumersViewModel = new ViewModelProvider(this).get(
                ConsumersViewModel.class);
        consumerRepository = ConsumerRepository.getInstance(getApplication());

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
        consumersViewModel.getConsumerByEmail(email).observe(this, producerItem -> {
            if (producerItem == null) {
                // Producer and its attributes
                Consumer consumer = new Consumer();
                consumer.setEmail(email);
                consumer.setName(name);
                consumer.setPassword(password);

                // Insert new user on API
                consumerRepository.postConsumer(consumer);

                // Insert new user in the local db
                consumerRepository.insertConsumer(new MutableLiveData<>(consumer));

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

