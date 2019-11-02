package com.example.gogobogo.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gogobogo.GenericEditor;
import com.example.gogobogo.GenericEditor.GERow;
import com.example.gogobogo.MainActivity;
import com.example.gogobogo.R;
import com.example.gogobogo.UserAccount;

import java.util.ArrayList;

public class SettingsFragment extends Fragment implements AccountSettingsFragment.OnFragmentInteractionListener
{
    private View view;

    private TextView mName;
    private TextView mEmail;

    private Button open_accountSettings;
    private UserAccount mAccount = MainActivity.activity.getGogoBogo().getUserAccount();

    private GenericEditor.OnInputListener accountUpdateListener = new GenericEditor.OnInputListener() {
        @Override
        public void sendInput(ArrayList<GERow> dialogData) {
            mAccount.setUserName(dialogData.get(0).getEditable().getText().toString());
            mAccount.setEmailAddress(dialogData.get(1).getEditable().getText().toString());
            mAccount.setPassword(dialogData.get(2).getEditable().getText().toString());

            mName.setText(mAccount.getUserName());
            mEmail.setText(mAccount.getEmailAddress());

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_settings, container, false);

        open_accountSettings = (Button) view.findViewById(R.id.settings_button_openAccountSettings);
        open_accountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccountEditor();
            }
        });

        mName = view.findViewById(R.id.settings_account_name);
        mEmail = view.findViewById(R.id.settings_account_email);



        mName.setText(mAccount.getUserName());
        mEmail.setText(mAccount.getEmailAddress());

        return this.view;
    }

    @Override
    public void onFragmentInteraction(UserAccount account) {

    }

    public void openAccountEditor()
    {
        ArrayList<GERow> rows = new ArrayList<>();
        rows.add(new GERow("Username", "New Username", getContext()));
        rows.add(new GERow("Email", "New email", getContext()));
        rows.add(new GERow("Password", "New Password", getContext()));

        GenericEditor accountEditor = new GenericEditor("Account", rows);
        accountEditor.setOnInputListener(accountUpdateListener);

        accountEditor.show(getFragmentManager(), "AccountEditor");
    }
}
