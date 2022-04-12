package com.nguyenvanminhnhat.projectcakeapp.pojo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.CartModel

@Database(entities = [CartModel::class], version = 1)
abstract class CartDB : RoomDatabase() {
    abstract fun cartDao(): CartDao
}