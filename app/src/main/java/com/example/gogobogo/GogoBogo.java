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

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;


public class GogoBogo
{
    /* Instance Variables */
    private ArrayList<Product> allProducts;         // Products on the home page
    private ShoppingList        shoppingList;       // Shopping List for the User
    private ArrayList<String>   storeList;          // List of Stores

    private Product tmpProduct = null;

    UserAccount userAccount;

    public GogoBogo()
    {
        this.allProducts = new ArrayList<>();
        this.shoppingList = new ShoppingList();
        this.storeList = new ArrayList<>();
    }

    /**
     * Filter the displayed allProducts by a list of stores
     * @param stores - List of stores to show allProducts from
     */
    public void filterByStore(ArrayList<String> stores)
    {
        // Iterate through allProducts
        for (Product product : allProducts)
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

    /**
     * Filter displayed products by a search query
     * @param query : The search query
     */
    public void filterByQuery(String query)
    {
        for (Product product : allProducts)
        {
            // Make it case insensitive
            String lc_name  = product.getName().toLowerCase();
            String lc_query = query.toLowerCase();

            // Filter by query
            if (!lc_name.contains(lc_query))
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
        this.allProducts.add(product);

        DatabaseHelper dbh = new DatabaseHelper();
        dbh.addProduct(product);

        // Add product to the home screen
        product.addToLayout(R.id.homeLayout);
    }

    public void removeProduct(Product product)
    {
        // Remove product from the home page
        product.setVisible(false);

        tmpProduct = product;

        DatabaseHelper dbh = new DatabaseHelper();
        OnSuccessListener deleteListener = new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                allProducts.remove(tmpProduct);
            }
        };

        dbh.removeProduct(product.getProductId(), deleteListener);
    }

    public ArrayList<Product> getAllProducts()
    {
        return this.allProducts;
    }

    public void setAllProducts(ArrayList<Product> products)
    {
        this.allProducts = products;

        for (Product product : products)
        {
            product.addToLayout(R.id.homeLayout);
        }
    }

    public void addToShoppingList(Product product)
    {
        shoppingList.addProduct(product);
    }

    public boolean itemInShoppingList(Product product)
    {
        return shoppingList.contains(product);
    }

    public ArrayList<String> getStoreList()
    {
        // Update the StoreList
        String storeName;
        for (Product product : allProducts)
        {
            storeName = product.getStore();

            if (!this.storeList.contains(storeName))
            {
                storeList.add(storeName);
            }
        }
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
        this.shoppingList = userAccount.getShoppingList();
    }

    public void updateProductReferences()
    {
        int i;

        Product userP;
        ArrayList<Product> shoppingList = userAccount.getShoppingList().getProducts();


        for (Product homeP : allProducts)
        {
            for (i = 0; i < shoppingList.size(); i++)
            {
                userP = shoppingList.get(i);

                if (userP.getProductId() != null && homeP.getProductId().equals(userP.getProductId()))
                {
                    userAccount.getShoppingList().getProducts().set(i, homeP);
                }
            }
        }
    }


}



