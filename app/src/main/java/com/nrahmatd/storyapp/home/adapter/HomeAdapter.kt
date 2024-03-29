package com.nrahmatd.storyapp.home.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nrahmatd.storyapp.databinding.ItemStoriesBinding
import com.nrahmatd.storyapp.detail.view.DetailStoriesActivity
import com.nrahmatd.storyapp.home.model.AllStoriesModel
import com.nrahmatd.storyapp.utils.loadImage
import com.nrahmatd.storyapp.utils.parseDate

class HomeAdapter :
    PagingDataAdapter<AllStoriesModel.Story, HomeAdapter.StoriesHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AllStoriesModel.Story>() {
            override fun areItemsTheSame(
                oldItem: AllStoriesModel.Story,
                newItem: AllStoriesModel.Story
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AllStoriesModel.Story,
                newItem: AllStoriesModel.Story
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesHolder =
        StoriesHolder(
            ItemStoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: StoriesHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class StoriesHolder(private val itemStoriesBinding: ItemStoriesBinding) :
        RecyclerView.ViewHolder(itemStoriesBinding.root) {

        fun bind(story: AllStoriesModel.Story) {
            with(itemStoriesBinding) {
                loadImage(story.photoUrl, ivContent, 0)
                tvName.text = story.name
                tvDesc.text = story.description
                tvCreateAt.text = parseDate(story.createdAt)

                root.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            it.context as Activity,
                            Pair(ivContent, "image"),
                            Pair(tvName, "name"),
                            Pair(tvCreateAt, "created_at"),
                            Pair(tvDesc, "description")
                        )

                    Intent(it.context, DetailStoriesActivity::class.java)
                        .putExtra("stories", story).also { intent ->
                            it.context.startActivity(
                                intent,
                                optionsCompat.toBundle()
                            )
                        }
                }
            }
        }
    }
}
