package prithvi.io.workmanager.data.persistence

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
        @PrimaryKey @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "latitude") val latitude: String,
        @ColumnInfo(name = "longitude") val longitude: String
)