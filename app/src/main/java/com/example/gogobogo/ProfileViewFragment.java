package com.example.gogobogo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ProfileViewFragment extends Fragment
{
    private View view;

    private UserAccount user;
    private TextView name;
    private TextView email;
    private TextView dateCreated;


    public ProfileViewFragment(UserAccount user)
    {
        this.user = user;


        name = view.findViewById(R.id.profileView_name);
        email = view.findViewById(R.id.profileView_email);
        dateCreated = view.findViewById(R.id.profileView_dateCreated);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_profileview, container, false);
        return view;
    }


    // TODO: Edit window for profile information
    public void editProfileInformation()
    {

    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getEmail() {
        return email;
    }

    public void setEmail(TextView email) {
        this.email = email;
    }


    public TextView getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(TextView dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }
}
