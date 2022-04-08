package com.unex.agrologistics.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.unex.agrologistics.R;
import com.unex.agrologistics.ui.login.LoginActivity;

public class ProfileFragment extends Fragment {

    /**
     * onCreateView method
     * @param inflater Fragment inflater
     * @param container Fragment container
     * @param savedInstanceState Bundle where instance is saved
     * @return Fragment View
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Retrieve profile name and email
        TextView profile_name = root.findViewById(R.id.profile_name);
        TextView profile_email = root.findViewById(R.id.profile_email);

        // Update the information on the UI
        SharedPreferences preferences = requireActivity().getSharedPreferences("LoggedUser",
                0);
        profile_name.setText(preferences.getString("name", ""));
        profile_email.setText(preferences.getString("email", ""));

        // Get logout button
        Button logout = root.findViewById(R.id.buttonLogout);

        // On Click remove the actual user
        logout.setOnClickListener(v -> {
            // Remove from preferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name", "");
            editor.putString("email", "");
            editor.putInt("id", -1);
            editor.apply();

            // Start Login activity
            startActivity(new Intent(this.getContext(), LoginActivity.class));
        });

        // Inflate fragment
        return root;
    }

}
