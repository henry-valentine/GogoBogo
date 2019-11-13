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
import java.util.ArrayList;


public class GogoBogo
{
    /* Instance Variables */
    private ArrayList<Product>  products;           // Products on the home page
    private ShoppingList        shoppingList;       // Shopping List for the User
    private ArrayList<String>   storeList;          // List of Stores

    UserAccount userAccount;

    public GogoBogo()
    {
        this.products = new ArrayList<>();
        this.shoppingList = new ShoppingList();

        this.storeList = new ArrayList<>();

        // TODO : Populate from Database instead of Hardcoding
        ////////////////////////////////////////////////////////
        storeList.add("Walmart");
        storeList.add("Publix");
        storeList.add("Aldi");
        storeList.add("NM Walmart");
        ////////////////////////////////////////////////////////
    }

    /**
     * Filter the displayed products by a list of stores
     * @param stores - List of stores to show products from
     */
    public void filterByStore(ArrayList<String> stores)
    {
        // Iterate through products
        for (Product product : products)
        {
            // If Product's store is not on filter list, hide it.
            if (!stores.contains(product.getStore()))
            {
                product.setVisible(false);
            }
            else
            {
                product.setVisible(true);
            }
        }

    }

    public void addProduct(Product product) {
        this.products.add(product);

        // Add product to the home screen
        product.addToLayout(R.id.homeLayout);
    }

    public void removeProduct(Product product)
    {
        // Remove product from the home page
        product.setVisible(false);
        this.products.remove(product);
    }

    public ArrayList<Product> getProducts()
    {
        return this.products;
    }

    public void addToShoppingList(Product product)
    {
        shoppingList.addProduct(product);

    }

    public ArrayList<String> getStoreList()
    {
        return storeList;
    }

    public ShoppingList getShoppingList()
    {
        return this.shoppingList;
    }
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}



