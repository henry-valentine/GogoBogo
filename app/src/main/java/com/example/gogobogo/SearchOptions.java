package com.example.gogobogo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;


import java.util.ArrayList;

public class SearchOptions extends AppCompatDialogFragment
{
    private GogoBogo    gogoBogo;
    private ArrayList<CheckBox> checkBoxes;

    public SearchOptions(GogoBogo gogoBogo)
    {
        this.gogoBogo = gogoBogo;
        this.checkBoxes = new ArrayList<>();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
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

                        // Do Nothing

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ok Button Logic

                        // Create a list of checked stores
                        ArrayList<String> searchStores = new ArrayList<>();
                        for (int i = 0; i < checkBoxes.size(); i++)
                        {
                            if (checkBoxes.get(i).isChecked())
                            {
                                String store = gogoBogo.getStoreList().get(i);
                                searchStores.add(store);
                            }
                        }

                        // Filter products by store
                        gogoBogo.filterByStore(searchStores);

                    }
                });

        // Logic //

        // Populate the Table
        TableLayout table = view.findViewById(R.id.store_table);

        for (String store : gogoBogo.getStoreList())
        {
            // Create a Checkbox for Each Store
            CheckBox checkBox = new CheckBox(MainActivity.activity);
            checkBox.setChecked(true);
            this.checkBoxes.add(checkBox);

            // Create Text Views
            TextView storeName = new TextView(MainActivity.activity);
            storeName.setText(store);
            storeName.setTextColor(Color.WHITE);

            TextView searchText = new TextView(MainActivity.activity);
            searchText.setText("Search");
            searchText.setTextColor(Color.LTGRAY);

            // Create Row
            TableRow tableRow = new TableRow(MainActivity.activity);

            // Add items to the row
            tableRow.addView(storeName);
            tableRow.addView(checkBox);
            tableRow.addView(searchText);

            // Add Row to Table
            table.addView(tableRow);
        }

        // End Logic //

        return builder.create();
    }
}
