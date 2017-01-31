package com.example.fragmentwithrecycler.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.models.Expenses;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapterExpenses extends RecyclerView.Adapter<RecyclerAdapterExpenses.ViewHolder> {

    static List<Expenses> expensesList;
    static Context context1;

    public RecyclerAdapterExpenses(Context context, List<Expenses> expensesList){
        this.expensesList = new ArrayList<Expenses>();
        this.context1 = context;
        this.expensesList = expensesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.item_expenses, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mExpensesName.setText(expensesList.get(position).geteName());
        holder.mPrice.setText(String.valueOf(expensesList.get(position).geteAmount()));
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mExpensesName, mPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            mExpensesName = (TextView)itemView.findViewById(R.id.textViewExpensesName);
            mPrice = (TextView)itemView.findViewById(R.id.textViewPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            createDialog();
        }

        private void createDialog(){
            AlertDialog.Builder alertBuilder =
                    new AlertDialog.Builder(context1, android.R.style.Theme_Material_Dialog_NoActionBar);
            alertBuilder.setTitle(expensesList.get(getAdapterPosition()).geteName());
            alertBuilder.setMessage(String.valueOf(expensesList.get(getAdapterPosition()).geteAmount()));
            AlertDialog b = alertBuilder.create();
            b.show();
        }
    }
}
