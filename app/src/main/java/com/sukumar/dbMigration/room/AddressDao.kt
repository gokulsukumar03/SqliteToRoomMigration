package com.sukumar.dbMigration.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * @Author sukumar
 * @Date 07/08/21 1:56 PM
 */

@Dao
interface AddressDao {

    @Query("SELECT * FROM user_address ")
    fun getAll(): List<AddressEntity?>?

    @Query("SELECT pin_code FROM user_address")
    fun getAllPinCode(): List<String?>?


    /*
  * Insert the object in database
  * @param note, object to be inserted
  */
    @Insert
    fun insert(addressEntity: AddressEntity?)
}