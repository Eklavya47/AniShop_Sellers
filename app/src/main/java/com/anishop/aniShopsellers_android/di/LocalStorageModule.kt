package com.anishop.aniShopsellers_android.di

import android.content.Context
import android.content.SharedPreferences
import com.anishop.aniShopsellers_android.data.dataSource.local.SharedPreferences.provideEncryptedSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {

    @Provides
    @Singleton
    fun provideSecureStorage(@ApplicationContext context: Context): SharedPreferences {
        return provideEncryptedSharedPreferences(context)
    }
}