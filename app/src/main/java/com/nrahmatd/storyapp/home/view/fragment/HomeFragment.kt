package com.nrahmatd.storyapp.home.view.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nrahmatd.storyapp.databinding.FragmentHomeBinding
import com.nrahmatd.storyapp.home.adapter.HomeAdapter
import com.nrahmatd.storyapp.home.viewmodel.AllStoriesViewModel
import com.nrahmatd.storyapp.home.viewmodel.AllStoriesViewModelFactory
import com.nrahmatd.storyapp.location.view.LocationActivity
import com.nrahmatd.storyapp.utils.GlobalVariable
import com.nrahmatd.storyapp.utils.getNotify
import com.sagara.klipz.baseview.BaseFragment
import io.reactivex.disposables.CompositeDisposable

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val allStoriesViewModel: AllStoriesViewModel by viewModels {
        AllStoriesViewModelFactory(requireActivity())
    }
    private lateinit var homeAdapter: HomeAdapter

    private val compositeDisposable = CompositeDisposable()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setup() {
        setupSwipeRefresh()
        setupRecyclerView()
        getAllStories()
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

    private fun getAllStories() {
        allStoriesViewModel.responsePaging.observe(this) {
            homeAdapter.submitData(lifecycle, it)
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
