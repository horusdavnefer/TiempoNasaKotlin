package com.tiempo.nasa.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tiempo.nasa.db.entities.Apollo

@Dao
interface ApolloDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addApollo(apollo: Apollo)

    @Delete
    fun deleteApollo(apollo: Apollo)

    @Update
    fun updateApollo(apollo: Apollo)

    @Query("SELECT * FROM Apollo")
    fun getAllApollo(): List<Apollo>
}