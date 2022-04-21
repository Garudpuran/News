package com.garudpuran.kyachalrahahai.Interfaces

import retrofit2.http.GET
import retrofit2.http.Url
import com.garudpuran.kyachalrahahai.Models.NewsModel
import retrofit2.Call

interface RetrofitAPI {
    @GET
  fun getAllNews(@Url url: String) : Call<NewsModel>

    @GET
    fun getNewsByCategory(@Url url: String) : Call<NewsModel>
}