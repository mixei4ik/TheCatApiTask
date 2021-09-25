package com.example.thecatapitask

import com.example.thecatapitask.data.Cat
import com.example.thecatapitask.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface TheCatApi {
    @GET("/v1/images/search?limit=10&order=ACSapi_key=9d0ecdda-4340-4dae-a1cf-dd8200c031df")
    suspend fun getListOfCats(): List<Result>
}

object TheCatApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.thecatapi.com")
        .build()

    private val theCatService = retrofit.create(TheCatApi::class.java)

    suspend fun getListOfCats(): List<Cat> {
        return withContext(Dispatchers.IO) {
            theCatService.getListOfCats()
                .map { result ->
                    Cat(
                        result.id,
                        result.imageUrl
                    )
                }
        }
    }
}