package com.example.gogobogo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SearchOptions extends AppCompatDialogFragment
{
    private GogoBogo    gogoBogo;

    public SearchOptions(GogoBogo gogoBogo)
    {
        this.gogoBogo = gogoBogo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.search_options, null);

        // This builds the window with 'Ok' and 'Cancel' buttons.
        builder.setView(view)
                .setTitle("Search Options")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel Button Logic

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ok Button Logic



                    }
                });

        // Logic //


        // End Logic //

        return builder.create();
    }
}
