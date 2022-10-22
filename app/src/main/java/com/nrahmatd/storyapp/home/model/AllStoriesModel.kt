package com.nrahmatd.storyapp.home.model

import com.google.gson.annotations.SerializedName
import com.sagara.klipz.baseview.model.BaseAdapterModel

data class AllStoriesModel(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<Story>,
    @SerializedName("message")
    val message: String
) : BaseAdapterModel, java.io.Serializable {
    data class Story(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("name")
        val name: String,
        @SerializedName("photoUrl")
        val photoUrl: String
    ) : BaseAdapterModel, java.io.Serializable {
        override fun getType(): Int = 0
    }

    override fun getType(): Int = 0
}
