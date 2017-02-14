package com.example.fragmentwithrecycler.mFragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.BoardMatesDB;
import com.example.fragmentwithrecycler.SqliteTables.ExpensesDB;
import com.example.fragmentwithrecycler.models.BoardMate;
import com.example.fragmentwithrecycler.models.Expenses;

import java.util.ArrayList;
import java.util.List;


public class CashFragment extends Fragment {

    private TextView bTotal, eTotal, totalAmount;
    List<Expenses> expensesList;
    ExpensesDB expensesDB;

    List<BoardMate> boardMateList;
    BoardMatesDB boardMatesDB;

    public CashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_cash, container, false);
        bTotal = (TextView) itemView.findViewById(R.id.boardMatesTotalAmount);
        eTotal = (TextView)itemView.findViewById(R.id.expensesTotalAmount);
        totalAmount = (TextView)itemView.findViewById(R.id.totalAmount);
        setTextViews();
        return itemView;
    }


    private void setTextViews(){
        bTotal.setText(String.valueOf(totalBoardMateAmount()));
        eTotal.setText(String.valueOf(totalExpenses()));
        if (totalBoardMateAmount()<totalExpenses()){
            totalAmount.setTextColor(Color.RED);
        }
        totalAmount.setText(String.valueOf(totalBoardMateAmount() - totalExpenses()));
    }

    private double totalExpenses(){
        // For the total of expenses amount;
        double total = 0;

        expensesDB = new ExpensesDB(getContext());
        expensesDB.open();
        expensesList = new ArrayList<>();
        expensesList = expensesDB.getAllExpenses();
        for (int i = 0; i < expensesList.size(); i++){
            total +=  expensesList.get(i).geteAmount();
        }
        return total;
    }


    private double totalBoardMateAmount(){
        // For the total of boardmate payable
        double totalBoardMate = 0;
        boardMatesDB = new BoardMatesDB(getContext());
        boardMatesDB.open();
        boardMateList = new ArrayList<>();
        boardMateList = boardMatesDB.getAllBoardMates();
        for (int i=0; i<boardMateList.size(); i++){
            totalBoardMate += boardMateList.get(i).getmPayable();
        }
        return  totalBoardMate;
    }
}
