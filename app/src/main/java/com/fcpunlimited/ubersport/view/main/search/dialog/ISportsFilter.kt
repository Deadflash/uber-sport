package com.fcpunlimited.ubersport.view.main.search.dialog

interface ISportsFilter {
    fun addFilterSport(sportId: String)
    fun removeFilterSportId(sportId: String)
    fun getFilteredSports(): MutableSet<String>?
}