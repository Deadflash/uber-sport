package com.fcpunlimited.ubersport.view.main.search

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.SportsQuery
import com.fcpunlimited.ubersport.di.game.GameFilterContainer
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.game.GamesLiveDataContainer
import com.fcpunlimited.ubersport.di.game.HttpEmptyResponseCallBack

@InjectViewState
class SearchPresenter(private val gameModel: GameModel,
                      private val gamesLiveDataContainer: GamesLiveDataContainer,
                      private val filter: GameFilterContainer)
    : MvpPresenter<SearchView>(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateSearchView() {
        if (gamesLiveDataContainer.gameData.value == null) {
            loadGames()
        }
        if (gamesLiveDataContainer.sportsData.value == null) {
            loadSports()
        }
    }

    fun loadSports() {
        gameModel.getSports(object : HttpEmptyResponseCallBack {
            override fun onFailure(message: String) {
                viewState.showMessage(message)
            }
        })
    }

    fun loadGames() {
        viewState.onSwipeRefresh(true)
        gameModel.getGames(object : HttpEmptyResponseCallBack {
            override fun onResponse() {
                viewState.onSwipeRefresh(false)
            }

            override fun onFailure(message: String) {
                viewState.onSwipeRefresh(false)
                viewState.showMessage(message)
            }
        })
    }

    fun updateUserSportFilterCount(sports: List<SportsQuery.Sport>) {
        val userSportIds = filter.getUserFilterSportIds()
        val sportIds = sports.map { it.id() }
//        val currentSportFilterIds = userSportIds
//                .filter { userSportId -> sportIds.contains(userSportId) }
//                .toSet()
//        filter.updateUserFilterSportIds(currentSportFilterIds)
//        viewState.showSportsFilterCount(currentSportFilterIds.size)
        viewState.showSportsFilterCount(0)
    }
}