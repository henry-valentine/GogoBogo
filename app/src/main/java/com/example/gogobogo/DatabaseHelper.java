package com.example.gogobogo;

import android.util.Log;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DatabaseHelper
{
    private static final String USER_DB_NAME = "users";
    private static final String PRODUCT_DB_NAME = "products";

    private OnUserAccountReceived onUserAccountReceivedListener;
    private OnProductListReceived onProductListReceivedListener;

    private FirebaseFirestore m_database = FirebaseFirestore.getInstance();

    private UserAccount m_tmpUser = null;


    public DatabaseHelper()
    {

    }

    public void addUser(String name, String password)
    {
        UserAccount newUser = new UserAccount(name, password);
        m_database.collection(USER_DB_NAME)
                .add(newUser);
    }

    public void addUser(UserAccount user)
    {
        DocumentReference doc = m_database.collection(USER_DB_NAME).document();

        user.setUserID(doc.getId());

        doc.set(user);
    }

    public void updateUser()
    {
        UserAccount user =  MainActivity.getMainActivity().getGogoBogo().getUserAccount();
        updateUser(user);
    }

    public void updateUser(UserAccount user)
    {
        m_database.collection(USER_DB_NAME).document(user.getUserID()).set(user);
    }



    public void requestUser(final String username, String password)
    {
        m_tmpUser = new UserAccount(username, password);

        m_database.collection(USER_DB_NAME).whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                UserAccount user = null;

                if (task.isSuccessful()) {

                    if (task.getResult().size() == 1) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.println(Log.ASSERT, "LOGIN INFO", "Found user");
                            user = document.toObject(UserAccount.class);


                            if (user.getPassword().equals(m_tmpUser.getPassword()))          // Valid password
                            {
                                Log.println(Log.ASSERT, "LOGIN INFO", "Valid Credentials");
                                user.setUserID(document.getId());
                                onUserAccountReceivedListener.onRetrieval(user);
                            }

                            else                                                            // Invalid password
                            {
                                Log.println(Log.ASSERT, "LOGIN INFO", "Invalid Credentials " + m_tmpUser.getPassword() + " and got " + user.getPassword());
                                onUserAccountReceivedListener.onRetrieval(null);
                            }
                        }
                    }

                    else {
                        Log.println(Log.ASSERT, "LOGIN INFO", "FAILED TO FIND USER? Number of users found by given name (" + m_tmpUser.getUsername() + "): " + task.getResult().size());
                        onUserAccountReceivedListener.onRetrieval(null);
                    }
                }
            }
        });
    }


    public void getUserByName(String name)
    {
        // Retrieves any user based on name, and retrieves a null account if user does not exist.

        m_tmpUser = new UserAccount(name, null);

        m_database.collection(USER_DB_NAME).whereEqualTo("username", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                UserAccount user = null;
                if (task.isSuccessful())
                {
                    if (task.getResult().size() == 1)
                    {
                        for (QueryDocumentSnapshot document : task.getResult())
                        {
                            user = document.toObject(UserAccount.class);
                            user.setUserID(document.getId());
                        }
                    }

                    else
                        Log.e("DATABASE", "MULTIPLE USER'S WITH SAME NAME!");

                    onUserAccountReceivedListener.onRetrieval(user);
                }
            }
        });
    }

    public void updateProduct(Product product)
    {
        m_database.collection(PRODUCT_DB_NAME).document(product.getProductId()).set(product);
    }

    public void addProduct(Product product)
    {
        DocumentReference doc = m_database.collection(PRODUCT_DB_NAME).document();

        product.setProductId(doc.getId());

        doc.set(product);
    }

    public void addProduct(String name, String store, float price, String deal, int id, int upvotes, int downvotes)
    {
        Product product = new Product(name, store, deal, price, upvotes, downvotes, null);

        addProduct(product);
    }

    public void addProduct(ArrayList<Product> products)
    {
        for (Product product : products)
        {
            addProduct(product);
        }
    }

    public Product getProduct(String id)
    {
        Product result = null;

        m_database.collection(PRODUCT_DB_NAME).document(id);

        return result;
    }

    public void removeProduct(@NonNull String productID, OnSuccessListener deleteListener)
    {

        m_database.collection(PRODUCT_DB_NAME).document(productID).delete()
                .addOnSuccessListener(deleteListener)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DATABASE", "ERROR DELETING PRODUCT?");
                    }
                });


    }

    public void getAllProducts()
    {
        // TODO : TESTTTT MEEEE! VERIFFYYY MEEEE!
        m_database.collection(PRODUCT_DB_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Product> result = new ArrayList<>();

                if (task.isSuccessful())
                {
                    Log.println(Log.ASSERT, "INFO", "Getting products...");
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        result.add(document.toObject(Product.class));
                    }

                    onProductListReceivedListener.onRetrieval(result);
                }
            }
        });
    }

    public interface OnUserAccountReceived
    {
        void onRetrieval(UserAccount user);
    }

    public interface OnProductListReceived
    {
        void onRetrieval(ArrayList<Product> products);
    }

    public OnUserAccountReceived getOnUserAccountReceivedListener() {
        return onUserAccountReceivedListener;
    }

    public void setOnUserAccountReceivedListener(OnUserAccountReceived onUserAccountReceivedlistener) {
        this.onUserAccountReceivedListener = onUserAccountReceivedlistener;
    }

    public OnProductListReceived getOnProductListReceivedListener()
    {
        return this.onProductListReceivedListener;
    }

    public void setOnProductListReceivedListener(OnProductListReceived listener)
    {
        this.onProductListReceivedListener = listener;
    }

}
