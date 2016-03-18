package healthsignz.com.todalist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devuser on 18-03-2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "superlistdb4";
    private static final String TABLE_NAME = "list";

    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String query ;
        query = "CREATE TABLE list(position INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        db.execSQL(query);
        Log.i("DATABASE ", "CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertRecord(String item){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",item);
        Log.d("query", "INSERETED");
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void insertAllRecord(String items[]){

        SQLiteDatabase database = this.getReadableDatabase();
        database.execSQL("delete from "+ TABLE_NAME);
        ContentValues contentValues = new ContentValues();
        for(int i=0 ; i < items.length ; i++){
            contentValues.put("name",items[i]);
            database.insert(TABLE_NAME, null, contentValues);
        }

        database.close();
    }
   /*public int updateStudent(List<String> items,int position){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",position);
        contentValues.put("name",position);
        return database.update("list",contentValues,"name "+"= ?",)
    }*/

    public void deleteRecord(int position){
        SQLiteDatabase database = this.getWritableDatabase();
        String whereClause = "position" + "=?";
        String[] whereArgs = new String[] { String.valueOf(position) };
        database.delete(TABLE_NAME, whereClause, whereArgs);
        database.close();
    }

    public ArrayList<String> getList(){
        ArrayList<String> items = new ArrayList<String>();
        String selectQuery = "SELECT name FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                items.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return items;
    }
}
