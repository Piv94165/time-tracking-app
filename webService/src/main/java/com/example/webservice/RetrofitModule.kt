package com.example.webservice

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    val BASE_URL =
        "https://calendrier.api.gouv.fr/jours-feries"

    @Singleton
    @Provides
    fun providesRetrofit(moshiConverterFactory: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create()) // convertir un json en String
            .addConverterFactory(
                MoshiConverterFactory.create(
                    moshiConverterFactory
                )
            )
            .baseUrl("$BASE_URL/metropole/")
            .build()
    }

    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun providesPublicHolidayService(retrofit: Retrofit) = retrofit.create(PublicHolidaysService::class.java)



}