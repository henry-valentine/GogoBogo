package com.example.gogobogo.ui.shoppingList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gogobogo.MainActivity;
import com.example.gogobogo.Product;
import com.example.gogobogo.R;
import com.example.gogobogo.ShoppingList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        shoppingListViewModel =
                ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        shoppingListViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        // Add all the GogoBogo Products to the homeLayout
        ShoppingList shoppingList = MainActivity.activity.getGogoBogo().getShoppingList();
        ArrayList<Product> products = shoppingList.getProducts();

        // Sort the ArrayList by Deal Rating
        Collections.sort(products);

        for (Product product : products)
        {
            product.addToLayout(R.id.shoppingListLayout);
        }
    }

    public ShoppingListViewModel getViewModel()
    {
        return this.shoppingListViewModel;
    }


}