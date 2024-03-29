package com.example.gogobogo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gogobogo.MainActivity;
import com.example.gogobogo.Product;
import com.example.gogobogo.R;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment
{

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        // Add all the GogoBogo Products to the homeLayout
        ArrayList<Product> products = MainActivity.getMainActivity().getGogoBogo().getAllProducts();

        // Sort the ArrayList by Deal Rating
        Collections.sort(products);

        for (Product product : products)
        {
            product.addToLayout(R.id.homeLayout);
        }

        // Set the Search View Query Listeners
        SearchView searchView = MainActivity.getMainActivity().findViewById(R.id.searchBar);

        // Logic to Handle Query Text
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.getMainActivity().getGogoBogo().filterByQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // Call a gogoBogo method here?

                return false;
            }
        });

    }

}