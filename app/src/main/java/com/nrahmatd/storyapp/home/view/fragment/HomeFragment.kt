package com.nrahmatd.storyapp.home.view.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nrahmatd.storyapp.databinding.FragmentHomeBinding
import com.nrahmatd.storyapp.home.adapter.HomeAdapter
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.home.viewmodel.AllStoriesViewModel
import com.nrahmatd.storyapp.home.viewmodel.AllStoriesViewModelFactory
import com.nrahmatd.storyapp.location.view.LocationActivity
import com.nrahmatd.storyapp.utils.GlobalVariable
import com.nrahmatd.storyapp.utils.ResponseHelper
import com.nrahmatd.storyapp.utils.getNotify
import com.nrahmatd.storyapp.utils.toast
import com.sagara.klipz.baseview.BaseFragment
import io.reactivex.disposables.CompositeDisposable

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var allStoriesViewModel: AllStoriesViewModel
    private lateinit var homeAdapter: HomeAdapter

    companion object {
        const val ALL_STORIES = 1
    }

    private val compositeDisposable = CompositeDisposable()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setup() {
        setupSwipeRefresh()
        setupRecyclerView()
        initViewModel()
        getAllStories()
        getResponse()
        initNotify()
        initOnClick()
    }

    private fun setupSwipeRefresh() {
        binding.swipeHome.setOnRefreshListener {
            getAllStories()
        }
    }

    private fun setupRecyclerView() {
        homeAdapter = HomeAdapter()
        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun initViewModel() {
        allStoriesViewModel =
            ViewModelProvider(this, AllStoriesViewModelFactory())[AllStoriesViewModel::class.java]
    }

    private fun getAllStories() = allStoriesViewModel.getAllStories(ALL_STORIES)

    private fun getResponse() {
        allStoriesViewModel.response.observe(this) {
            when (it.code) {
                ResponseHelper.ERROR -> toast(requireActivity(), it.response as String)
                ResponseHelper.LOADING -> binding.swipeHome.isRefreshing = it.response as Boolean
                ALL_STORIES -> {
                    val response = it.response as AllStoriesModel
                    homeAdapter.setDatas(response.listStory)
                }
            }
        }
    }

    private fun initNotify() {
        getNotify(compositeDisposable) {
            when (it.TAG) {
                GlobalVariable.NOTIFY_STORIES -> getAllStories()
            }
        }
    }

    private fun initOnClick() {
        binding.apply {
            fabLocation.setOnClickListener {
                startActivity(Intent(requireActivity(), LocationActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
