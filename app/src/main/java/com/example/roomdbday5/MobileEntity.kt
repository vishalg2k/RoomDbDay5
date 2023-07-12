package com.example.roomdbday5

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mobile_pwd_data")
data class MobileEntity(
    @PrimaryKey(autoGenerate = true)
    var mobId: Int=0,

    @ColumnInfo("mobile_num")
    var mobNum:String,

    @ColumnInfo("Passwd")
    var pswd:String,

)
