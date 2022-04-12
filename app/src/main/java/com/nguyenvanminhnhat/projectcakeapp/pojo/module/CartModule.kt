package com.nguyenvanminhnhat.projectcakeapp.pojo.module

import android.content.Context
import androidx.room.Room
import com.nguyenvanminhnhat.projectcakeapp.pojo.db.CartDB
import com.nguyenvanminhnhat.projectcakeapp.pojo.db.CartDao
import com.nguyenvanminhnhat.projectcakeapp.pojo.value.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CartModule {

    @Provides
    @Singleton
    fun provideDetailDao(detailDatabase: CartDB) : CartDao {
        return detailDatabase.cartDao()
    }

    @Provides
    fun getDetailDB(@ApplicationContext context : Context) : CartDB {
        return Room.databaseBuilder(
            context,
            CartDB::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries()
            .build()
    }

}