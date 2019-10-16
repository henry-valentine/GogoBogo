package com.example.gogobogo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ProductEditor extends AppCompatDialogFragment
{

    private View view;

    DatabaseHelper gbDB;
    private EditText productType, productName, storeName, productPrice, dealDesc;

    private Product     product;
    private GogoBogo    gogoBogo;

    public ProductEditor(GogoBogo gogoBogo, Product product) {
        this.gogoBogo = gogoBogo;
        this.product = product;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.gbDB = new DatabaseHelper(getContext());
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
                        try {
                            gbDB.insertData(productType.getText().toString(),
                                    productName.getText().toString(), storeName.getText().toString(),
                                    productPrice.getText().toString(), dealDesc.getText().toString());
                        }
                        catch (Exception e){
                            System.out.println("Exception occurred in Database: /n" + e);
                        }

                        String type  = productType.getText().toString();
                        String name  = productName.getText().toString();
                        String store = storeName.getText().toString();
                        String price = productPrice.getText().toString();
                        String deal  = dealDesc.getText().toString();

                        Cursor res = gbDB.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            System.out.println("Error Nothing found");
                            return;
                        }

                        // TODO: SET product OBJECT ATTRIBUTES FOR OK CONDITION

                        // Add a new Product
                        product.setAll(type, name, store, Float.parseFloat(price), deal);
                        gogoBogo.addProduct(product);

                    }
                });

        // Logic //

        productType = view.findViewById(R.id.productEditor_productType_editable);
        productName = view.findViewById(R.id.productEditor_productName_editable);
        storeName = view.findViewById(R.id.productEditor_storeName_editable);
        productPrice = view.findViewById(R.id.productEditor_productPrice_editable);
        dealDesc = view.findViewById(R.id.productEditor_dealDescription_editable);

        // End Logic //

        return builder.create();
    }
}
