package com.example.todolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper(context:Context) :
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
    companion object{


        private const val DATABASE_NAME ="todo.db"
        private const val DATABASE_VERSION=1
        private const val id="id"
        private const val task="task"
        private const val titem ="table_item"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createdb = ("CREATE TABLE "+ titem + "("
        + id + " INTEGER," + task + " TEXT" + ")")
        p0?.execSQL(createdb)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertItem(itm:ItemModel):Long{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(id, itm.id)
        contentValues.put(task, itm.Task)
        val success = db.insert(titem, null, contentValues)
        db.close()
        return success


    }

    fun deleteItem(iid:Int):Int{
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(id,iid)
        val success=db.delete(titem, "id="+iid,null)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllItems(): ArrayList<ItemModel> {
        val itemList: ArrayList<ItemModel> = ArrayList()
        val selectQuery = "SELECT * from $titem"
        var db = this.readableDatabase
        var cursor: Cursor?
        try {
            cursor=db.rawQuery(selectQuery,null)
        } catch (e: Exception) {
            e.printStackTrace()
            return ArrayList()
        }
        var id:Int
        var desc:String
        if(cursor.moveToFirst()){
            do{
                id=cursor.getInt(cursor.getColumnIndex("id"))
                desc=cursor.getString(cursor.getColumnIndex("task"))
                val itm=ItemModel(id=id, Task=desc)
                itemList.add(itm)
            }while(cursor.moveToNext())
        }
        return itemList
    }


}