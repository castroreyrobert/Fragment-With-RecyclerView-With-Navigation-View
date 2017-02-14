package com.example.fragmentwithrecycler.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.BoardMatesDB;
import com.example.fragmentwithrecycler.SqliteTables.ExpensesDB;
import com.example.fragmentwithrecycler.mFragments.BoardMateListFragment;
import com.example.fragmentwithrecycler.mFragments.CashFragment;
import com.example.fragmentwithrecycler.mFragments.ExpensesListFragment;
import com.example.fragmentwithrecycler.models.BoardMate;
import com.example.fragmentwithrecycler.models.Expenses;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public static final String BOARDMATE_DATE =
            "com.example.recyclerwithdatabasedemo.activities.BOARDMATE_DATE";
    public static final String FRAGMENT_TO_LAUNCH =
            "com.example.recyclerwithdatabasedemo.activities.FRAGMENT_TO_LAUNCH";
    public static final String NAVIGATION_TO_LAUNCH =
            "com.example.fragmentwithrecycler.activities.NAVIGATION_TO_LAUNCH";
    public static final String EXPENSES_ID =
            "com.example.fragmentwithrecycler.activities.EXPENSES_ID";
    public static final String EXPENSES_NAME =
            "com.example.fragmentwithrecycler.activities.EXPENSES_NAME";
    public static final String EXPENSES_AMOUNT =
            "com.example.fragmentwithrecycler.activities.EXPENSES_AMOUNT";
    public static final String EXPENSES_DATE =
            "com.example.fragmentwithrecycler.activities.EXPENSES_DATE";

    public enum FRAGMENT_TO_LOAD {VIEW, EDIT, ADD}
    public enum NAVIGATION_TO_LOAD {EXPENSES, BOARDMATE,CASH}
    public static NAVIGATION_TO_LOAD navigation_to_load;

    private FragmentManager fManager;
    private FragmentTransaction fTransaction;

    List<Expenses> expensesList;
    ExpensesDB expensesDB;

    List<BoardMate> boardMateList;
    BoardMatesDB boardMatesDB;

    private Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabMore);
        FloatingActionButton fabAdd = (FloatingActionButton)findViewById(R.id.fabAdd);
        FloatingActionButton fabTotal = (FloatingActionButton)findViewById(R.id.fabTotal);
        final LinearLayout addLayout = (LinearLayout)findViewById(R.id.add_layout);
        final LinearLayout totalLayout = (LinearLayout)findViewById(R.id.total_layout);
        final Animation showButton = AnimationUtils.loadAnimation(MainActivity.this,R.anim.show_button);
        final Animation hideButton = AnimationUtils.loadAnimation(MainActivity.this, R.anim.hide_button);
        final Animation showLayout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_layout);
        final Animation hideLayout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.hide_layout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Intent intent = getIntent();
        navigation_to_load = (NAVIGATION_TO_LOAD) intent.getSerializableExtra(NAVIGATION_TO_LAUNCH);
        if (navigation_to_load != null){
            launchNavigation(navigation_to_load); }
        else{
            navigation_to_load = NAVIGATION_TO_LOAD.BOARDMATE;
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addLayout.getVisibility() == View.VISIBLE && totalLayout.getVisibility() == View.VISIBLE){
                    addLayout.setVisibility(View.GONE);
                    totalLayout.setVisibility(View.GONE);
                    addLayout.startAnimation(hideLayout);
                    totalLayout.startAnimation(hideLayout);
                    fab.startAnimation(hideButton);
                }else {
                    addLayout.setVisibility(View.VISIBLE);
                    totalLayout.setVisibility(View.VISIBLE);
                    addLayout.startAnimation(showLayout);
                    totalLayout.startAnimation(showLayout);
                    fab.startAnimation(showButton);
                }

            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabButtonClicked(navigation_to_load);
            }
        });


        fabTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               makeSnackBar(navigation_to_load,view);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


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
                setTitle("BoardMate List");
                navigation_to_load = NAVIGATION_TO_LOAD.BOARDMATE;
                break;
            case EXPENSES:
                ExpensesListFragment eFragment = new ExpensesListFragment();
                fTransaction.replace(R.id.content_main, eFragment, "EXPENSES_LIST_FRAGMENT");
                setTitle("Expenses List");
                navigation_to_load = NAVIGATION_TO_LOAD.EXPENSES;
                break;
            case CASH:
                CashFragment cFragment = new CashFragment();
                fTransaction.replace(R.id.content_main, cFragment, "CASH_COMPUTATION");
                setTitle("Cash Tracker");
                navigation_to_load = NAVIGATION_TO_LOAD.CASH;
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

        }else if (id == R.id.nav_cash){
            CashFragment cFragment = new CashFragment();
            fTransaction.replace(R.id.content_main, cFragment, "CASH_COMPUTATION");
            setTitle("Cash Tracker");
            navigation_to_load = NAVIGATION_TO_LOAD.CASH;

        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        fTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static String calculateDate(){
        Date dateNow = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance();
        String sDate = dateFormat.format(dateNow);
        return sDate;
    }


    public static double formatNumber(double amount){
        DecimalFormat df1 = new DecimalFormat("####.00");
        return Double.parseDouble(df1.format(amount));
    }


    public double totalExpenses(){
        // For the total of expenses amount;
        double total = 0;

        expensesDB = new ExpensesDB(this);
        expensesDB.open();
        expensesList = new ArrayList<>();
        expensesList = expensesDB.getAllExpenses();
        for (int i = 0; i < expensesList.size(); i++){
            total +=  expensesList.get(i).geteAmount();
        }
       return total;
    }


    public double totalBoardMateAmount(){
        // For the total of boardmate payable
        double totalBoardMate = 0;
        boardMatesDB = new BoardMatesDB(getApplication());
        boardMatesDB.open();
        boardMateList = new ArrayList<>();
        boardMateList = boardMatesDB.getAllBoardMates();
        for (int i=0; i<boardMateList.size(); i++){
            totalBoardMate += boardMateList.get(i).getmPayable();
        }
        return  totalBoardMate;
    }

    private void makeSnackBar(NAVIGATION_TO_LOAD ntl,View view){
        switch (ntl){
            case EXPENSES:
                snackbar = Snackbar.make(view, "Total: " + String.valueOf(totalExpenses())
                        ,Snackbar.LENGTH_INDEFINITE)
                        .setAction("HIDE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Handles user clicks the HIDE
                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.CYAN);
                snackbar.show();
                break;
            case BOARDMATE:
                snackbar = Snackbar.make(view, "Total: " + String.valueOf(totalBoardMateAmount())
                        ,Snackbar.LENGTH_INDEFINITE)
                        .setAction("HIDE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Handles user clicks the HIDE
                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
                TextView tv1 = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                tv1.setTextColor(Color.CYAN);
                snackbar.show();
        }
    }

}
