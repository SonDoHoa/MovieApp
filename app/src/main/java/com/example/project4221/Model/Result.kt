package com.example.project4221.Model

import com.google.gson.annotations.SerializedName


data class Result(
    @SerializedName("results") val results : ArrayList<Movies>?,
    val page : Int?,
    @SerializedName("total_results")val total_results: Int?,
    @SerializedName("total_pages")val total_pages : Int?)
{
    constructor() : this(ArrayList(),null,null, null)
}