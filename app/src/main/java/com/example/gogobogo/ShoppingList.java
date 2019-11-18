/*
 * TITLE:        ShoppingList.java
 * AUTHOR(s):    John Thomson, Lizzy Jackson, Payton Boliek,
 *               Rosemary Morataya, Henry Valentine
 * VERSION:      October 6, 2019
 * DESCRIPTION:
 * Class representing a shopping list maintained by a user.
 *
 * REVISION HISTORY:
 * October 6, 2019 : Initial Implementation
 *
 * */

package com.example.gogobogo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList
{

    /* Instance Variables */
    private List<Product> m_products;

    /* Constructors */
    public ShoppingList()
    {
        this.m_products = new ArrayList<>();
    }

    /* Methods */

    /**
     * Add a product to the shopping list
     * @param product : Product to be added
     */
    public void addProduct(Product product)
    {
        this.m_products.add(product);
    }

    /**
     * Removes a product from shopping list
     * @param product : Product to be removed
     */
    public void removeProduct(Product product)
    {
        product.setVisible(false);
        this.m_products.remove(product);
    }


    public List<Product> getProducts()
    {
        return m_products;
    }
    public void setProducts(List<Product> products)
    {
        this.m_products = products;
    }

} // end class
