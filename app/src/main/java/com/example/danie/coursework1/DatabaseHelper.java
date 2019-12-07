package com.example.danie.coursework1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper
{

    public static String DATABASE_NAME = "expenses_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_EXPENSES = "expenses";
    private static final String KEY_ID = "_id";
    private static final String KEY_DESCRIPTION_COLUMN = "DESCRIPTION_COLUMN";
    private static final String KEY_COST_COLUMN = "COST_COLUMN";
    private static final String KEY_RECEIPT_DATE_COLUMN = "RECEIPT_DATE_COLUMN";
    private static final String KEY_DATE_ADDED_TO_APP_COLUMN = "DATE_ADDED_TO_APP_COLUMN";
    private static final String KEY_VAT_COLUMN = "VAT_COLUMN";
    private static final String KEY_TOTAL_COST_COLUMN = "TOTAL_COST_COLUMN";
    private static final String KEY_EXPENSE_PAID_COLUMN = "EXPENSE_PAID_COLUMN";
    private static final String KEY_DATE_PAID_COLUMN = "DATE_PAID_COLUMN";
    private static final String KEY_IMAGE_COLUMN = "IMAGE_COLUMN";

    private static final String CREATE_DATABASE_TABLE_EXPENSES = "CREATE TABLE "
            + DATABASE_TABLE_EXPENSES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DESCRIPTION_COLUMN + " TEXT," + KEY_COST_COLUMN + " TEXT,"
            + KEY_RECEIPT_DATE_COLUMN + " TEXT," + KEY_DATE_ADDED_TO_APP_COLUMN + " TEXT,"
            + KEY_VAT_COLUMN + " INTEGER," + KEY_TOTAL_COST_COLUMN + " TEXT,"
            + KEY_EXPENSE_PAID_COLUMN + " INTEGER," + KEY_DATE_PAID_COLUMN + " TEXT," + KEY_IMAGE_COLUMN + " BLOB);";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS '" + DATABASE_TABLE_EXPENSES +"'");
        onCreate(db);
    }

    public void addExpense(String description, String cost,
                           String receiptDate, String dateAddedtoApp,
                           int vat, int expensepaid, String totalcost, String datePaid,
                           byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newVals = new ContentValues();
        newVals.put(KEY_DESCRIPTION_COLUMN, description);
        newVals.put(KEY_COST_COLUMN, cost);
        newVals.put(KEY_RECEIPT_DATE_COLUMN, receiptDate);
        newVals.put(KEY_DATE_ADDED_TO_APP_COLUMN, dateAddedtoApp);
        newVals.put(KEY_VAT_COLUMN, vat);
        newVals.put(KEY_TOTAL_COST_COLUMN, totalcost);
        newVals.put(KEY_EXPENSE_PAID_COLUMN, expensepaid);
        newVals.put(KEY_DATE_PAID_COLUMN, datePaid);
        newVals.put(KEY_IMAGE_COLUMN, image);
        db.insert(DATABASE_TABLE_EXPENSES, null, newVals);
    }

    public ArrayList<ExpenseTemplate> getExpenses(boolean includePaid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseTemplate> expenseTemplateArrayList = new ArrayList<ExpenseTemplate>();

        String selectQuery = "";
        if(includePaid) { selectQuery = setQuery(true); }
        else if(!includePaid) { selectQuery = setQuery(false); }

        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext())
        {
            ExpenseTemplate expensetemplate = new ExpenseTemplate();
            expensetemplate.setID
                    (cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            expensetemplate.setDesc
                    (cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION_COLUMN)));
            expensetemplate.setCost
                    (cursor.getString(cursor.getColumnIndex(KEY_COST_COLUMN)));
            expensetemplate.setReceiptDate
                    (cursor.getString(cursor.getColumnIndex(KEY_RECEIPT_DATE_COLUMN)));
            expensetemplate.setDateAddedtoApp
                    (cursor.getString(cursor.getColumnIndex(KEY_DATE_ADDED_TO_APP_COLUMN)));
            expensetemplate.setVAT
                    (cursor.getInt(cursor.getColumnIndex(KEY_VAT_COLUMN)));
            expensetemplate.setTotalCost
                    (cursor.getString(cursor.getColumnIndex(KEY_TOTAL_COST_COLUMN)));
            expensetemplate.setExpensepaid
                    (cursor.getInt(cursor.getColumnIndex(KEY_EXPENSE_PAID_COLUMN)));
            expensetemplate.setDatePaid
                    (cursor.getString(cursor.getColumnIndex(KEY_DATE_PAID_COLUMN)));
            expensetemplate.setImage
                    (cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE_COLUMN)));
            expenseTemplateArrayList.add(expensetemplate);
        }
        return expenseTemplateArrayList;
    }


    public String setQuery(Boolean includePaid)
    {
        String query = "";
        if(includePaid == true) { query = "SELECT * FROM " + DATABASE_TABLE_EXPENSES; }
        else if(includePaid == false)
        {
            query = "SELECT * FROM " +
                DATABASE_TABLE_EXPENSES + " WHERE " + KEY_EXPENSE_PAID_COLUMN + " = 0";
        }
        return query;
    }

    public void updateExpense(int id, String description, String cost,
                              String receiptDate, String dateAddedtoApp,
                              int vat, int expensepaid, String totalcost,
                              String datePaid, byte[] image)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues updateVals = new ContentValues();
        updateVals.put(KEY_DESCRIPTION_COLUMN, description);
        updateVals.put(KEY_COST_COLUMN, cost);
        updateVals.put(KEY_RECEIPT_DATE_COLUMN, receiptDate);
        updateVals.put(KEY_DATE_ADDED_TO_APP_COLUMN, dateAddedtoApp);
        updateVals.put(KEY_VAT_COLUMN, vat);
        updateVals.put(KEY_EXPENSE_PAID_COLUMN, expensepaid);
        updateVals.put(KEY_TOTAL_COST_COLUMN, totalcost);
        updateVals.put(KEY_DATE_PAID_COLUMN, datePaid);
        updateVals.put(KEY_EXPENSE_PAID_COLUMN, expensepaid);
        updateVals.put(KEY_IMAGE_COLUMN, image);

        String where = KEY_ID + " = ?";
        String whereArgs[] = new String[] { String.valueOf(id)};
        database.update(DATABASE_TABLE_EXPENSES, updateVals, where, whereArgs);
    }

    public void deleteExpense(int id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DATABASE_TABLE_EXPENSES, KEY_ID + " =?", new String[] {String.valueOf(id)});
    }
}
