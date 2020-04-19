package com.example.instagram.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.instagram.data.local.db.dao.AddressDao
import com.example.instagram.data.local.db.dao.UserDao
import com.example.instagram.data.local.db.dao.entity.Address
import com.example.instagram.data.local.db.dao.entity.User
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        User::class,
        Address::class
    ],
    exportSchema = false,
    version = 2
)
@TypeConverters(Converter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun addressDao(): AddressDao

    abstract fun userDao(): UserDao
}