package com.sukumar.dbMigration

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sukumar.dbMigration.room.AddressEntity
import com.sukumar.dbMigration.room.RoomDbHelper
import com.sukumar.dbMigration.room.UserEntity
import com.sukumar.dbMigration.sqilite.AddressModel
import com.sukumar.dbMigration.sqilite.SqliteDbHelper
import com.sukumar.dbMigration.sqilite.UserModel
import java.util.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    val TAG = "Logger"
    lateinit var sqliteData: TextView
    lateinit var roomData: TextView
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        sqliteData = findViewById(R.id.sqliteData)
        sqliteData.setOnClickListener {
            getSqliteData()
        }

        roomData = findViewById(R.id.roomData)
        roomData.setOnClickListener {
            getRoomData()
        }

    }

    private fun getSqliteData() {
        if (isDbMigrated()) {
            Toast.makeText(
                this,
                "Database Migrated to Room.. clear data and try again",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        setSqliteExecuted()
        val databaseHandler = SqliteDbHelper(this)
        val autoId = Random().nextInt(100)
        val userName = "name_$autoId"
        val userEmail = "email_$autoId"
        val user = UserModel(autoId, userName, userEmail)
        databaseHandler.addUser(user)

        val userAddress = "address_random_$autoId"
        val pinCode = "pincode-$autoId-$autoId"
        val address = AddressModel(userAddress, pinCode)
        databaseHandler.addAddress(address)


        val userList = databaseHandler.getUserName()
        userList.let {
            Log.d(TAG, userList.toString())
            Log.d(TAG, "Total user count :::    ${userList.size}")
        }

        val addressList = databaseHandler.getUserPinCode()
        userList.let {
            Log.d(TAG, addressList.toString())
            Log.d(TAG, "Total address count :::    ${addressList.size}")
        }
    }


    private fun getRoomData() {
        if (isDataNotExist()) {
            Toast.makeText(this, "Execute Sqlite first...", Toast.LENGTH_SHORT).show()
            return
        }
        Executors.newSingleThreadExecutor().execute(Runnable {
            setDbMigrated()
            val database = RoomDbHelper.getInstance(this)
            val autoId = Random().nextInt(100)
            val userName = "name_$autoId"
            val userEmail = "email_$autoId"
            val user = UserEntity()
            user.userId = autoId
            user.userName = userName
            user.userEmail = userEmail
            database?.getUserDao()?.insert(user)

            val address = AddressEntity()
            address.address = "address_random_$autoId"
            address.pinCode = "pincode-$autoId-$autoId"
            database?.getAddressDa0()?.insert(address)

            val userList = database?.getUserDao()?.getAllUserName()
            userList?.let {
                Log.d(TAG, userList.toString())
                Log.d(TAG, "Total user count :::    ${userList.size}")
            }


            val addressList = database?.getAddressDa0()?.getAllPinCode()
            addressList?.let {
                Log.d(TAG, addressList.toString())
                Log.d(TAG, "Total address count :::    ${addressList.size}")
            }

        })
    }


    private fun isDbMigrated() = sharedPref.getBoolean("room", false)

    private fun setDbMigrated() {
        sharedPref.edit().putBoolean("room", true).apply()
    }

    private fun isDataNotExist() = sharedPref.getBoolean("sqlite", true)

    private fun setSqliteExecuted() {
        sharedPref.edit().putBoolean("sqlite", false).apply()
    }

}