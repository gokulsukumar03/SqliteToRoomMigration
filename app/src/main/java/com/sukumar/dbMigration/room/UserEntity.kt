package com.sukumar.dbMigration.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author sukumar
 * @Date 06/08/21 11:49 AM
 */


@Entity(tableName = "user", primaryKeys = ["id", "name"])
class UserEntity {

    @ColumnInfo(name = "id")
    var userId: Int = 0

    @ColumnInfo(name = "name")
    var userName: String = ""

    @ColumnInfo(name = "email")
    var userEmail: String? = null
}