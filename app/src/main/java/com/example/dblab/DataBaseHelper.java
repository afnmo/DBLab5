package com.example.dblab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

//    called the first time the DB is accessed. here the code to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT)";
        db.execSQL(createTableStatement);
    }

//    called if the database version number changes. prevents prev users apps from breaking when you change the DB design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(customerModel cModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        id is an auto increment in the database, no need to put it!
        contentValues.put(COLUMN_CUSTOMER_NAME, cModel.getName());
        contentValues.put(COLUMN_CUSTOMER_AGE, cModel.getAge());

        long insert = db.insert(CUSTOMER_TABLE, null, contentValues);
//        System.out.println(insert);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }





    public boolean DeleteOne(customerModel cModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString= "Delete From " + CUSTOMER_TABLE + " WHERE " + COLUMN_ID + " = " + cModel.getID() ;
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        } else{
            // nothing happens. no one is added.
            return false;
        }
        //close
    }


    public List<customerModel> getEveryone(){
    List<customerModel> returnList = new ArrayList<>();
    // get data from database
    String queryString = "Select * from "+ CUSTOMER_TABLE;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(queryString, null);
       if(cursor.moveToFirst()){
        // loop through cursor results
        do{
            int SID = cursor.getInt(0); // customer ID
            String SName = cursor.getString(1);
            int SAge = cursor.getInt(2);

            customerModel newCustomer = new customerModel(SID, SName, SAge);
            returnList.add(newCustomer);
        }while (cursor.moveToNext());
    } else{
        // nothing happens. no one is added.
    }
       cursor.close();
       db.close();
       return returnList;
    }

}
