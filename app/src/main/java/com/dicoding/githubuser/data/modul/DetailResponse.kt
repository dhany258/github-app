package com.dicoding.githubuser.data.modul

import com.google.gson.annotations.SerializedName

 class DetailResponse(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("name")
	val name: String? = null,
)
