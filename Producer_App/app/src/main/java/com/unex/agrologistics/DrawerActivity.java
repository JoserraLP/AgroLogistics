package com.unex.agrologistics;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.unex.agrologistics.data.repository.ProducerEventRepository;
import com.unex.agrologistics.utils.Constants;

import java.util.Locale;

public class DrawerActivity extends AppCompatActivity {

    // Configuration for the App Bar
    private AppBarConfiguration mAppBarConfiguration;

    /**
     * onCreate method
     * @param savedInstanceState Bundle with data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ------- UI ------- //
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder
                (R.id.nav_map, R.id.nav_news, R.id.nav_settings, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();

        // Set the basic nav actions
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // ------- Settings ------- //

        // Check app language and set it on the app
        String languageCode = getSharedPreferences(Constants.PREFERENCES_NAME, Constants.PREFERENCES_MODE).getString(Constants.PREFERENCES_LANGUAGE, Constants.PREFERENCES_LANGUAGE_DEF_VALUE);
        if (languageCode != null && !languageCode.equals(Locale.getDefault().getLanguage()))
            putLanguage(languageCode);

        // Load producer events
        // Repository to load data
        ProducerEventRepository producerEventRepository =
                ProducerEventRepository.getInstance(this.getApplication());
        producerEventRepository.loadProducerEvents();
    }

    /**
     * Set the language app
     * @param languageCode language code to be set
     */
    @SuppressWarnings("deprecation")
    public void putLanguage(String languageCode) {
        // Create a Locale with the language code
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        // Configure the Locale
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    /**
     * Allows to navigate up in the navigation drawer
     * @return If navigation up is supported
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
