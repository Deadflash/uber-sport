package com.fcpunlimited.ubersport.struct.game

import androidx.recyclerview.widget.DiffUtil

class GameParticipantDiffUtilCallback(private val oldParticipant: List<GameParticipantsDto>,
                                      private val newParticipants: List<GameParticipantsDto>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldParticipant.size

    override fun getNewListSize(): Int = newParticipants.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldParticipant[oldItemPosition].participant.id() == newParticipants[newItemPosition].participant.id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldParticipant[oldItemPosition] == newParticipants[oldItemPosition]
}