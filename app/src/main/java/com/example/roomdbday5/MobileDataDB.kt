package com.example.roomdbday5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities =[ MobileEntity::class], version = 1)
abstract class MobileDataDB : RoomDatabase() {
    abstract fun mobileDao(): MobilePwdDAO

    companion object{
        private var INSTANCE:MobileDataDB ?= null

        fun getInstance(context:Context): MobileDataDB{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MobileDataDB::class.java,
                        "customerdb.db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}