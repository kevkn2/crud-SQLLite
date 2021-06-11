package com.example.crudwithsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.widget.TextView


class DB_Controller(context: Context?,
                    name: String?,
                    factory: CursorFactory?,
                    version: Int) :
        SQLiteOpenHelper(context, "TEST.DB", factory, version) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE STUDENTS(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT UNIQUE, LASTNAME TEXT);")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS STUDENTS;")
        onCreate(sqLiteDatabase)
    }

    fun insert_student(firstname: String?, lastname: String?) {
        val contentValues = ContentValues()
        contentValues.put("FIRSTNAME", firstname)
        contentValues.put("LASTNAME", lastname)
        this.writableDatabase.insertOrThrow("STUDENTS", "", contentValues)
    }

    fun delete_student(firstname: String) {
        this.writableDatabase.delete("STUDENTS", "FIRSTNAME='$firstname'", null)
    }

    fun update_student(old_firstname: String, new_firstname: String) {
        this.writableDatabase.execSQL("UPDATE STUDENTS SET FIRSTNAME='$new_firstname' WHERE FIRSTNAME='$old_firstname'")
    }

    fun list_all_students(textView: TextView) {
        val cursor: Cursor = this.readableDatabase.rawQuery("SELECT * FROM STUDENTS", null)
        textView.text = ""
        while (cursor.moveToNext()) {
            textView.append(cursor.getString(1).toString() + " " + cursor.getString(2) + "\n")
        }
    }
}