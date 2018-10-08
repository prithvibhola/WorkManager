package prithvi.io.workmanager.data.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [
    Location::class
], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}