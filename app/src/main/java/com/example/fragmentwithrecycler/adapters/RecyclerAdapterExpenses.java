package com.example.fragmentwithrecycler.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.ExpensesDB;
import com.example.fragmentwithrecycler.activities.ExpensesDetailActivity;
import com.example.fragmentwithrecycler.activities.MainActivity;
import com.example.fragmentwithrecycler.models.Expenses;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapterExpenses extends RecyclerView.Adapter<RecyclerAdapterExpenses.ViewHolder> {

    static List<Expenses> expensesList;
    static Context context1;
    static ExpensesDB dbHelper;

    public RecyclerAdapterExpenses(Context context, List<Expenses> expensesList) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView mExpensesName, mPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            mExpensesName = (TextView) itemView.findViewById(R.id.textViewExpensesName);
            mPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            createDialog();
        }

        private void createDialog() {
            AlertDialog.Builder alertBuilder =
                    new AlertDialog.Builder(context1, android.R.style.Theme_Material_Dialog_NoActionBar);
            alertBuilder.setTitle(expensesList.get(getAdapterPosition()).geteName());
            alertBuilder.setMessage(String.valueOf(expensesList.get(getAdapterPosition()).geteAmount()));
            AlertDialog b = alertBuilder.create();
            b.show();
        }

        @Override
        public boolean onLongClick(View view) {
            createPop(view);
            return false;
        }

        private void createPop(final View view) {
            PopupMenu popup = new PopupMenu(context1, view);
            popup.setGravity(Gravity.CENTER);
            popup.inflate(R.menu.menu_long_press);
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.menu_edit:
                            //TODO:
                            Intent intent = new Intent(context1, ExpensesDetailActivity.class);
                            intent.putExtra(MainActivity.FRAGMENT_TO_LAUNCH, MainActivity.FRAGMENT_TO_LOAD.EDIT);
                            intent.putExtra(MainActivity.EXPENSES_ID,expensesList.get(getAdapterPosition()).geteId());
                            intent.putExtra(MainActivity.EXPENSES_NAME, expensesList.get(getAdapterPosition()).geteName());
                            intent.putExtra(MainActivity.EXPENSES_AMOUNT, expensesList.get(getAdapterPosition()).geteAmount());
                            intent.putExtra(MainActivity.EXPENSES_DATE, expensesList.get(getAdapterPosition()).geteDate());
                            context1.startActivity(intent);
                            break;
                        case R.id.menu_delete:
                            dbHelper = new ExpensesDB(context1);
                            dbHelper.open();
                            dbHelper.deleteExpenses(expensesList.get(getAdapterPosition()).geteId());
                            Intent intent1 = new Intent(context1, MainActivity.class);
                            intent1.putExtra(MainActivity.NAVIGATION_TO_LAUNCH, MainActivity.NAVIGATION_TO_LOAD.EXPENSES);
                            context1.startActivity(intent1);
                            dbHelper.close();
                    }
                    return false;
                }
            });

        }
    }
}
