package com.example.fragmentwithrecycler.mFragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.ExpensesDB;
import com.example.fragmentwithrecycler.adapters.RecyclerAdapterExpenses;
import com.example.fragmentwithrecycler.models.Expenses;

import java.util.ArrayList;
import java.util.List;


public class ExpensesListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    List<Expenses> expensesList;
    ExpensesDB expensesDB;

    public ExpensesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_expenses_list, null);
        expensesDB = new ExpensesDB(getActivity());
        expensesDB.open();

        expensesList = new ArrayList<>();
        expensesList = expensesDB.getAllExpenses();

        // Reference
        recyclerView = (RecyclerView) rootView.findViewById(R.id.Expenses_RV);
        recyclerView.setHasFixedSize(true);

        // LayOut Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Adapter
        mAdapter = new RecyclerAdapterExpenses(getActivity(), expensesList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return rootView;
    }
}
