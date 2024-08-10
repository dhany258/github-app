package com.dicoding.githubuser.data.modul

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

@Parcelize
@Entity(tableName = "user")
data class ItemsItem(

	@ColumnInfo(name = "login")
	@field:SerializedName("login")
	val login: String,

	@ColumnInfo(name = "avatar_url")
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@ColumnInfo(name = "html_url")
	@field:SerializedName("html_url")
	val htmlUrl: String,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int
): Parcelable
