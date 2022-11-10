package com.example.demo_sports.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlin.collections.HashMap


@Parcelize
data class ResponseFromApi(
    @SerializedName("Matchdetail")
    val matchdetail: Matchdetail,
    @SerializedName("Teams")
    val teams: HashMap<String, Teams>
) : Parcelable

@Parcelize
data class Matchdetail(
    val Match: Match,
    val Series: Series,
    val Team_Away: String,
    val Team_Home: String,
    val Venue: Venue
) : Parcelable

@Parcelize
data class Match(
    val Code: String,
    val Date: String,
    val Daynight: String,
    val Id: String,
    val League: String,
    val Livecoverage: String,
    val Number: String,
    val Offset: String,
    val Time: String,
    val Type: String
) : Parcelable

@Parcelize
data class Venue(
    val Id: String,
    val Name: String
) : Parcelable

@Parcelize
data class Series(
    val Id: String,
    val Name: String,
    val Status: String,
    val Tour: String,
    val Tour_Name: String
) : Parcelable

@Parcelize
data class Teams(
    @SerializedName("Name_Full")
    val Name_Full: String,
    @SerializedName("Name_Short")
    val Name_Short: String,
    @SerializedName("Players")
    val players: HashMap<String, Players>
) : Parcelable

@Parcelize
data class Players(
    @SerializedName("Name_Full")
    val Name_Full: String,
    @SerializedName("Iscaptain")
    val Iscaptain: Boolean,
    @SerializedName("Iskeeper")
    val Iskeeper: Boolean
) : Parcelable
