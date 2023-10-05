package com.mel.allmovies.viewModel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class AppViewModel: ViewModel() {
    protected var disposable: CompositeDisposable = CompositeDisposable()
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}