package com.example.gogobogo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class GenericEditor extends AppCompatDialogFragment
{
    /**
     * Instructions:
     * Instantiate your ArrayList<GERow> object with your desired text fields...
     * Instantiate GenericEditor object with the title and the specified rows...
     * Declare your OnInputListener object within your class, overriding the sendInput method [Used to return the desired field info]...
     * Pass in the listener to the object using .setOnInputListener()
     * Done.
     */

    private boolean created = false;

    private View view;
    private TableLayout mLayout;
    private AlertDialog mAlertDialog;
    private String mTitle;

    private OnInputListener mOnInputListener;

    private ArrayList<GERow> mRows = new ArrayList<>();


    public GenericEditor(String title, ArrayList<GERow> rows)
    {
        this.mTitle = title;
        this.mRows = rows;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog_genericeditor, null);

        builder.setView(view)
                .setTitle(mTitle)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel button logic
                    }
                })
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Accept button logic

                        mOnInputListener.sendInput(mRows);
                    }
                });

        // Logic //
        //

        this.mLayout = (TableLayout) view.findViewById(R.id.dialog_genericeditor);
        init();

        mAlertDialog = builder.create();

        this.created = true;
        return mAlertDialog;
    }

    public void addRow(ArrayList<GERow> rows)
    {
        for(int i = 0; i<rows.size(); i++)
        {
            addRow(rows.get(i));
        }
    }

    public void addRow(GERow row)
    {
        this.mRows.add(row);
        if(this.created)
            this.mLayout.addView(row.getRowLayout());
    }

    public void addRow(String title, String hint, Context context)
    {
        GERow row = new GERow(title, hint, context);
        mRows.add(row);
        if(this.created)
            this.mLayout.addView(row.getRowLayout());
    }

    // Initialize the layout (used by onCreate method, can only be called once!)
    private void init()
    {
        for(int i = 0; i<this.mRows.size(); i++)
        {
            this.mLayout.addView(this.mRows.get(i).getRowLayout());
        }
    }

    // Listening interface

    public interface OnInputListener
    {
        void sendInput(ArrayList<GERow> dialogData);
    }


    // Getters and Setters

    public OnInputListener getOnInputListener()
    {
        return mOnInputListener;
    }

    public void setOnInputListener(OnInputListener listener)
    {
        this.mOnInputListener = listener;
    }

    // Sub Class for creating new rows within GE.
    public static class GERow
    {
        private TableRow layout;
        private TextView title;
        private EditText editable;

        public GERow(String title, String hint, Context context, boolean isPassword)
        {
            this(title, hint, context);

            editable.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        public GERow(String title, String hint, Context context)
        {
            this.layout = new TableRow(context);
            this.title = new TextView(context);
            this.editable = new EditText(context);

            // Setup layout parameters
            LayoutParams lp = new TableRow.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            );

            LayoutParams lpTextView = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );

            LayoutParams lpEditText = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );

            // Setup layout, TextView, and EditText
            lp.gravity = Gravity.CENTER;
            this.layout.setLayoutParams(lp);

            lpTextView.gravity = Gravity.CENTER;
            lpTextView.weight = 1;
            this.title.setLayoutParams(lpTextView);

            this.title.setTextSize(18);
            this.title.setText(title);
            this.title.setTextColor(Color.WHITE);

            lpEditText.weight = 1;
            lpEditText.gravity = Gravity.LEFT;
            lpEditText.width = 50;
            this.editable.setLayoutParams(lpEditText);

            this.editable.setText("");
            this.editable.setHint(hint);
            this.editable.setHintTextColor(Color.parseColor("#7C7C7C"));
            this.editable.setTextColor(Color.WHITE);

            // Adds textview and edittext to layout.
            this.layout.addView(this.title);
            this.layout.addView(this.editable);
        }

        // Getters and Setters

        public String getFieldString()
        {
            return this.editable.getText().toString();
        }

        public TableRow getRowLayout() {
            return this.layout;
        }

        public void setRowLayout(TableRow layout) {
            this.layout = layout;
        }

        public TextView getTitle() {
            return this.title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public EditText getEditable() {
            return this.editable;
        }

        public void setEditable(EditText editable) {
            this.editable = editable;
        }
    }
}
