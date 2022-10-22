package com.nrahmatd.storyapp.detail.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nrahmatd.storyapp.baseview.BaseActivity
import com.nrahmatd.storyapp.databinding.ActivityDetailStoriesBinding
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.utils.parseDate

class DetailStoriesActivity : BaseActivity<ActivityDetailStoriesBinding>() {

    private lateinit var stories: AllStoriesModel.Story

    override val bindingInflater: (LayoutInflater) -> ActivityDetailStoriesBinding
        get() = ActivityDetailStoriesBinding::inflate

    override fun setup(savedInstanceState: Bundle?) {
        /** Wait until all resource is already loaded */
        supportPostponeEnterTransition()
        initExtras()
        initView()
    }

    override fun statusBarColor(): Int = 0

    private fun initExtras() {
        stories = intent.getSerializableExtra("stories") as AllStoriesModel.Story
    }

    private fun initView() {
        binding.apply {
            tvStoryName.text = stories.name
            tvStoryDescription.text = stories.description
            tvStoryDate.text = parseDate(stories.createdAt)
            /** Parse image to ImageView
             * Using listener for make sure the enter transition only called when loading completed
             * */
            Glide
                .with(this@DetailStoriesActivity)
                .load(stories.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Continue enter animation after image loaded
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(ivStoryImage)
        }
    }
}
