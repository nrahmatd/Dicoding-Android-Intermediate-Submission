package com.nrahmatd.storyapp.database.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nrahmatd.storyapp.home.model.AllStoriesModel

@Dao
interface StoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(quote: List<AllStoriesModel.Story>?)

    @Query("SELECT * FROM stories")
    fun getAllQuote(): PagingSource<Int, AllStoriesModel.Story>

    @Query("DELETE FROM stories")
    suspend fun deleteAll()
}
