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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product implements Comparable<Product>
{
    /* Instance Variables */
    private LinearLayout m_productLayout;
    private TextView     m_dealRating_tv;
    private String       m_name;
    private String       m_store;
    private String       m_deal;
    private String       m_productId;
    private int          m_upvotes;
    private int          m_downvotes;
    private float        m_price;

    /* Constructors */


    public Product ()
    {
        this("None", "None", "None", -1, 0, 0, null);
    }

    public Product(String name, String store, String deal, float price, int upvote, int downvote, String productId)
    {
        // Initialize Instance Variables
        this.m_name         = name;
        this.m_store        = store;
        this.m_deal         = deal;
        this.m_price        = price;
        this.m_upvotes      = upvote;
        this.m_downvotes    = downvote;

        // Generate Product Id
        this.m_productId = productId;
    }

    /**
     * Constructor for creating a NEW Product
     * @param name
     * @param store
     * @param deal
     * @param price
     */
    public Product(String name, String store, String deal, float price)
    {
        // Initialize Instance Variables
        this.m_name         = name;
        this.m_store        = store;
        this.m_deal         = deal;
        this.m_price        = price;
        this.m_upvotes      = 0;
        this.m_downvotes    = 0;

        // Generate Product Id
        this.m_productId = this.generateProductId();
    }


    /* Methods */
    /**
     *  Generates a visual representation of this product
     *  with an upvote, downvote, and add to cart button
     *  in the specified linear layout.
     *
     * @param layout_id        : ID of the Linear layout to add product to
     */
    public void addToLayout(final int layout_id)
    {
        // Activity Context
        Context context = MainActivity.getMainActivity();

        // Layout to Add This Product To
        LinearLayout lm = MainActivity.getMainActivity().findViewById(layout_id);

        // Set the Layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        // Create LinearLayout for product
        this.m_productLayout = new LinearLayout(context);
        this.setVisible(true);
        m_productLayout.setOrientation(LinearLayout.HORIZONTAL);
        m_productLayout.setBackgroundColor(0xf2f2f2f2);

        // Add the Shopping Cart Button (Add or Remove) //
        ImageButton addToCartButton = new ImageButton(context);

        // Determine if we need the 'Add' or 'Remove' Button
        if (layout_id == R.id.shoppingListLayout)
        {
            addToCartButton.setImageResource(R.drawable.remove_from_cart);
        }
        else
        {
            addToCartButton.setImageResource(R.drawable.add_to_cart);
        }


        addToCartButton.setBackgroundColor(0x00000000);
        addToCartButton.setPadding(20, 20, 30, 20);
        addToCartButton.setLayoutParams(params);

        // Set click listener for add to cart button
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(layout_id == R.id.homeLayout) {
                    Log.i("TAG", "Adding" + m_name + "to Cart");
                    Snackbar.make(v, "Adding " + m_name + " to Shopping List", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    // Add Product to Shopping List
                    addToShoppingList();
                }
                else
                {
                    Log.i("TAG", "Removing" + m_name + "from Cart");
                    Snackbar.make(v, "Removing " + m_name + " from Shopping List", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    // Add Product to Shopping List
                    removeFromShoppingList();
                }
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

        // Display Deal Rating
        m_dealRating_tv = new TextView(context);
        m_dealRating_tv.setText("\n" + getDealRating());

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

                // Update the deal rating
                m_dealRating_tv.setText("\n" + getDealRating());

                Log.i("TAG", "DOWNVOTE RECEIVED");
                Snackbar.make(v, ("Downvotes Received: " + m_downvotes), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if(removeIfNeeded())
                {
                    Log.i("TAG", "Removed Product: " + m_name);
                    Snackbar.make(v, ("Removing Product: " + m_name), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        //Add button to LinearLayout
        m_productLayout.addView(downButton);

        // Add Deal Rating Text View to Layout
        m_productLayout.addView(m_dealRating_tv);


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

                // Update the deal rating
                m_dealRating_tv.setText("\n" + getDealRating());

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

    /**
     * Set visibility of a product
     */
    public void setVisible(boolean visible)
    {
        if (visible)
        {
            this.m_productLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            this.m_productLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int compareTo(Product product) {
        // Descending in order of Deal Rating
        return product.getDealRating() - this.getDealRating();
    }

    private void addToShoppingList()
    {
        MainActivity.getMainActivity().getGogoBogo().addToShoppingList(this);
    }

    private void removeFromShoppingList()
    {
        ShoppingList shoppingList = MainActivity.getMainActivity().getGogoBogo().getShoppingList();
        shoppingList.removeProduct(this);
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

    public String getProductId()
    {
        return this.m_productId;
    }

    public void setProductId(String id)
    {
        this.m_productId = id;
    }


    public void setAll(String name, String store, Float price, String deal)
    {
        this.setName(name);
        this.setStore(store);
        this.setPrice(price);
        this.setDeal(deal);
    }

    /**
     * Generates a unique product id
     * @return
     */
    public String generateProductId()
    {
        // Get the current time
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

        // Generate a random int between 0 and 100
        int rand = (int) (Math.random() * 100);

        String productId = date.format(new Date()) + "-" + rand;

        Log.d("DEBUG", "Generated new product ID: " + productId);

        return productId;
    }

    public int getDealRating()
    {
        return m_upvotes - m_downvotes;
    }

    /**
     *
     * @return True if it was removed. False if it wasn't
     */
    public boolean removeIfNeeded()
    {
        if (getDealRating() < -5)
        {
            // Remove From Shopping List if it's there
            ShoppingList shoppingList = MainActivity.getMainActivity().getGogoBogo().getShoppingList();
            shoppingList.removeProduct(this);

            // Remove from Home Page
            MainActivity.getMainActivity().getGogoBogo().removeProduct(this);

            return true;
        }
        else
        {
            return false;
        }
    }
} // end class
