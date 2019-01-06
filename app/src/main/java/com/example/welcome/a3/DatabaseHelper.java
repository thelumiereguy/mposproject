package com.example.welcome.a3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static String DB_PATH = "/data/data/com.example.welcome.a3/databases/";
    // Data Base Name.
    private static final String DATABASE_NAME = "sqlite.db";
    // Data Base Version.
    private static final int DATABASE_VERSION = 1;
    // Table Names of Data Base.


    public Context context;
    static SQLiteDatabase sqliteDataBase;


    public DatabaseHelper(Context context) throws IOException {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
        this.context = context;
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createDataBase() throws IOException{
        //check if the database exists
        boolean databaseExist = checkDataBase();

        if(databaseExist){
            // Do Nothing.
        }else{
            this.getWritableDatabase();
            copyDataBase();
        }// end if else dbExist
    } // end createDataBase().

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring byte stream.
     * */
    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * This method opens the data base connection.
     * First it create the path up till data base of the device.
     * Then create connection with data base.
     */
    public void openDataBase() throws SQLException{
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

//    public static final String Dbname="sqlite.db";
//    public static final String table1name="Item_table";
//    public static final String ic1="SKU";
//    public static final String ic2="Item_desc";
//    public static final String ic3="MRP";
//    public static final String ic4="Units";
//
//
//    public static final String table2name="Bill_table";
//    public static final String bc1="Bill_no";
//    public static final String bc2="Timestamp";
//    public static final String bc3="Amt";
//    public static final String bc4="T_Mode";
//    public static final String bc5="Detail";
//
//    public static final String table3name="Transaction_table";
//    public static final String tc1="Bill_no";
//    public static final String tc2="Sku_no";
//
//
//    public DatabaseHelper(Context context) {
//        super(context, Dbname, null , 1);
//        SQLiteDatabase db=this.getWritableDatabase();
//
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("Create table " + table1name + "(SKU INTEGER PRIMARY KEY, Item_desc TEXT, MRP REAL, Units INTEGER)");
//        db.execSQL("insert into " + table1name + " values (2345674332,'LEXI 5',10,190)");
//        db.execSQL("insert into " + table1name + " values (4567893412,'NAVNEET L',20,80)");
//        db.execSQL("insert into " + table1name + " values (6723569067,'CAMLIN L',25,90)");
//        db.execSQL("insert into " + table1name + " values (38261949204,'PARKER BAL',130,20)");
//        db.execSQL("insert into " + table1name + " values (112233445566,'Classmate XL',30,100)");
//        db.execSQL("insert into " + table1name + " values (545472729648,'REYNOLDS R',10,110)");
//        db.execSQL("insert into " + table1name + " values (820374521775,'CELLO GR',11,150)");
//        db.execSQL("insert into " + table1name + " values (839362740235,'UNIBALL M',35,80)");
//        db.execSQL("insert into " + table1name + " values (883326394012,'CAM ERASER',5,200)");
//        db.execSQL("insert into " + table1name + " values (978674930248,'ANDROID FOR D',1100,15)");
        //db.execSQL("Create table " + table2name + "(Bill_no INTEGER PRIMARY KEY, Timestamp TEXT, Amt REAL, T_Mode INTEGER, Detail TEXT)");
        //db.execSQL("Create table " + table3name + "(Bill_no INTEGER , Sku_no INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + table1name);
//        db.execSQL("DROP TABLE IF EXISTS " + table2name);
//        db.execSQL("DROP TABLE IF EXISTS " + table3name);
//        onCreate(db);
    }

    public Cursor getProdData(String SKU) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT item_desc,mrp FROM 'item_table' where sku="+SKU,null);
        return res;
    }

    public Cursor billdata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res2 = db.rawQuery("SELECT max(bill_no) FROM 'bill_table'",null);
        return res2;
    }

    public boolean insertbill(int transno,String currentDate,double sum,int tmode,String detail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bill_no", transno);
        values.put("timestamp", currentDate);
        values.put("Amt", sum);
        values.put("T_mode", tmode);
        values.put("detail",detail);
        boolean success = db.insert("bill_table", null, values) > 0;
        db.close();
        return success;
    }

}
