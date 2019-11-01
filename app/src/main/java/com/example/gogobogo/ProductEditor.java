package com.example.gogobogo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ProductEditor extends AppCompatDialogFragment
{
    private EditText productName;
    private EditText storeName;
    private EditText productPrice;
    private EditText dealDesc;

    private Product     product;
    private GogoBogo    gogoBogo;

    public ProductEditor(GogoBogo gogoBogo, Product product) {
        this.gogoBogo = gogoBogo;
        this.product = product;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.producteditor_layout, null);

        // This builds the window with 'Ok' and 'Cancel' buttons.
        builder.setView(view)
                .setTitle("Product Editor")
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

                        String name  = productName.getText().toString();
                        String store = storeName.getText().toString();
                        String price = productPrice.getText().toString();
                        String deal  = dealDesc.getText().toString();

                        // Add a new Product
                        product.setAll(name, store, Float.parseFloat(price), deal);
                        gogoBogo.addProduct(product);

                    }
                });

        // Logic //

        productName = view.findViewById(R.id.productEditor_productName_editable);
        storeName = view.findViewById(R.id.productEditor_storeName_editable);
        productPrice = view.findViewById(R.id.productEditor_productPrice_editable);
        dealDesc = view.findViewById(R.id.productEditor_dealDescription_editable);

        // End Logic //

        return builder.create();
    }
}
