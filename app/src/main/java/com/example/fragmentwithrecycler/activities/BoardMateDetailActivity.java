package com.example.fragmentwithrecycler.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.mFragments.BoardMateAddFragment;
import com.example.fragmentwithrecycler.mFragments.BoardMateViewFragment;


public class BoardMateDetailActivity extends AppCompatActivity{

    public static final String ADD_BOARDMATE =
            "com.example.recyclerwithdatabasedemo.activities.ADD_BOARDMATE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_mate_detail);
        createAddFragment();
    }

    private void createAddFragment(){
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.BOARDMATE_NAME);
        long id = intent.getLongExtra(MainActivity.BOARDMATE_ID,0);
        String add = intent.getStringExtra(MainActivity.BOARDMATE_ADDRESS);
        String number = intent.getStringExtra(MainActivity.BOARDMATE_NUMBER);
        String status  = intent.getStringExtra(MainActivity.BOARDMATE_STATUS);
        double amount = intent.getDoubleExtra(MainActivity.BOARDMATE_AMOUNT,0);
        String date = intent.getStringExtra(MainActivity.BOARDMATE_DATE);
        MainActivity.FRAGMENT_TO_LOAD ftl = (MainActivity.FRAGMENT_TO_LOAD)
                intent.getSerializableExtra(MainActivity.FRAGMENT_TO_LAUNCH);

        // For fragment manager: DEFAULT
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();


        switch (ftl){
            case ADD:
                BoardMateAddFragment bAddFragment = new BoardMateAddFragment();
                fTransaction.add(R.id.activity_board_mate_detail, bAddFragment, "BOARDMATE_ADD_FRAGMENT");
                Bundle b = new Bundle();
                b.putBoolean(ADD_BOARDMATE, true);
                bAddFragment.setArguments(b);

                setTitle("ADD BOARDMATE");
                break;

            case VIEW:
               BoardMateViewFragment bViewFragment = new BoardMateViewFragment();
                fTransaction.add(R.id.activity_board_mate_detail, bViewFragment, "BOARDMATE_VIEW_FRAGMENT");

                // Passing the listview items clicked to the BoardMateViewFragment
                Bundle bundle = new Bundle();
                bundle.putLong(MainActivity.BOARDMATE_ID, id);
                bundle.putString(MainActivity.BOARDMATE_NAME, name);
                bundle.putString(MainActivity.BOARDMATE_ADDRESS, add);
                bundle.putString(MainActivity.BOARDMATE_NUMBER, number);
                bundle.putString(MainActivity.BOARDMATE_STATUS, status);
                bundle.putDouble(MainActivity.BOARDMATE_AMOUNT, amount);
                bundle.putString(MainActivity.BOARDMATE_DATE, date);
                bViewFragment.setArguments(bundle);
                setTitle("VIEW FRAGMENT");

            case EDIT:
                BoardMateAddFragment bEditFragment = new BoardMateAddFragment();
                fTransaction.add(R.id.activity_board_mate_detail, bEditFragment, "BOARDMATE_EDIT_FRAGMENT");

                // Passing the the listview items clicked to the BoardMateAddFragment
                Bundle bundle1 = new Bundle();
                bundle1.putBoolean(ADD_BOARDMATE, false);
                bundle1.putLong(MainActivity.BOARDMATE_ID, id);
                bundle1.putString(MainActivity.BOARDMATE_NAME, name);
                bundle1.putString(MainActivity.BOARDMATE_ADDRESS, add);
                bundle1.putString(MainActivity.BOARDMATE_NUMBER, number);
                bundle1.putString(MainActivity.BOARDMATE_STATUS, status);
                bundle1.putDouble(MainActivity.BOARDMATE_AMOUNT, amount);
                bundle1.putString(MainActivity.BOARDMATE_DATE, date);
                bEditFragment.setArguments(bundle1);
                setTitle("EDIT FRAGMENT");
                break;
        }
        fTransaction.commit();
    }
}
