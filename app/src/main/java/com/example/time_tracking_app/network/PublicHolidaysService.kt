package com.example.time_tracking_app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PublicHolidaysService {
    @GET("{year}.json")
    suspend fun getPublicHolidaysForAZoneForAYear(@Path("year") year: Int): Response<Map<String, String>>

}