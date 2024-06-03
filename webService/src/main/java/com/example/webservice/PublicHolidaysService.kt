package com.example.webservice

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PublicHolidaysService {
    @GET("{year}.json")
    suspend fun getPublicHolidaysForAZoneForAYear(@Path("year") year: Int): Response<Map<String, String>>

}