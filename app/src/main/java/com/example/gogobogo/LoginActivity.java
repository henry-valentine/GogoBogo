package com.example.gogobogo;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gogobogo.ui.login.LoginFragment;

public class LoginActivity extends FragmentActivity
{
    private static GogoBogo gogoBogo = MainActivity.activity.getGogoBogo();
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private LoginFragment loginFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginFragment = new LoginFragment();
        loginFragment.setLoginListener(new LoginFragment.OnLoginListener() {
            @Override
            public void onLogin(UserAccount userAccount) {
                gogoBogo.setUserAccount(userAccount);
                finish();
            }
        });

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_fragment_container, loginFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed()
    {
        // Prevents the user from going to main activity before logging in.
        // Forces the task to minimize on pressing back button
        moveTaskToBack(true);
    }

    public void setGogoBogo(GogoBogo gogoBogo)
    {
        this.gogoBogo = gogoBogo;
    }
}
