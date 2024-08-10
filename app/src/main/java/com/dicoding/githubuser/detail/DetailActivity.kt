package com.dicoding.githubuser.detail

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.detail.follow.FollowFragment
import com.dicoding.githubuser.data.local.DbModule
import com.dicoding.githubuser.data.modul.DetailResponse
import com.dicoding.githubuser.data.modul.ItemsItem
import com.dicoding.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(DbModule(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<ItemsItem>(Extra_user)


        if(item != null){
            viewModel.findDetailUser(item.login)
            viewModel.getFollowers(item.login)
            viewModel.getFollowing(item.login)
        }

        viewModel.userDetail.observe(this){
            setUser(it)
        }
        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )
        val titleFragment = mutableListOf<String>(
            getString(R.string.follower),
            getString(R.string.following)
        )
        val adapter = DetailAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tableLayout, binding.viewPager){ tab, posisi ->
            tab.text = titleFragment[posisi]
        }.attach()

        binding.favorite.setOnClickListener{
            viewModel.setFavorite(item)
        }

        viewModel.findFavorite(item?.id ?: 0) {
            binding.favorite.changeIconColor(R.color.red)
        }

        viewModel.resultSuksesFavorite.observe(this) {
            binding.favorite.changeIconColor(R.color.red)
        }

        viewModel.resultDeleteFavorite.observe(this) {
            binding.favorite.changeIconColor(R.color.white)
        }


    }

    private  fun setUser(user:DetailResponse){
        binding.apply {
            name.text = user.name
            username.text = user.login
            followingSum.text = user.following.toString()
            followerSum.text = user.followers.toString()
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .into(imageAvatar)
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object{
        const val Extra_user = "Extra_user"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}