package com.fcpunlimited.ubersport.struct.game

import androidx.recyclerview.widget.DiffUtil

class GameDtoDiffUtilCallback(private val oldGamesDto: List<GameDto>,
                              private val newGamesDto: List<GameDto>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldGamesDto.size

    override fun getNewListSize(): Int = newGamesDto.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldGamesDto[oldItemPosition].game.id() == newGamesDto[newItemPosition].game.id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldGamesDto[oldItemPosition].game == newGamesDto[oldItemPosition].game
}