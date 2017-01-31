package com.example.fragmentwithrecycler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.mFragments.BoardMateListFragment;
import com.example.fragmentwithrecycler.mFragments.ExpensesListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String BOARDMATE_ID =
            "com.example.recyclerwithdatabasedemo.activities.BOARDMATE_ID";
    public static final String BOARDMATE_NAME =
            "com.example.recyclerwithdatabasedemo.activities.BOARDMATE_NAME";
    public static final String BOARDMATE_ADDRESS =
            "com.example.recyclerwithdatabasedemo.activities.BOARDMATE_ADDRESS";
    public static final String BOARDMATE_NUMBER =
            "com.example.recyclerwithdatabasedemo.activities.BOARDMATE_NUMBER";
    public static final String BOARDMATE_STATUS =
            "com.example.recyclerwithdatabasedemo.activities.BOARDMATE_STATUS";
    public static final String BOARDMATE_AMOUNT =
            "com.example.recyclerwithdatabasedemo.activities.BOARDMATE_AMOUNT";
    public static final String FRAGMENT_TO_LAUNCH =
            "com.example.recyclerwithdatabasedemo.activities.FRAGMENT_TO_LAUNCH";
    public static final String NAVIGATION_TO_LAUNCH =
            "com.example.fragmentwithrecycler.activities.NAVIGATION_TO_LAUNCH";

    public enum FRAGMENT_TO_LOAD {VIEW, EDIT, ADD}
    public enum NAVIGATION_TO_LOAD {EXPENSES, BOARDMATE}
    public static NAVIGATION_TO_LOAD navigation_to_load;



    private FragmentManager fManager;
    private FragmentTransaction fTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        navigation_to_load = (NAVIGATION_TO_LOAD) intent.getSerializableExtra(NAVIGATION_TO_LAUNCH);
        if (navigation_to_load == null)
            navigation_to_load = NAVIGATION_TO_LOAD.BOARDMATE;
        launchNavigation(navigation_to_load);

        Toast.makeText(this, String.valueOf(navigation_to_load), Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabButtonClicked(navigation_to_load);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void onFabButtonClicked(NAVIGATION_TO_LOAD adl){
        switch (adl){
            case BOARDMATE:
                Intent intentAdd = new Intent(getApplicationContext(),BoardMateDetailActivity.class);
                intentAdd.putExtra(FRAGMENT_TO_LAUNCH, FRAGMENT_TO_LOAD.ADD);
                startActivity(intentAdd);
                break;
            case EXPENSES:
                Intent intentAddExpense = new Intent(this,ExpensesDetailActivity.class);
                intentAddExpense.putExtra(FRAGMENT_TO_LAUNCH, FRAGMENT_TO_LOAD.ADD);
                startActivity(intentAddExpense);
                break;
        }
    }

    private void launchNavigation(NAVIGATION_TO_LOAD ntl){
        fManager = getSupportFragmentManager();
        fTransaction = fManager.beginTransaction();
        switch (ntl){
            case BOARDMATE:
                BoardMateListFragment bFragment = new BoardMateListFragment();
                fTransaction.replace(R.id.content_main, bFragment, "BOARDMATE_LIST_FRAGMENT");
               // setTitle("BoardMate List");
                ntl = NAVIGATION_TO_LOAD.BOARDMATE;
                break;
            case EXPENSES:
                ExpensesListFragment eFragment = new ExpensesListFragment();
                fTransaction.replace(R.id.content_main, eFragment, "EXPENSES_LIST_FRAGMENT");
                //setTitle("Expenses List");
                ntl = NAVIGATION_TO_LOAD.EXPENSES;
                break;
            default:
                BoardMateListFragment bFragment2 = new BoardMateListFragment();
                fTransaction.replace(R.id.content_main, bFragment2, "BOARDMATE_LIST_FRAGMENT");
                //setTitle("BoardMate List");
                ntl = NAVIGATION_TO_LOAD.BOARDMATE;
                break;
        }
        fTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fManager = getSupportFragmentManager();
        fTransaction = fManager.beginTransaction();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_boardmate) {
            // Handle the camera action
            BoardMateListFragment bFragment = new BoardMateListFragment();
            fTransaction.replace(R.id.content_main, bFragment, "BOARDMATE_LIST_FRAGMENT");
            setTitle("BoardMate List");
            navigation_to_load = NAVIGATION_TO_LOAD.BOARDMATE;
        } else if (id == R.id.nav_expenses) {
            ExpensesListFragment eFragment = new ExpensesListFragment();
            fTransaction.replace(R.id.content_main, eFragment, "EXPENSES_LIST_FRAGMENT");
            setTitle("Expenses List");
            navigation_to_load = NAVIGATION_TO_LOAD.EXPENSES;

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        fTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
