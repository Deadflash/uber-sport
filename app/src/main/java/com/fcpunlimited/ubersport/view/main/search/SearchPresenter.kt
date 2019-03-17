package com.fcpunlimited.ubersport.view.main.search

import androidx.lifecycle.*
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fcpunlimited.ubersport.GamesQuery
import com.fcpunlimited.ubersport.di.game.GameModel
import com.fcpunlimited.ubersport.di.gql.GraphQlResponseCallback
import com.fcpunlimited.ubersport.struct.game.GameDto
import com.fcpunlimited.ubersport.type.GameFiltersInput
import com.fcpunlimited.ubersport.type.GameStatus

@InjectViewState
class SearchPresenter(private val gameModel: GameModel) : MvpPresenter<SearchView>(),
        GraphQlResponseCallback<GamesQuery.Data>, LifecycleObserver {

    private val gameData: MutableLiveData<List<GameDto>> = MutableLiveData()

    fun getGameData(): LiveData<List<GameDto>> = gameData

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateSearchView() {
        if (gameData.value == null) {
            onSwipeRefresh()
        }
    }

    fun onSwipeRefresh() {
        viewState.onSwipeRefresh(true)
        val gameFilter = GameFiltersInput.builder()
                .status(GameStatus.PENDING)
                .build()
        gameModel.getGames(gameFilter, this@SearchPresenter)
    }

    override fun onOkResponse(data: GamesQuery.Data) {
        gameData.postValue(data.games()?.games()?.map(::GameDto)?.toCollection(arrayListOf()))
        viewState.onSwipeRefresh(false)
    }

    override fun onErrorResponse(message: String) {
        viewState.showMessage(message)
        viewState.onSwipeRefresh(false)
    }
}