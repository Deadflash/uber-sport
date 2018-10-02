package com.fcpunlimited.ubersport.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.arellomobile.mvp.MvpDelegate

abstract class BaseMvpFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentLayout(), container, false)
    }

    private var mIsStateSaved: Boolean = false
    private var mMvpDelegate: MvpDelegate<out BaseMvpFragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        this.mIsStateSaved = false
        this.getMvpDelegate().onAttach()
    }

    override fun onResume() {
        super.onResume()
        this.mIsStateSaved = false
        this.getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        this.mIsStateSaved = true
        this.getMvpDelegate().onSaveInstanceState(outState)
        this.getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()
        this.getMvpDelegate().onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.getMvpDelegate().onDetach()
        this.getMvpDelegate().onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.activity!!.isFinishing) {
            this.getMvpDelegate().onDestroy()
        } else if (this.mIsStateSaved) {
            this.mIsStateSaved = false
        } else {
            var anyParentIsRemoving = false

            var parent = this.parentFragment
            while (!anyParentIsRemoving && parent != null) {
                anyParentIsRemoving = parent.isRemoving
                parent = parent.parentFragment
            }

            if (this.isRemoving || anyParentIsRemoving) {
                this.getMvpDelegate().onDestroy()
            }

        }
    }

    private fun getMvpDelegate(): MvpDelegate<*> {
        if (this.mMvpDelegate == null) {
            this.mMvpDelegate = MvpDelegate(this)
        }

        return this.mMvpDelegate!!
    }

    abstract fun getFragmentLayout(): Int

    abstract fun getFragmentTag(): String

    abstract fun getFragmentMenu(): Int?
}