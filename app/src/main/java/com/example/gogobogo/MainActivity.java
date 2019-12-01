package com.example.gogobogo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private GenericEditor.OnInputListener productEditorListener = new GenericEditor.OnInputListener() {
        @Override
        public void sendInput(ArrayList<GenericEditor.GERow> dialogData)
        {
            if (!dialogData.get(0).getFieldString().isEmpty() && !dialogData.get(1).getFieldString().isEmpty() && !dialogData.get(2).getFieldString().isEmpty() && !dialogData.get(3).getFieldString().isEmpty()) {
                gogoBogo.addProduct(new Product(
                        dialogData.get(0).getEditable().getText().toString(),
                        dialogData.get(1).getEditable().getText().toString(),
                        dialogData.get(2).getEditable().getText().toString(),
                        Float.parseFloat(dialogData.get(3).getEditable().getText().toString()
                        )
                ));
            }
            else {
                Log.e("PRODUCT ERROR", "Failed to init product due to empty input field...");
            }
        }
    };

    private static MainActivity activity;
    private GogoBogo gogoBogo;

    private AppBarConfiguration mAppBarConfiguration;

    private Button logoutButton;


    // TODO TESTING
//    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Generated Code //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Static reference to application context
        MainActivity.activity = this;
        gogoBogo = new GogoBogo();

        // Start login activity, which then waits for completion before returning to main
        transferToLoginActivity();
        DatabaseHelper dbh = new DatabaseHelper();
        dbh.setOnProductListReceivedListener(new DatabaseHelper.OnProductListReceived() {
            @Override
            public void onRetrieval(ArrayList<Product> products) {
                gogoBogo.setAllProducts(products);
            }
        });

        dbh.getAllProducts();

        // FAB ACTION //
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creating New Deal", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Add a New Deal Here
                openProductEditor();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_shopping_list)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    public void transferToLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Opens the editor for the given product
    public void openProductEditor()
    {
        ArrayList<GenericEditor.GERow> rows = new ArrayList<>();
        rows.add(new GenericEditor.GERow("Product", "Product Name", this));
        rows.add(new GenericEditor.GERow("Store", "Store Name", this));
        rows.add(new GenericEditor.GERow("Deal", "Deal Description", this));
        rows.add(new GenericEditor.GERow("Price", "$$$", this));


        GenericEditor pEditor = new GenericEditor("Product Editor", rows);
        pEditor.setOnInputListener(productEditorListener);

        pEditor.show(getSupportFragmentManager(), "ProductEditor");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    /* Custom Event Handlers */
    public void searchMenu(View view)
    {
        // Handles the Click from the search menu
        SearchOptions searchOptions = new SearchOptions(this.gogoBogo);
        searchOptions.show(getSupportFragmentManager(), "test");
    }

    public GogoBogo getGogoBogo()
    {
        return gogoBogo;
    }

    public void setGogoBogo(GogoBogo gogoBogo)
    {
        this.gogoBogo = gogoBogo;
    }

    public static MainActivity getMainActivity() {
        return activity;
    }
}
