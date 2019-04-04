package com.fcpunlimited.ubersport.struct.game

import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.SportsQuery
import com.fcpunlimited.ubersport.view.adapters.IListItem

class SportFilterDto(var game: SportsQuery.Sport) : IListItem {
    override fun getLayoutId(): Int = R.layout.sport_filter_item
}