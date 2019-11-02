package com.example.gogobogo.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.gogobogo.GenericEditor;
import com.example.gogobogo.GenericEditor.GERow;
import com.example.gogobogo.MainActivity;
import com.example.gogobogo.R;
import com.example.gogobogo.UserAccount;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private OnFragmentInteractionListener mListener;

    private Button btnOpenEditor;
    private UserAccount mAccount = MainActivity.activity.getGogoBogo().getUserAccount();



    public AccountSettingsFragment() {
        // Required activity_login public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AccountSettingsFragment newInstance() {
        AccountSettingsFragment fragment = new AccountSettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);

        btnOpenEditor = view.findViewById(R.id.accountSettings_button_edit);
        btnOpenEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void updateAccount(UserAccount account) {
        if (mListener != null) {
            mListener.onFragmentInteraction(account);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(UserAccount account);
    }



    public void openAccountEditor()
    {
        ArrayList<GERow> rows = new ArrayList<>();
        rows.add(new GERow("Username", "New Username", getContext()));
        rows.add(new GERow("Email", "New email", getContext()));
        rows.add(new GERow("Password", "New Password", getContext()));

        GenericEditor accountEditor = new GenericEditor("Account", rows);
        //accountEditor.setOnInputListener(accountUpdateListener);

        accountEditor.show(getFragmentManager(), "AccountEditor");
    }


}
