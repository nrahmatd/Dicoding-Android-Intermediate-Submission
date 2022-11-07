package com.nrahmatd.storyapp

import com.nrahmatd.storyapp.home.model.AllStoriesModel

object DataDummy {

    fun generateDummyStoriesResponse(): List<AllStoriesModel.Story> {
        val items: MutableList<AllStoriesModel.Story> = arrayListOf()
        for (i in 0..100) {
            val story = AllStoriesModel.Story(
                id = i.toString(),
                name = "Mamat",
                description = "Alat Pemadam Kebakaran",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1667831122851_lKTktHqW.jpg",
                createdAt = "2022-11-07T14:25:22.854Z",
                lat = -6.268288,
                lon = 106.89265
            )

            items.add(story)
        }
        return items
    }
}
