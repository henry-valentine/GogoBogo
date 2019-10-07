package com.example.gogobogo;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;


// ADDED IMPORT
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.View.OnClickListener;



public class MainActivity extends AppCompatActivity {

    private GogoBogo gogoBogo;                               // Our Stuff
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Generated Code //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        // FAB ACTION //
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creating New Deal", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Add a New Deal Here
                addDeal(view);

            }
        });




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // Our Code //
        gogoBogo = new GogoBogo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    int i = 0; // TEMP

    /* Custom Event Handlers */

    /**
     *
     * Called whenever the Add Deal Button is pressed
     *
     * @param view
     */
    public void addDeal(View view)
    {
        Log.i("TAG", "YOU PRESSED THE BLUE BUTTON!");

        LinearLayout lm = findViewById(R.id.homeLayout);

        Product tempProduct;

        switch(i)
        {
            case 1:
                tempProduct  = new Product("Hot Dogs", "Walmart",
                                            "Buy One Get One", 500);
                break;
            case 2:
                tempProduct  = new Product("Pineapple", "Publix",
                        "Buy Two Get One", 420);
                break;
            case 3:
                tempProduct  = new Product("Dignity", "Aldi",
                        "Buy Three Get One", 99999);
                break;
            case 4:
                tempProduct  = new Product("Friendship", "Walmart",
                        "OUT OF STOCK", 0);
                break;
            case 5:
                tempProduct  = new Product("Pickled Ice Cream", "Some Guy On the Street",
                        "I'll Pay You to Eat It", 20);
                break;
            case 6:
                tempProduct  = new Product("Fresh Memes", "Reddit",
                        "New Look, Same Great Dankness", 69420);
                break;
            case 7:
                tempProduct  = new Product("Education", "Embry-Riddle",
                        "You Give Us All Your Money", 160000);
                break;
            case 8:
                tempProduct  = new Product("Parking Spot", "Embry-Riddle",
                        "Currently Unavailable", 9999999);
                break;
            default:
                tempProduct  = new Product("Stuff", "Stuff-Mart",
                        "No Deals Available", 999);
        }

        i++;

        // Add Product to Home Screen.
        tempProduct.addToLayout(this, lm);

        // For Now, just generate a new deal and add it to the home page



    }


}
