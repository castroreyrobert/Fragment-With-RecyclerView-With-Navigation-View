package com.example.fragmentwithrecycler.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.ExpensesDB;


public class ExpensesDetailActivity extends AppCompatActivity {
    
    private static final String TAG = "EXPENSESDETAIL";

    private ExpensesDB dbHelper;
    private boolean editExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_detail);
        showAddExpensesDialog();


    }

    private void showAddExpensesDialog(){
        final Intent intent = getIntent();

        dbHelper = new ExpensesDB(this);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_NoActionBar);
        LayoutInflater inflater= this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_alert_dialog_expenses_, null);
        alertBuilder.setView(dialogView);



        final EditText etEname = (EditText)dialogView.findViewById(R.id.etEname);
        final EditText etPrice = (EditText)dialogView.findViewById(R.id.etEPrice);

        MainActivity.FRAGMENT_TO_LOAD ftl = (MainActivity.FRAGMENT_TO_LOAD) intent.getSerializableExtra(MainActivity.FRAGMENT_TO_LAUNCH);
        switch (ftl){
            case EDIT:
                etEname.setText(intent.getStringExtra(MainActivity.EXPENSES_NAME));
                etPrice.setText(String.valueOf(intent.getDoubleExtra(MainActivity.EXPENSES_AMOUNT,0)));
                editExpenses = true;

        }

        alertBuilder.setTitle("Add Expenses");
        alertBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "onClick:");
                if (!etEname.getText().toString().isEmpty() && !etPrice.getText().toString().isEmpty()){

                    if (editExpenses){
                        dbHelper.updateExpenses(intent.getLongExtra(MainActivity.EXPENSES_ID,0),
                                etEname.getText().toString(), MainActivity.formatNumber(Double.parseDouble(etPrice.getText().toString())),
                                intent.getStringExtra(MainActivity.EXPENSES_DATE));
                    }else {
                        dbHelper.addExpenses(etEname.getText().toString(),
                                MainActivity.formatNumber(Double.parseDouble(etPrice.getText().toString())), MainActivity.calculateDate());
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(MainActivity.NAVIGATION_TO_LAUNCH, MainActivity.NAVIGATION_TO_LOAD.EXPENSES);
                    startActivity(intent);
                    dbHelper.close();
                }else {
                    Toast.makeText(ExpensesDetailActivity.this, "Please fill all the fields!"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });
        AlertDialog b = alertBuilder.create();
        b.show();
    }

}
