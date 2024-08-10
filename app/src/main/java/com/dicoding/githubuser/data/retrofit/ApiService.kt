package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.modul.DetailResponse
import com.dicoding.githubuser.data.modul.GithubResponse
import com.dicoding.githubuser.data.modul.ItemsItem
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

        @GET("search/users")
        fun getUserSearch(
            @Query("q") q: String
        ): Call<GithubResponse>

        @GET("users/{username}")
        fun getUserDetail(
            @Path("username") username: String
        ): Call<DetailResponse>

        @GET("users/{username}/followers")
        fun getUserFollowers(
            @Path("username") username: String
        ): Call<List<ItemsItem>>

        @GET("users/{username}/following")
        fun getUserFollowing(
            @Path("username") username: String
        ): Call<List<ItemsItem>>
    }

