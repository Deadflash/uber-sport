package com.fcpunlimited.ubersport.view.main.create_game

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.fcpunlimited.ubersport.R
import com.fcpunlimited.ubersport.struct.event.CreateEventDto
import com.fcpunlimited.ubersport.view.BaseMvpActivity
import com.fcpunlimited.ubersport.view.adapters.CustomAdapter
import com.fcpunlimited.ubersport.view.adapters.IListItem
import kotlinx.android.synthetic.main.activity_create_game.*
import kotlinx.android.synthetic.main.recycler_container.*

class CreateGameActivity : BaseMvpActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)

        val events = arrayListOf<IListItem>(CreateEventDto("Football", 1),
                CreateEventDto("Volleyball", 1),
                CreateEventDto("Tennis", 1),
                CreateEventDto("Paintball", 1),
                CreateEventDto("Cycling", 1))

        val adapter = CustomAdapter()
        lifecycle.addObserver(adapter)
        adapter.add(events)

        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)

        fab.setOnClickListener {finish()}
    }
}
