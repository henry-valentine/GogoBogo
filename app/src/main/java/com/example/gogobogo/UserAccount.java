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

public class UserAccount
{
    /* Static Variables */
    private static int nextUserId = 0;

    /* Instance Variables */
    private int             m_userId;
    private String          m_userName;
    private String          m_password;
    private String          m_emailAddress;

    private ShoppingList    shoppingList;

    /* Constructors */
    public UserAccount (String userName, String password)
    {
        this.m_userName = userName;
        this.m_password = password;

        this.shoppingList = new ShoppingList();

        // Set User ID
        this.m_userId = nextUserId;
        nextUserId++;

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

    public String getEmailAddress() {
        return m_emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.m_emailAddress = emailAddress;
    }
} // end class
