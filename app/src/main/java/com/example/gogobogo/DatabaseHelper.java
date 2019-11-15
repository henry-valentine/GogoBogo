package com.example.gogobogo;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatabaseHelper {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference();


    public DatabaseHelper() {
    // TODO : ALL BELOW IS TEST CODE
        // TODO: Temp. Created a test document

//        Map<String, Object> user = new HashMap<>();
//            user.put("first", "Test");
//            user.put("last", "test");
//            user.put("username", "testing");
//            user.put("password", "test");
//            user.put("cart_number", 1238);
//
//        // Add a new document with a generated ID
//            db.collection("users")
//                    .add(user)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//        @Override
//        public void onSuccess(DocumentReference documentReference) {
//            Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//        }
//    })
//            .addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception e) {
//            Log.w("TAG", "Error adding document", e);
//        }
//    });
//
//        // Read from database
//            db.collection("users")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d("TAG", document.getId() + " => " + document.getData());
//                    }
//                } else {
//                    Log.w("TAG", "Error getting documents.", task.getException());
//                }
//            }
//        });
    }
    public void writeProduct(String name, String store, String deal, Integer price, String productId, int upvote, int downvote) { // still working on votes, whats best way to do it?
        //        FirebaseUser user =  mAuth.getCurrentUser(); //I dont know if we wont to use this to auth users, but if we did this is how
        //        String userId = user.getUid(); //Not sure exactly what this does, probably can be removed/replaced with id generation?
        Product product = new Product(name, store, deal, price, upvote, downvote, productId); //I dont know how ot do this, but basically if we can get what the user puts in the text fields we wont have to hard code this
        mRef.child("products").child(productId).setValue(product);  //if its easier to have the ID generated here in this class/method, we can do .child(UserID) at the end and avoid passing through the ID as a parm
    }

    public void writeUsers(String userName, String password, String userId) {
        UserAccount user = new UserAccount(userName, password);
        mRef.child("users").child(userId).setValue(user);
        //Log.d("TAG", "DocumentSnapshot added with ID: " + mRef.getId());
    }

    public void changePassowrd(String newpassword, String userId) { //Just here to test updating things
        mRef.child("users").child(userId).child("username").setValue(newpassword);
    }

    public void removeProduct(String userId){
        mRef.child("products").child(userId).removeValue(); //I think this should work the way I think it will, but not sure
    }

    public void getProduct(String name, String productId){ //TODO. Still working on this its meant to read products, same will be done for users
        ValueEventListener productListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Product product = dataSnapshot.getValue(Product.class);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mRef.addValueEventListener(productListener);
    }
}
