package com.example.fragmentwithrecycler.SqliteTables;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fragmentwithrecycler.models.BoardMate;

import java.util.ArrayList;
import java.util.List;

public class    BoardMatesDB {

    public static final String TAG = "BoardmatesDB";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private Context mContext;
    private String[] mAllColumns = {DBHelper.COLUMN_BOARDMATE_ID, DBHelper.COLUMN_BOARDMATE_NAME,
            DBHelper.COLUMN_BOARDMATE_ADDRESS, DBHelper.COLUMN_BOARDMATE_NUMBER,
            DBHelper.COLUMN_BOARDMATE_PAYABLE, DBHelper.COLUMN_BOARDMATE_PAYABLE};

    public BoardMatesDB(Context context){
        this.mContext = context;
        mDBHelper = new DBHelper(context);
        // Opens the database
        try {
            open();
        } catch (SQLException e){
            Log.e(TAG, "SQLException on openning database " + e.getMessage() );
        }
    }

    public void open()throws SQLException{
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close(){mDBHelper.close();}

    public BoardMate addBoardmate(String name, String address, String number, double payable,
                                  String status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_BOARDMATE_NAME, name);
        contentValues.put(DBHelper.COLUMN_BOARDMATE_ADDRESS, address);
        contentValues.put(DBHelper.COLUMN_BOARDMATE_NUMBER, number);
        contentValues.put(DBHelper.COLUMN_BOARDMATE_PAYABLE, payable);
        contentValues.put(DBHelper.COLUMN_BOARDMATE_STATUS, status + "");

        long insertID = mDatabase.insert(DBHelper.TB_BOARDMATES, null, contentValues);
        Cursor res = mDatabase.query(DBHelper.TB_BOARDMATES,mAllColumns,
                DBHelper.COLUMN_BOARDMATE_ID + " = " + insertID, null,null,null,null);
        res.moveToFirst();
        BoardMate newBMate = cursorToBoardMate(res);
        res.close();
        return newBMate;
    }

    public long updateBoardMate(long idToUpdate, String name, String address, String number,
                                double amount, String stats){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_BOARDMATE_NAME, name);
        contentValues.put(DBHelper.COLUMN_BOARDMATE_ADDRESS, address);
        contentValues.put(DBHelper.COLUMN_BOARDMATE_NUMBER, number);
        contentValues.put(DBHelper.COLUMN_BOARDMATE_PAYABLE, amount);
        contentValues.put(DBHelper.COLUMN_BOARDMATE_STATUS, stats);

        return mDatabase.update(DBHelper.TB_BOARDMATES,contentValues,
                DBHelper.COLUMN_BOARDMATE_ID + " = " + idToUpdate, null);
    }

    public long deleteBoardMate(long idToDelete){
        return  mDatabase.delete(DBHelper.TB_BOARDMATES, DBHelper.COLUMN_BOARDMATE_ID + " = "
                + idToDelete, null);
    }

    public List<BoardMate> getAllBoardMates(){
        List<BoardMate> listBoardMates = new ArrayList<BoardMate>();
        String query = "SELECT * FROM " + DBHelper.TB_BOARDMATES;

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                BoardMate boardMate = new BoardMate();
                boardMate.setmId(cursor.getLong(0));
                boardMate.setmName(cursor.getString(1));
                boardMate.setmAddress(cursor.getString(2));
                boardMate.setmNumber(cursor.getString(3));
                boardMate.setmPayable(cursor.getDouble(4));
                boardMate.setmStatus(cursor.getString(5));
                listBoardMates.add(boardMate);
            }while (cursor.moveToNext());
        }

        return listBoardMates;
    }

    private BoardMate cursorToBoardMate(Cursor cursor){
        BoardMate newBoardMate = new BoardMate(cursor.getLong(0), cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getDouble(4),cursor.getString(5));
        return newBoardMate;
    }
}
