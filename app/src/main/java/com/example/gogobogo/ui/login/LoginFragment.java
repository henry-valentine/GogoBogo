package com.example.gogobogo.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gogobogo.GenericEditor;
import com.example.gogobogo.R;
import com.example.gogobogo.UserAccount;

import java.util.ArrayList;

public class LoginFragment extends Fragment
{
    private View mView;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView registrationButton;
    private UserAccount userAccount;

    private GenericEditor registrationWindow;
    private GenericEditor.OnInputListener registerListener = new GenericEditor.OnInputListener() {
        @Override
        public void sendInput(ArrayList<GenericEditor.GERow> dialogData) {
            String name = dialogData.get(0).getEditable().getText().toString();
            String email = dialogData.get(1).getEditable().getText().toString();
            String pass = dialogData.get(2).getEditable().getText().toString();

            UserAccount user = new UserAccount(name, pass);

            if (isUniqueCredentials(user))
            {
                userAccount = user;
                if (loginListener == null)
                    Log.e("REGISTER", "MISSING LOGIN LISTENER! Must override OnLoginListener!");

                // TODO : Add user to database

                //loginListener.onLogin(user);
            }
        }
    };

    private OnLoginListener loginListener;

    public LoginFragment()
    {
        // Required activity_login constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_login, container, false);

        username = mView.findViewById(R.id.login_username_edittext);
        password = mView.findViewById(R.id.login_password_edittext);
        loginButton = mView.findViewById(R.id.login_button);
        registrationButton = mView.findViewById(R.id.login_register);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        password.setOnKeyListener(new View.OnKeyListener() {
            // Handles the user pressing 'Enter' when finished writing in their password, rather than needing to press 'Login' button.
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean result = false;

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    attemptLogin();
                    result = true;
                }

                return result;
            }
        });

        // Button logic:

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildRegistrationWindow();
            }
        });

    }

    private void attemptLogin()
    {
        UserAccount account = retrieveUserFromDB(username.getText().toString(), password.getText().toString());
        if (account != null)
        {
            // TODO : Find user information from credentials

            // TEST
            userAccount = account;
            // END TEST

            loginListener.onLogin(userAccount);
        }

        else
        {
            Toast.makeText(getActivity(), "Sorry, invalid credentials.", Toast.LENGTH_SHORT).show();
            username.setText(null);
            password.setText(null);
        }
    }

    private UserAccount retrieveUserFromDB(String username, String password)
    {
        // Use this to validate user credentials within the text fields
        UserAccount result = new UserAccount("", "");

        // TODO: Database user validation
        // TEST LOGIC
        if (!username.isEmpty() && !password.isEmpty())
        {
            result.setPassword(password);
            result.setUserName(username);
        }

        return result;
    }

    private boolean isUniqueCredentials(UserAccount user)
    {
        boolean result = false;

        // TODO: Database unique user validation
        // TEST:
        result = true;

        return result;
    }

    private void buildRegistrationWindow()
    {
        ArrayList<GenericEditor.GERow> rows = new ArrayList<>();
        rows.add(new GenericEditor.GERow("Username", "name", getActivity()));
        rows.add(new GenericEditor.GERow("Email", "email", getActivity()));
        rows.add(new GenericEditor.GERow("Password", "password", getActivity(), true));

        GenericEditor registrationWindow = new GenericEditor("Register", rows);
        registrationWindow.show(getFragmentManager(), "REGISTRATION_EDITOR");
    }

    public interface OnLoginListener
    {
        public void onLogin(UserAccount userAccount);
    }

    public OnLoginListener getLoginListener() {
        return loginListener;
    }

    public void setLoginListener(OnLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public View getFragmentView() {
        return mView;
    }

    public void setFragmentView(View mView) {
        this.mView = mView;
    }
}
