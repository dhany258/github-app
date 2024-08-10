package com.dicoding.githubuser.detail.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.detail.DetailViewModel
import com.dicoding.githubuser.databinding.FragmentFollowBinding
import com.dicoding.githubuser.utilitis.UserAdapter

class FollowFragment : Fragment() {

    private var binding: FragmentFollowBinding? = null
    private val adapter by lazy {
        UserAdapter{

        }
    }
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvFollow?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowFragment.adapter
        }
        val type = arguments?.getInt(TYPE) ?: FOLLOWERS
        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        observeViewModel(type)

    }

    private fun observeViewModel(type: Int) {
        when (type) {
            FOLLOWERS -> {
                viewModel.listFollowers.observe(viewLifecycleOwner) { followers ->
                    adapter.submitList(followers)
                }
            }
            FOLLOWING -> {
                viewModel.listFollowing.observe(viewLifecycleOwner) { following ->
                    adapter.submitList(following)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }




    companion object {
        private const val TYPE = "TYPE"
        const val FOLLOWERS = 1
        const val FOLLOWING = 2

        fun newInstance(type: Int) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(TYPE, type)
                }
            }
    }
}