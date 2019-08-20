//package com.fcpunlimited.ubersport.view.main.create_game
//
//import android.content.Context
//import android.os.Bundle
//import android.view.View
//import com.arellomobile.mvp.presenter.InjectPresenter
//import com.arellomobile.mvp.presenter.PresenterType
//import com.arellomobile.mvp.presenter.ProvidePresenter
//import com.fcpunlimited.ubersport.R
//import com.fcpunlimited.ubersport.utils.Constants.CREATE_GAME_FRAGMENT_TAG
//import com.fcpunlimited.ubersport.view.BaseMvpFragment
//import org.jetbrains.anko.runOnUiThread
//import org.jetbrains.anko.toast
//
//class CreateGameFragment : BaseMvpFragment(), CreateGameView {
//
//    companion object {
//        const val CREATE_GAME_FRAGMENT_PRESENTER = "createGameFragmentPresenter"
//    }
//
////    private val gamesLiveDataContainer: GamesLiveDataContainer by inject()
////    private val gameModel: GameModel by inject()
//
//    @InjectPresenter(type = PresenterType.GLOBAL, tag = CREATE_GAME_FRAGMENT_PRESENTER)
//    lateinit var presenter: CreateGamePresenter
//
//    @ProvidePresenter(type = PresenterType.GLOBAL, tag = CREATE_GAME_FRAGMENT_PRESENTER)
//    fun providePresenter() = CreateGamePresenter()
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        addPreferencesFromResource()
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//    override fun showMessage(message: String) {
//        context?.runOnUiThread { toast(message) }
//    }
//
//    override fun getFragmentLayout(): Int = R.layout.fragment_create_game
//
//    override fun getFragmentTag(): String = CREATE_GAME_FRAGMENT_TAG
//}
