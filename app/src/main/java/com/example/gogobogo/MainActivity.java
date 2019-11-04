package com.example.gogobogo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
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
            // TODO : What is type - and does it need to be assigned by user?
            gogoBogo.addProduct(new Product(
                    "NONE?",
                    dialogData.get(0).getEditable().getText().toString(),
                    dialogData.get(1).getEditable().getText().toString(),
                    dialogData.get(2).getEditable().getText().toString(),
                    Float.parseFloat(dialogData.get(3).getEditable().getText().toString())
            ));
        }
    };

    public static MainActivity activity;
    private GogoBogo gogoBogo;

    private AppBarConfiguration mAppBarConfiguration;


    @RequiresApi(api = Build.VERSION_CODES.M)
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

        //give premissions //
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            System.out.println("Permission Granted");
            boolean v = true;
        } else {
            System.out.println("Permission Denied");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            boolean v = false;
        }

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
                R.id.nav_home, R.id.nav_shopping_list, R.id.nav_maps)
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
}
