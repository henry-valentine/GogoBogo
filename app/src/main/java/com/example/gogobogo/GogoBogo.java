/*
* TITLE:        GogoBogo.java
* AUTHOR(s):    John Thomson, Lizzy Jackson, Payton Boliek,
*               Rosemary Morataya, Henry Valentine
* VERSION:      September 30, 2019
* DESCRIPTION:
* John, where James had had "had had" had had "had." "Had had" had had the teacher's approval.
*
* REVISION HISTORY:
*
*
* */

package com.example.gogobogo;

import android.widget.LinearLayout;
import java.util.ArrayList;


public class GogoBogo
{

    public ArrayList<Product> products;     // Products on the home page
    public ShoppingList shoppingList;       // Shopping List for the User

    /* Instance Variables */
    UserAccount userAccount;

    public GogoBogo()
    {
        this.products = new ArrayList<>();
        this.shoppingList = new ShoppingList();
    }

    public void addProduct(Product product) {
        this.products.add(product);

        // Add product to the home screen
        LinearLayout lm = MainActivity.activity.findViewById(R.id.homeLayout);
        product.addToLayout(MainActivity.activity, lm);
    }

    public void addToShoppingList(Product product)
    {
        shoppingList.addProduct(product);

    }

    public ShoppingList getShoppingList()
    {
        return this.shoppingList;
    }

    public ArrayList<Product> getProducts()
    {
        return this.products;
    }
}



