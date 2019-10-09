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

    public ArrayList<Product> products;

    /* Instance Variables */
    UserAccount userAccount;



    public GogoBogo()
    {
        this.products = new ArrayList<Product>();

    }

    public void addProduct(Product product) {
        this.products.add(product);

        // Add product to the home scree
        LinearLayout lm = MainActivity.activity.findViewById(R.id.homeLayout);
        product.addToLayout(MainActivity.activity, lm);
    }

}



