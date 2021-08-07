package com.sukumar.dbMigration.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author sukumar
 * @Date 07/08/21 1:47 PM
 */

@Entity(tableName = "user_address")
class AddressEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var addressId: Int? = null

    @ColumnInfo(name = "address")
    var address: String? = null

    @ColumnInfo(name = "pin_code")
    var pinCode: String? = null
}