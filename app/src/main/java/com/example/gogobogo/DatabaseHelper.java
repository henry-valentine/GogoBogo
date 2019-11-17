package com.example.gogobogo;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatabaseHelper
{
    private static final String USER_DB_NAME = "users";
    private static final String PRODUCT_DB_NAME = "products";

    private DatabaseHelper.OnUserAccountReceived onUserAccountReceivedlistener;

    private FirebaseFirestore m_database = FirebaseFirestore.getInstance();

    private UserAccount m_tmpUser = null;


    public DatabaseHelper()
    {

    }

    public void addUser(String name, String password)
    {
        UserAccount newUser = new UserAccount(name, password);
        m_database.collection("users")
                .add(newUser);
    }

    public void addUser(UserAccount user)
    {
        m_database.collection("users")
                .add(user);
    }

    public void requestUser(final String username, String password)
    {
        m_tmpUser = new UserAccount(username, password);

        m_database.collection("users").whereEqualTo("userName", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                UserAccount user = null;

                if (task.isSuccessful()) {
                    Log.println(Log.ASSERT, "INFO", "Task successful");

                    if (task.getResult().size() == 1) {
                        Log.println(Log.ASSERT, "INFO", "Single result found");

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.println(Log.ASSERT, "INFO", "Found user");
                            user = document.toObject(UserAccount.class);


                            if (user.getPassword().equals(m_tmpUser.getPassword()))          // Valid password
                            {
                                Log.println(Log.ASSERT, "INFO", "Valid Credentials");
                                onUserAccountReceivedlistener.onRetrieval(user);
                            }

                            else                                                            // Invalid password
                            {
                                Log.println(Log.ASSERT, "INFO", "Invalid Credentials " + m_tmpUser.getPassword() + " and got " + user.getPassword());
                                onUserAccountReceivedlistener.onRetrieval(null);
                            }
                        }
                    }

                    else
                        Log.println(Log.ASSERT, "INFO", "More than one? " +task.getResult().size());
                }

                if (user != null)
                {
                    Log.println(Log.ASSERT, "INFO", "Current Object Name: " + user.getUserName());
                }
            }
        });
    }


    public void getUserByName(String name)
    {
        // Retrieves any user based on name, and retrieves a null account if user does not exist.

        m_tmpUser = new UserAccount(name, null);

        m_database.collection("users").whereEqualTo("userName", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        }
                    }

                    else
                        Log.e("DATABASE", "MULTIPLE USER'S WITH SAME NAME!");

                    onUserAccountReceivedlistener.onRetrieval(user);
                }
            }
        });
    }

    public void addProduct(String name, String store, float price, String deal, int id, int upvotes, int downvotes)
    {

    }

    public Product getProduct(int id)
    {
        Product result = null;

        return result;
    }

    public ArrayList<Product> getProductsByName(String name)
    {
        ArrayList<Product> result = null;

        return result;
    }

    public ArrayList<Product> getProductByStore(String name)
    {
        ArrayList<Product> result = null;

        return result;
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
        public void onRetrieval(UserAccount user);
    }

    public OnUserAccountReceived getOnUserAccountReceivedlistener() {
        return onUserAccountReceivedlistener;
    }

    public void setOnUserAccountReceivedlistener(OnUserAccountReceived onUserAccountReceivedlistener) {
        this.onUserAccountReceivedlistener = onUserAccountReceivedlistener;
    }
}
