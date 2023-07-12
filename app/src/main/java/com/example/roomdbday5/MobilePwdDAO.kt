package com.example.roomdbday5

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface MobilePwdDAO {
    @Insert
    suspend fun dataInsert(item:MobileEntity)

    @Query("SELECT mobId FROM mobile_pwd_data WHERE mobile_num = :mobnum ")
    suspend fun searchId(mobnum:String): Int

    @Query("SELECT Passwd from mobile_pwd_data where mobile_num=:mobnum")
    suspend fun searchPswd(mobnum:String): String

    @Update()
    suspend fun dataUpdate(item: MobileEntity)

    @Query("DELETE FROM mobile_pwd_data WHERE mobId = :mid")
    suspend fun dataDeletebyId(mid: Int): Int

    @Delete()
    suspend fun deleteMobData(item: MobileEntity)

    @Query("Delete FROM mobile_pwd_data")
    suspend fun deleteAll()

}