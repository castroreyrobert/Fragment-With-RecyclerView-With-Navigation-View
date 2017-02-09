package com.example.fragmentwithrecycler.SqliteTables;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fragmentwithrecycler.models.Expenses;

import java.util.ArrayList;
import java.util.List;

public class ExpensesDB {

    public static final String TAG = "ExpensesDB";

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;

    private String[] mAllColumns = {DBHelper.COLUMN_EXPENSES_ID, DBHelper.COLUMN_EXPENSES_NAME,
            DBHelper.COLUMN_EXPENSES_AMOUNT, DBHelper.COLUMN_EXPENSES_DATE};

    public ExpensesDB(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // Opens the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database:  " + e.getMessage());
        }
    }
    public void open()throws SQLException{
            mDatabase = mDbHelper.getWritableDatabase();
        }
    public void close(){
        mDbHelper.close();
    }

    public Expenses addExpenses(String name, double amount, String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_EXPENSES_NAME, name);
        contentValues.put(DBHelper.COLUMN_EXPENSES_AMOUNT, amount);
        contentValues.put(DBHelper.COLUMN_EXPENSES_DATE, date);

        long insertID = mDatabase.insert(DBHelper.TB_EXPENSES,null,contentValues);
        Cursor res = mDatabase.query(DBHelper.TB_EXPENSES, mAllColumns,
                DBHelper.COLUMN_EXPENSES_ID + " = " + insertID, null,null,null,null);
        res.moveToFirst();
        Expenses newExpense = cursorToExpense(res);
        res.close();
        return newExpense;
    }

    public long updateExpenses(long idToUpdate, String name, double amount, String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_EXPENSES_NAME, name);
        contentValues.put(DBHelper.COLUMN_EXPENSES_AMOUNT, amount);
        contentValues.put(DBHelper.COLUMN_EXPENSES_DATE, date);

        return mDatabase.update(DBHelper.TB_EXPENSES, contentValues,
                DBHelper.COLUMN_EXPENSES_ID + " = "  + idToUpdate, null);
    }

    public long deleteExpenses(long idToDelete){
        return mDatabase.delete(DBHelper.TB_EXPENSES, DBHelper.COLUMN_EXPENSES_ID + " = " + idToDelete,
                null);
    }

    public List<Expenses> getAllExpenses(){
        List<Expenses> listExpenses = new ArrayList<Expenses>();
        String query = "SELECT * FROM " + DBHelper.TB_EXPENSES;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                Expenses expenses = new Expenses();
                expenses.seteId(cursor.getLong(0));
                expenses.seteName(cursor.getString(1));
                expenses.seteAmount(cursor.getDouble(2));
                expenses.seteDate(cursor.getString(3));
                listExpenses.add(expenses);
            }while (cursor.moveToNext());
        }
        return listExpenses;
    }

    private Expenses cursorToExpense(Cursor cursor){
        Expenses newExpense = new Expenses(cursor.getLong(0),cursor.getString(1),cursor.getDouble(2),
                cursor.getString(3));
        return newExpense;
    }

}
