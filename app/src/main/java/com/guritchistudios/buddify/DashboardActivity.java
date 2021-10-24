package com.guritchistudios.buddify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {

    ActionBar actionBar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Account Activity");
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnItemSelectedListener(selectedListener);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment, "");
        transaction.commit();
    }

    private final NavigationBarView.OnItemSelectedListener selectedListener = new NavigationBarView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                  actionBar.setTitle("Home");
                  HomeFragment fragment = new HomeFragment();
                  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                  transaction.replace(R.id.content, fragment, "");
                  transaction.commit();
                  return true;

                case R.id.nav_users:
                    actionBar.setTitle("Users");
                    UsersFragment fragment1 = new UsersFragment();
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.content, fragment1, "");
                    transaction1.commit();
                    return true;

                case R.id.nav_add_blogs:
                    actionBar.setTitle("Add");
                    AddBlogsFragment fragment2 = new AddBlogsFragment();
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.content, fragment2, "");
                    transaction2.commit();
                    return true;

                case R.id.nav_chat:
                    actionBar.setTitle("Chat");
                    ChatListFragment fragment3 = new ChatListFragment();
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.content, fragment3, "");
                    transaction3.commit();
                    return true;

                case R.id.nav_profile:
                    actionBar.setTitle("Profile");
                    ProfileFragment fragment4 = new ProfileFragment();
                    FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                    transaction4.replace(R.id.content, fragment4, "");
                    transaction4.commit();
                    return true;
            }
            return false;
        }
    };
}