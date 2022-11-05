package com.nrahmatd.storyapp.database.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: String,
    val prevKeys: Int?,
    val nextKeys: Int?
)
