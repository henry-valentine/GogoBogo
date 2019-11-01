/*
 * TITLE:        Product.java
 * AUTHOR(s):    John Thomson, Lizzy Jackson, Payton Boliek,
 *               Rosemary Morataya, Henry Valentine
 * VERSION:      October 6, 2019
 * DESCRIPTION:
 * Class representing a product and any associated deals.
 *
 * REVISION HISTORY:
 * October 6, 2019 : Initial Implementation
 *
 * */

package com.example.gogobogo;

import android.app.ActionBar;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class Product implements Comparable<Product>
{
    /* Static Variables */
    private static int nextProductId = 0;

    /* Instance Variables */
    private LinearLayout m_productLayout;
    private String       m_name;
    private String       m_store;
    private String       m_deal;
    private int          m_productId;
    private int          m_upvotes;
    private int          m_downvotes;
    private float        m_price;

    private GogoBogo gogoBogo;

    /* Constructors */
    public Product(String name, String store, String deal, float price, GogoBogo gogoBogo)
    {
        // Initialize Instance Variables
        this.m_name         = name;
        this.m_store        = store;
        this.m_deal         = deal;
        this.m_price        = price;

        this.gogoBogo = gogoBogo;

        this.m_upvotes      = 0;
        this.m_downvotes    = 0;

        // Set Product ID
        this.m_productId = nextProductId;
        nextProductId++;
    }

    /* Methods */
    /**
     *  Generates a visual representation of this product
     *  with an upvote, downvote, and add to cart button
     *  in the specified linear layout.
     *
     * @param context   : The current context for this app
     * @param lm        : Linear layout to add product to
     */
    public void addToLayout(Context context, LinearLayout lm)
    {
        // Set the Layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        // Create LinearLayout for product
        this.m_productLayout = new LinearLayout(context);
        m_productLayout.setOrientation(LinearLayout.HORIZONTAL);
        m_productLayout.setBackgroundColor(0xf2f2f2f2);

        // Add the Shopping Cart Button //
        ImageButton addToCartButton = new ImageButton(context);
        addToCartButton.setImageResource(R.drawable.add_to_cart);
        addToCartButton.setBackgroundColor(0x00000000);
        addToCartButton.setPadding(20, 20, 30, 20);
        addToCartButton.setLayoutParams(params);

        // Set click listener for add to cart button
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Log.i("TAG", "Adding" + m_name + "to Cart");
                Snackbar.make(v, "Adding " + m_name +  " to Shopping List", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Add Product to Shopping List
                addToShoppingList();

            }
        });

        //Add button to LinearLayout
        m_productLayout.addView(addToCartButton);

        // Create TextView
        TextView product = new TextView(context);
        product.setText(m_name + "\n" + m_store + "\n" + m_deal);
        product.setWidth(450);
        m_productLayout.addView(product);

        // Create TextView
        TextView price = new TextView(context);
        price.setText("\n$" + m_price);
        price.setWidth(200);
        m_productLayout.addView(price);

        // Add the Downvote Button //
        ImageButton downButton = new ImageButton(context);
        downButton.setImageResource(R.drawable.downvote);
        downButton.setBackgroundColor(0x00000000);
        downButton.setPadding(20, 20, 20, 0);
        downButton.setLayoutParams(params);

        // Set click listener for button
        downButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                m_downvotes++;

                Log.i("TAG", "DOWNVOTE RECEIVED");
                Snackbar.make(v, ("Downvotes Received: " + m_downvotes), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Add button to LinearLayout
        m_productLayout.addView(downButton);

        // Add the Upvote Button //
        ImageButton upButton = new ImageButton(context);
        upButton.setImageResource(R.drawable.upvote);
        upButton.setBackgroundColor(0x00000000);
        upButton.setPadding(20, 20, 20, 0);
        upButton.setLayoutParams(params);

        // Set click listener for button
        upButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                m_upvotes++;

                Log.i("TAG", "UPVOTE RECEIVED");
                Snackbar.make(v, ("Upvotes Received: " + m_upvotes), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Add button to LinearLayout
        m_productLayout.addView(upButton);

        // Add Product to the Home Linear Layout
        lm.addView(m_productLayout);

    } // end addToLayout

    @Override
    public int compareTo(Product product) {
        // Descending in order of Deal Rating
        return product.getDealRating() - this.getDealRating();
    }

    private void addToShoppingList()
    {
        this.gogoBogo.addToShoppingList(this);
    }

    /* Getters and Setters */
    public String getName()
    {
        return m_name;
    }

    public void setName(String name)
    {
        this.m_name = name;
    }

    public String getStore()
    {
        return m_store;
    }

    public void setStore(String store)
    {
        this.m_store = store;
    }

    public String getDeal()
    {
        return m_deal;
    }

    public void setDeal(String deal)
    {
        this.m_deal = deal;
    }

    public int getUpvotes()
    {
        return m_upvotes;
    }

    public void setUpvotes(int upvotes)
    {
        this.m_upvotes = upvotes;
    }

    public int getDownvotes()
    {
        return m_downvotes;
    }

    public void setDownvotes(int downvotes)
    {
        this.m_downvotes = downvotes;
    }

    public float getPrice()
    {
        return m_price;
    }

    public void setPrice(float price)
    {
        this.m_price = price;
    }


    public void setAll(String name, String store, Float price, String deal)
    {
        this.setName(name);
        this.setStore(store);
        this.setPrice(price);
        this.setDeal(deal);
    }

    public int getDealRating()
    {
        return m_upvotes - m_downvotes;
    }
} // end class
