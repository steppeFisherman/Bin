package com.example.bin.ui.di

import android.content.Context
import com.example.bin.data.model.mappers.MapCacheToDomain
import com.example.bin.data.model.mappers.MapCloudToCache
import com.example.bin.data.net.CloudData
import com.example.bin.data.net.Service
import com.example.bin.data.repository.CacheSource
import com.example.bin.data.repository.CloudSource
import com.example.bin.data.repository.ExceptionHandle
import com.example.bin.data.repository.RepositoryImpl
import com.example.bin.data.room.AppRoomDao
import com.example.bin.data.room.AppRoomDatabase
import com.example.bin.domain.Repository
import com.example.bin.utils.FormatCountryName
import com.example.bin.utils.FormatPhoneNumber
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context): AppRoomDao =
        AppRoomDatabase.getInstance(context = context).getAppRoomDao()

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Service.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): Service = retrofit.create(Service::class.java)

    @Provides
    @Singleton
    fun provideCloud(service: Service): CloudData = CloudData.Base(service)

    @Provides
    @Singleton
    fun provideMapCacheToDomain(): MapCacheToDomain = MapCacheToDomain.Base()

    @Provides
    @Singleton
    fun provideMapCloudToCache(): MapCloudToCache =
        MapCloudToCache.Base(
            phoneFormat = FormatPhoneNumber.Base(),
            countryNameFormat = FormatCountryName.Base()
        )

    @Provides
    fun provideExceptionHandle(): ExceptionHandle = ExceptionHandle.Base()

    @Provides
    fun provideCloudSource(
        cloudData: CloudData,
        appDao: AppRoomDao,
        mapCloudToCache: MapCloudToCache,
        mapCacheToDomain: MapCacheToDomain,
    ): CloudSource = CloudSource.Base(
        cloudData = cloudData,
        appDao = appDao,
        mapperCloudToCache = mapCloudToCache,
        mapperCacheToDomain = mapCacheToDomain,
    )

    @Provides
    fun provideCacheSource(
        appDao: AppRoomDao,
        mapCacheToDomain: MapCacheToDomain,
        exceptionHandle: ExceptionHandle,
    ): CacheSource = CacheSource.Base(
        appDao = appDao,
        mapperCacheToDomain = mapCacheToDomain,
        exceptionHandle = exceptionHandle
    )

    @Provides
    @Singleton
    fun provideRepository(
        cloudSource: CloudSource,
        cacheSource: CacheSource,
        exceptionHandle: ExceptionHandle,
    ): Repository = RepositoryImpl(
        cloudSource = cloudSource,
        cacheSource = cacheSource,
        exceptionHandle = exceptionHandle,
    )
}