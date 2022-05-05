package com.example.Data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.Model.Grocery;
import com.example.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context ctx;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GROCERY_TABLE =
                "CREATE TABLE " + Constants.TABLE_NAME + "(" +
                        Constants.KEY_ID + " INTEGER PRIMARY KEY, " +
                        Constants.KEY_GROCERY_ITEM + " TEXT, " +
                        Constants.KEY_QTY_NUMBER + " TEXT, " +
                        Constants.KEY_DATE_NAME + " LONG);";
        sqLiteDatabase.execSQL(CREATE_GROCERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    /*
    CRUD OPERATIONS : CREATE READ UPDATE DELETE
     */

    //Add Grocery
    public void addGrocery(Grocery grocery){
        SQLiteDatabase db = this.getWritableDatabase();

        //contentValues is a hashmap object that store data in key-value format
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,values);
        Log.d("Saved!!","Saved to db");
        db.close();
    }

    //Get a Grocery
    public Grocery getGrocery(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        //cursor allows us array through or curse through our db easily
        Cursor cursor = db.query(Constants.TABLE_NAME,new String[] {Constants.KEY_ID,
                Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY_NUMBER,Constants.KEY_DATE_NAME},
                Constants.KEY_ID + "=?",
                new String[] {String.valueOf(id)},null,null,null,null);

        if(cursor!= null)
            cursor.moveToFirst();


            Grocery grocery = new Grocery();
            grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_ID))));
            grocery.setName(cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_GROCERY_ITEM)));
            grocery.setQuantity(cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_QTY_NUMBER)));

            //covert timeStamp to something readable
            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Constants.KEY_DATE_NAME)))
                    .getTime());
            grocery.setDateItemAdded(formattedDate);
            return grocery;


    }

    //Get all Groceries
    public List<Grocery> getAllGroceries(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Grocery> groceryList = new ArrayList<>();

//        Cursor cursor = db.query(Constants.TABLE_NAME,new String[] {Constants.KEY_ID,
//                Constants.KEY_GROCERY_ITEM,Constants.KEY_QTY_NUMBER,Constants.KEY_DATE_NAME},
//                null,null,null,null,
//                Constants.KEY_DATE_NAME + " DESC");
        String select = "SELECT * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do{
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndexOrThrow(Constants.KEY_QTY_NUMBER)));
//                grocery.setId(Integer.parseInt(cursor.getString(0)));
//                grocery.setName(cursor.getString(1));
//                grocery.setQuantity(cursor.getString(2));

                //covert timeStamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Constants.KEY_DATE_NAME)))
                        .getTime());
//                String formattedDate = dateFormat.format(new Date(cursor.getLong(3))
//                        .getTime());
                grocery.setDateItemAdded(formattedDate);
                //add to the GroceryList
                groceryList.add(grocery);

            }while (cursor.moveToNext());
        }

        return groceryList;
    }

    //Update Grocery
    public int updateGrocery(Grocery grocery){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_GROCERY_ITEM,grocery.getName());
        values.put(Constants.KEY_QTY_NUMBER, grocery.getQuantity());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis()); //get system time

        //update row
        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID + "=?", new String[] {String.valueOf(grocery.getId())});
    }

    //Delete Grocery
    public void deleteGrocery(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID + " =?", new String[] {String.valueOf(id)});

        db.close();
    }

    //Get Count
    public int getGroceriesCount(){

        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        return cursor.getCount();
    }
}
