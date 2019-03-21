package com.fcpunlimited.ubersport.utils

import android.content.res.Resources
import com.fcpunlimited.ubersport.R

val Int.toPx: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

enum class SportType(val iconId: Int) {
    Football(R.drawable.ic__ionicons_svg_md_football),
    Basketball(R.drawable.ic__ionicons_svg_md_basketball),
    Volleyball(R.drawable.ic__ionicons_svg_md_tennisball),
    Paintball(R.drawable.ic__ionicons_svg_md_baseball),
    Tennis(R.drawable.ic__ionicons_svg_md_tennisball),
    Bicycle(R.drawable.ic__ionicons_svg_md_american_football)
}

fun getSportIconByName(sportName: String): Int {
    val containsSport = SportType.values().map { sportType -> sportType.name }.contains(sportName)
    if (containsSport)
        return SportType.valueOf(sportName).iconId
    return SportType.Football.iconId
}