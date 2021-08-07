package com.sukumar.dbMigration.room

import androidx.room.*


/**
 * @Author sukumar
 * @Date 06/08/21 11:59 AM
 */


@Dao
interface UserDao {

    @Query("SELECT * FROM user ")
    fun getAll(): List<UserEntity?>?

    @Query("SELECT name FROM user")
    fun getAllUserName(): List<String?>?


    /*
  * Insert the object in database
  * @param note, object to be inserted
  */
    @Insert
    fun insert(note: UserEntity?)

    /*
  * update the object in database
  * @param note, object to be updated
  */
    @Update
    fun update(repos: UserEntity?)

    /*
  * delete the object from database
  * @param note, object to be deleted
  */
    @Delete
    fun delete(note: UserEntity?)

    /*
  * delete list of objects from database
  * @param note, array of objects to be deleted
  */
    @Delete
    fun delete(vararg note: UserEntity?) // Note... is varargs, here note is an array


}