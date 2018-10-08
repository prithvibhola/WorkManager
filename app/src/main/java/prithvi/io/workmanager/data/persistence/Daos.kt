package prithvi.io.workmanager.data.persistence

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(location: Location): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locations: List<Location>): List<Long>

    @Query("SELECT * FROM location WHERE id = :id")
    fun selectById(id: Long): Flowable<Location>

    @Query("SELECT * FROM location")
    fun selectAll(): Flowable<List<Location>>

    @Update
    fun update(location: Location): Int

    @Query("DELETE FROM location WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("DELETE FROM location")
    fun deleteAll(): Int
}