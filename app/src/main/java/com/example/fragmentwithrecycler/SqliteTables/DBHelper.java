package com.example.fragmentwithrecycler.SqliteTables;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // Boardmates table property
    public static final String TB_BOARDMATES = "boardmates_tb";
    public static final String COLUMN_BOARDMATE_ID = "b_id";
    public static final String COLUMN_BOARDMATE_NAME = "b_name";
    public static final String COLUMN_BOARDMATE_ADDRESS = "b_address";
    public static final String COLUMN_BOARDMATE_NUMBER = "b_number";
    public static final String COLUMN_BOARDMATE_PAYABLE = "b_payable";
    public static final String COLUMN_BOARDMATE_STATUS = "b_status";

    // Expenses table property
    public static final String TB_EXPENSES = "expenses_tb";
    public static final String COLUMN_EXPENSES_ID = "e_id";
    public static final String COLUMN_EXPENSES_NAME = "e_name";
    public static final String COLUMN_EXPENSES_AMOUNT = "e_amount";

    // Cash table property
    public static final String TB_CASH = "cash_tb";
    public static final String COLUMN_CASH_ID = "c_id";
    public static final String COLUMN_CASH_TOTAL = "t_total";

    private static final String DB_NAME = "boardinghouse.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_QUERY_TABLE_BOARDMATES = "CREATE TABLE " + TB_BOARDMATES + "( "
            + COLUMN_BOARDMATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_BOARDMATE_NAME + " TEXT NOT NULL, "
            + COLUMN_BOARDMATE_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_BOARDMATE_NUMBER + " TEXT NOT NULL, "
            + COLUMN_BOARDMATE_PAYABLE + " REAL NOT NULL, "
            + COLUMN_BOARDMATE_STATUS + " TEXT NOT NULL );";

    private static final String CREATE_QUERY_TABLE_EXPENSES = "CREATE TABLE " + TB_EXPENSES + "( "
            + COLUMN_EXPENSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EXPENSES_NAME + " TEXT NOT NULL, "
            + COLUMN_EXPENSES_AMOUNT + " REAL NOT NULL );";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY_TABLE_BOARDMATES);
        db.execSQL(CREATE_QUERY_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.w(TAG, "Upgrading the database from version " + i + " to "+ i1 );

        db.execSQL("DROP TABLE IF EXISTS " + TB_BOARDMATES);
        db.execSQL("DROP TABLE IF EXISTS " + TB_EXPENSES);

        onCreate(db);
    }
}
