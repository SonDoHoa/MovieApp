package com.example.project4221.Room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Movies_Database")
data class MoviesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?=null,
    var name: String?,
    var description: String?,
    var backdropPath: String?
) : Parcelable{
    constructor(): this(null, "", "", "")
}