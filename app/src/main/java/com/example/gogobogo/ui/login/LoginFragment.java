package com.example.gogobogo.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.gogobogo.DatabaseHelper;
import com.example.gogobogo.GenericEditor;
import com.example.gogobogo.R;
import com.example.gogobogo.UserAccount;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LoginFragment extends Fragment
{
    private View mView;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView registrationButton;
    private UserAccount userAccount;
    private UserAccount m_tmpUser;

    private DatabaseHelper dbh;

    private GenericEditor registrationWindow;
    private GenericEditor.OnInputListener registerListener = new GenericEditor.OnInputListener() {
        @Override
        public void sendInput(ArrayList<GenericEditor.GERow> dialogData) {
            // Upon the pressing of the 'Accept' button within the register window...

            String name = dialogData.get(0).getEditable().getText().toString();
            String email = dialogData.get(1).getEditable().getText().toString(); // Depreciated
            String pass = dialogData.get(2).getEditable().getText().toString();

            // Instantiate database helper, setup the temporary user...
            dbh = new DatabaseHelper();

            m_tmpUser = new UserAccount(name, pass);

            // Verify that the user doesn't already exist by requesting the user from the database.
            dbh.setOnUserAccountReceivedListener(new DatabaseHelper.OnUserAccountReceived() {
                @Override
                public void onRetrieval(UserAccount user) {
                    if (user == null)
                    {
                        userAccount = m_tmpUser;

                        dbh.addUser(m_tmpUser);

                        loginListener.onLogin(userAccount);
                    }

                    else
                        displayTopMessage("Sorry, user by that name already exists. Please try again.");
                }
            });

            // Executes the retrieval of the user for unique credential verification.
            dbh.getUserByName(name);


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
                    retrieveUserFromDB();
                    result = true;
                }

                return result;
            }
        });

        // Button logic:

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveUserFromDB();
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildRegistrationWindow();
            }
        });

    }

    private void attemptLogin(UserAccount user)
    {
        Log.println(Log.ASSERT, "INFO", "Attempting login...");
        if (user != null)
        {
            userAccount = user;

            loginListener.onLogin(userAccount);
        }

        else
        {
            displayTopMessage("Sorry, invalid credentials, please try again.");
        }
    }

    private void retrieveUserFromDB()
    {
        // Use this to validate user credentials within the text fields
        Log.println(Log.ASSERT, "INFO", "Retrieving...");

        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        DatabaseHelper dbh = new DatabaseHelper();

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {

            dbh.setOnUserAccountReceivedListener(new DatabaseHelper.OnUserAccountReceived() {
                @Override
                public void onRetrieval(UserAccount user) {
                    Log.println(Log.ASSERT, "INFO", "Attempting attempt...");
                    attemptLogin(user);
                }
            });

            dbh.requestUser(username, password);
        }

        else
            attemptLogin(null);
    }


    private void buildRegistrationWindow()
    {
        ArrayList<GenericEditor.GERow> rows = new ArrayList<>();
        rows.add(new GenericEditor.GERow("Username", "name", getActivity()));
        rows.add(new GenericEditor.GERow("Email", "email", getActivity()));
        rows.add(new GenericEditor.GERow("Password", "password", getActivity(), true));

        GenericEditor registrationWindow = new GenericEditor("Register", rows);
        registrationWindow.setOnInputListener(registerListener);
        registrationWindow.show(getFragmentManager(), "REGISTRATION_EDITOR");
    }

    public void displayTopMessage(String message)
    {
        Log.println(Log.ASSERT, "INFO", "Message sent");
        LinearLayout loginLayout = mView.findViewById(R.id.login_window_layout);
        Snackbar snack = Snackbar.make(loginLayout, message, Snackbar.LENGTH_LONG);
        View tmpView = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tmpView.getLayoutParams();
        params.gravity = Gravity.TOP;
        tmpView.setLayoutParams(params);
        snack.show();
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
