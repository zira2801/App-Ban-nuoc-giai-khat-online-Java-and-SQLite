package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Fragment.Favorite;
import Fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private boolean DoublePressToExit = false;
    Toast toast;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static  final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String name = sharedPreferences.getString(KEY_USERNAME,null);
        String pass = sharedPreferences.getString(KEY_PASSWORD,null);
      mBottomNavigationView = findViewById(R.id.bottom_nav);
      getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay,new HomeFragment()).commit();

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay,new HomeFragment()).commit();
                        return true;
                    case R.id.list_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay,new ListSPFragment()).commit();
                        return true;
                    case R.id.cart:
                        if(name == null){
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        }
                        else if(sharedPreferences.contains(KEY_USERNAME)){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay,new CartFragment()).commit();
                        }
                        return true;
                    case R.id.favorite:

                        if(name == null){
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        }
                        else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay,new FavoriteFragment()).commit();

                        }

                        return true;
                    case R.id.profile:
                        if(name == null){
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_left);
                        }
                        else if(sharedPreferences.contains(KEY_USERNAME)){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_lay,new ProfileFragment()).commit();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(DoublePressToExit){
            finishAffinity();
        }else{
            DoublePressToExit = true;
            Toast.makeText(MainActivity.this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DoublePressToExit = false;
                }
            },1500);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}