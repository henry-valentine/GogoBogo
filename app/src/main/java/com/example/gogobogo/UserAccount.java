/*
 * TITLE:        UserAccount.java
 * AUTHOR(s):    John Thomson, Lizzy Jackson, Payton Boliek,
 *               Rosemary Morataya, Henry Valentine
 * VERSION:      October 2, 2019
 * DESCRIPTION:
 * John, where James had had "had had" had had "had." "Had had" had had the teacher's approval.
 *
 * REVISION HISTORY:
 * October 2, 2019 : Initial Implementation
 *
 * */

// TODO
// Favorite List of Stores
// Load Info From Database. requestUser() function?
//

package com.example.gogobogo;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserAccount
{

    /* Instance Variables */
    private String          m_username;
    private String          m_password;
    //private String          m_shoppingListId;
    private ArrayList<Product>    m_shoppingList;



    /* Constructors */

    public UserAccount ()
    {
        this(null, null);
    }

    public UserAccount (String username, String password)
    {
        this(username, password, new ArrayList<Product>());
    }

    public UserAccount (String username, String password, ArrayList<Product> shoppingList)
    {
        this.m_username = username;
        this.m_password = password;
        this.m_shoppingList = shoppingList;

        // Generate List Id
        //this.m_shoppingListId = generateShoppingListId();
    }

    /* Methods */



    /* Getters and Setters */
    public void addProductToList(Product product) // TODO : Remove and verify
    {
        this.m_shoppingList.add(product);
    }



    public ArrayList<Product> getShoppingList()
    {
        return this.m_shoppingList;
    }

    public void setShoppingList(ArrayList<Product> shoppingList)
    {
        this.m_shoppingList = shoppingList;
    }

    public String getUserName() {
        return m_username;
    }

    public void setUserName(String userName) {
        this.m_username = userName;
    }

    public String getPassword() {
        return m_password;
    }

    public void setPassword(String password) {
        this.m_password = password;
    }

} // end class
