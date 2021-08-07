package com.sukumar.dbMigration.sqilite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * @Author sukumar
 * @Date 05/08/21 8:12 PM
 */
class SqliteDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "userDb"
        val table_user = "user"
        val key_id = "id"
        val key_name = "name"
        val key_email = "email"

        val table_address = "user_address"
        val id = "id"
        val address = "address"
        val pincode = "pin_code"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createUserTable = "CREATE TABLE $table_user ($key_id INTEGER, $key_name TEXT, $key_email TEXT)"
        db?.execSQL(createUserTable)

        val createAddressTable = "CREATE TABLE $table_address ($id INTEGER PRIMARY KEY AUTOINCREMENT, $address TEXT, $pincode TEXT)"
        db?.execSQL(createAddressTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    fun addUser(user: UserModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(key_id, user.userId)
        contentValues.put(key_name, user.userName)
        contentValues.put(key_email, user.userEmail)
        val success = db.insert(table_user, null, contentValues)
        db.close()
        return success
    }


    fun getUserName(): List<String> {
        val userList: ArrayList<String> = ArrayList()
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $table_user";
        val cursor = db.rawQuery(selectQuery, null);
        var userName: String
        if (cursor.moveToFirst()) {
            do {
                userName = cursor.getString(cursor.getColumnIndexOrThrow(key_name))
                userList.add(userName)
            } while (cursor.moveToNext())
        }
        return userList
    }


    fun addAddress(addressModel: AddressModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(address, addressModel.keyAddress)
        contentValues.put(pincode, addressModel.pinCode)
        val success = db.insert(table_address, null, contentValues)
        db.close()
        return success
    }


    fun getUserPinCode(): List<String> {
        val pinCodeList: ArrayList<String> = ArrayList()
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $table_address";
        val cursor = db.rawQuery(selectQuery, null);
        var pinCode: String
        if (cursor.moveToFirst()) {
            do {
                pinCode = cursor.getString(cursor.getColumnIndexOrThrow(pincode))
                pinCodeList.add(pinCode)
            } while (cursor.moveToNext())
        }
        return pinCodeList
    }

}