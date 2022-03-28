package Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import Model.Item;
import Utils.Utils;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(  Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }//end Constructor DatabaseHandler()

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PEOPLE_TABLE ="CREATE TABLE " + Utils.TABLE_NAME + " (" +
              Utils.KEY_ID + " INTEGER PRIMARY KEY,"
            + Utils.KEY_ITEM_NAME + " TEXT,"
            + Utils.KEY_ITEM_DESCRIPTION + " TEXT,"
            + Utils.KEY_ITEM_QUANTITY + " TEXT," +
              Utils.KEY_ITEM_SELLING_PRICE + " TEXT)";
        db.execSQL(CREATE_PEOPLE_TABLE);
    }//end of onCreate() SQLiteDatabase

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME);
        onCreate(db);
    }//end of onUpgrade() SQLiteDatabase

    //Use this function when you need to add Item to table
    public void addItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_ITEM_NAME , item.getItem_name());
        contentValues.put(Utils.KEY_ITEM_DESCRIPTION , item.getItem_description());
        contentValues.put(Utils.KEY_ITEM_QUANTITY , String.valueOf(item.getItemQuantity()) );
        contentValues.put(Utils.KEY_ITEM_SELLING_PRICE , item.getItem_selling_price());
        database.insert(Utils.TABLE_NAME , null,contentValues);
        database.close();
    }//end of addItem() function

    //Use this function when you need to display single record in table
    public  Item getItem(int id){
        SQLiteDatabase database = this.getReadableDatabase() ;
        Cursor cursor = database.query(Utils.TABLE_NAME ,
            new String[]{Utils.KEY_ID,
                         Utils.KEY_ITEM_NAME,
                         Utils.KEY_ITEM_DESCRIPTION,
                         Utils.KEY_ITEM_QUANTITY,
                         Utils.KEY_ITEM_SELLING_PRICE }
                ,Utils.KEY_ID + "=?"
                , new String[]{String.valueOf(id)}
                ,null
                ,null
                ,null
                ,null);
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            Item item = new Item(Integer.parseInt(cursor.getString(0))
                    , cursor.getString(1)
                    , cursor.getString(2)
                    , Integer.parseInt(cursor.getString(3))
                    , Integer.parseInt(cursor.getString(4)));
            cursor.close();
            return item;

        }else
            return null;
    }//end of getItem() function

//Use this function if you need to display all the records in table
    public List<Item> getItems(){
        SQLiteDatabase database = this.getReadableDatabase() ;
        List<Item> itemsList = new ArrayList<>();
        String getAll = "SELECT * FROM "+ Utils.TABLE_NAME;
        Cursor cursor = database.rawQuery(getAll ,null );
        if (cursor.moveToFirst())
            do{
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setItem_name(cursor.getString(1));
                item.setItem_description(cursor.getString(2));
                item.setItem_selling_price(Integer.parseInt(cursor.getString(3)));
                item.setItemQuantity(Integer.parseInt(cursor.getString(4)));
                itemsList.add(item);
            }while (cursor.moveToNext());
            cursor.close();
        return  itemsList;
    }//end of getItems() function

//Use this function if you need to update single record in table
    public  void updateItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase() ;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_ITEM_NAME , item.getItem_name());
        contentValues.put(Utils.KEY_ITEM_DESCRIPTION , item.getItem_description());
        contentValues.put(Utils.KEY_ITEM_QUANTITY , String.valueOf(item.getItemQuantity()) );
        contentValues.put(Utils.KEY_ITEM_SELLING_PRICE , String.valueOf(item.getItem_selling_price()));

        database.update(Utils.TABLE_NAME
                , contentValues,Utils.KEY_ID + "=?"
                , new String[]{ String.valueOf(item.getId())});
        database.close();
    }//end of updateItem() function

//Use this function if you need to delete single record in table
    public  void deleteItem(Item item){
        SQLiteDatabase database = this.getWritableDatabase() ;
        database.delete(Utils.TABLE_NAME
                , Utils.KEY_ID + "=?"
                ,  new String[]{ String.valueOf(item.getId())});
        database.close();
    }//end of deleteItem() function

//Use this function if you need to count the number of records in table
    public  int getNumItem(){
        String getAll = "SELECT * FROM "+ Utils.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase() ;
        Cursor cursor = database.rawQuery(getAll ,null );
        int result = cursor.getCount();
         cursor.close();
        return result;
    }//end of getNumItem() function
}//end class DatabaseHandler