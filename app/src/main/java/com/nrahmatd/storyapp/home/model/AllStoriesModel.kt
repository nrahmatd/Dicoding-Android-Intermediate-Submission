package com.nrahmatd.storyapp.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    @Entity(tableName = "stories")
    data class Story(
        @field:SerializedName("createdAt")
        val createdAt: String,
        @field:SerializedName("description")
        val description: String,
        @PrimaryKey
        @field:SerializedName("id")
        val id: String,
        @field:SerializedName("lat")
        val lat: Double? = null,
        @field:SerializedName("lon")
        val lon: Double? = null,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("photoUrl")
        val photoUrl: String
    ) : BaseAdapterModel, java.io.Serializable {
        override fun getType(): Int = 0
    }

    override fun getType(): Int = 0
}
