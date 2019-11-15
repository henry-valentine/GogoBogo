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
// Load Info From Database. getUser() function?
//

package com.example.gogobogo;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserAccount
{

    /* Instance Variables */
    private String          m_userName;
    private String          m_password;
    private String          m_shoppingListId;

    /* Constructors */
    public UserAccount (String userName, String password)
    {
        this.m_userName = userName;
        this.m_password = password;


        // Generate List Id
        this.m_shoppingListId = generateShoppingListId();

        // getUser() - Get user from database with the above credentials
        // If the database has a matching user, return all the info and populate this object

    }

    /* Methods */


    /* Getters and Setters */
    public String getUserName() {
        return m_userName;
    }

    public void setUserName(String userName) {
        this.m_userName = userName;
    }

    public String getPassword() {
        return m_password;
    }

    public void setPassword(String password) {
        this.m_password = password;
    }

    public String generateShoppingListId() //TODO. Maybe change the logic so that the products and users will be different Id's although it doesnt big matter
    {
        // Get the current time
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

        // Generate a random int between 0 and 100
        int rand = (int) (Math.random() * 100);

        String shoppingListId = date.format(new Date()) + "-" + rand;

        Log.d("DEBUG", "Generated new Shopping List ID: " + shoppingListId);

        return shoppingListId;
    }
} // end class
