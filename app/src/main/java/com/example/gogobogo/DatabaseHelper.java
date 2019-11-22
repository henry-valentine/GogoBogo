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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
                    Log.println(Log.ASSERT, "INFO", "Task successful " + m_tmpUser.getUsername() + " " + m_tmpUser.getPassword());

                    if (task.getResult().size() == 1) {
                        Log.println(Log.ASSERT, "INFO", "Single result found");

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.println(Log.ASSERT, "INFO", "Found user");
                            user = document.toObject(UserAccount.class);


                            if (user.getPassword().equals(m_tmpUser.getPassword()))          // Valid password
                            {
                                Log.println(Log.ASSERT, "INFO", "Valid Credentials");
                                user.setUserID(document.getId());
                                onUserAccountReceivedListener.onRetrieval(user);
                            }

                            else                                                            // Invalid password
                            {
                                Log.println(Log.ASSERT, "INFO", "Invalid Credentials " + m_tmpUser.getPassword() + " and got " + user.getPassword());
                                onUserAccountReceivedListener.onRetrieval(null);
                            }
                        }
                    }

                    else {
                        Log.println(Log.ASSERT, "INFO", "Number of Results: " + task.getResult().size());
                        onUserAccountReceivedListener.onRetrieval(null);
                    }
                }

                if (user != null)
                {
                    Log.println(Log.ASSERT, "INFO", "Current Object Name: " + user.getUsername());
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

    public Product getProduct(int id)
    {
        Product result = null;



        return result;
    }

    public ArrayList<Product> getProductsByName(String name)
    {
        // TODO
        ArrayList<Product> result = null;

        return result;
    }

    public ArrayList<Product> getProductByStore(String name)
    {
        // TODO
        ArrayList<Product> result = null;

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

    public String generateID() //TODO. Maybe change the logic so that the products and users will be different Id's although it doesnt big matter
    {
        // Get the current time
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

        // Generate a random int between 0 and 100
        int rand = (int) (Math.random() * 100);

        String id = date.format(new Date()) + "-" + rand;

        Log.d("DEBUG", "Generated new ID: " + id);

        return id;
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
