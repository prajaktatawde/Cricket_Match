package com.example.sports_demo.network

import com.example.demo_sports.NZIN
import com.example.demo_sports.SAPK
import com.example.demo_sports.model.ResponseFromApi
import retrofit2.Call
import retrofit2.http.GET

interface RetroService {

    @GET(NZIN)
    suspend fun getDataForNZIN(): ResponseFromApi

    @GET(SAPK)
    suspend fun getDataForSAPK(): ResponseFromApi
}