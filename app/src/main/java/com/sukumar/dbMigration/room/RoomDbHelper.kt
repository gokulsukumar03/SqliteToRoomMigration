package com.sukumar.dbMigration.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration


/**
 * @Author sukumar
 * @Date 06/08/21 11:48 AM
 */

@Database(entities = [UserEntity::class, AddressEntity::class], version = 2)
abstract class RoomDbHelper : RoomDatabase() {
    abstract fun getUserDao(): UserDao?

    abstract fun getAddressDa0(): AddressDao?

    fun cleanUp() {
        dbHelper = null
    }

    companion object {

        private var dbHelper: RoomDbHelper? = null
        fun getInstance(context: Context): RoomDbHelper? {
            if (null == dbHelper) {
                dbHelper = buildDatabaseInstance(context)
            }
            return dbHelper
        }

        private fun buildDatabaseInstance(context: Context): RoomDbHelper {
            return Room.databaseBuilder(
                context, RoomDbHelper::class.java, "userDb"
            ).addMigrations(MIGRATION_1_2).build()
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                /*
                * 1. Rename Table
                * 2. Create Table (with existing table name)
                * 3. Create Table
                * 4. Drop Renamed Table
                * */

                // 1. Rename Table
                val userOldTable = "ALTER TABLE user RENAME TO user_old"
                database.execSQL(userOldTable)

                // 2. Create Table
                val userTable =
                    "CREATE TABLE user (id INTEGER NOT NULL, name TEXT NOT NULL, email TEXT, PRIMARY KEY (id, name))"
                database.execSQL(userTable)

                // 3. Copy all data
                val getAllUserOldData = " SELECT * FROM user_old"
                val copyUserTable = "INSERT INTO user $getAllUserOldData"
                database.execSQL(copyUserTable)

                // Drop Renamed Table
                val dropDummyUserTable = "DROP TABLE user_old"
                database.execSQL(dropDummyUserTable)
            }
        }
    }

}