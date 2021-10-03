package com.example.thecatapitask

import com.example.thecatapitask.data.Cat
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatService {
    @Headers("x-api-key: $API_KEY")
    @GET("/v1/images/search")
    suspend fun getListOfCats(
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @Query("page") page: Int = 0,
        @Query("mime_types") format: String = "jpg",
        @Query("order") order: String = "ACS"
    ): List<Cat>

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val API_KEY = "9d0ecdda-4340-4dae-a1cf-dd8200c031df"

    }
}


