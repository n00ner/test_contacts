package ru.n00ner.testcontacs.base

import android.arch.lifecycle.ViewModel
import ru.n00ner.testcontacs.injection.component.DaggerViewModelInjector
import ru.n00ner.testcontacs.injection.component.ViewModelInjector
import ru.n00ner.testcontacs.injection.module.NetworkModule
import ru.n00ner.testcontacs.ui.list.ContactListViewModel
import ru.n00ner.testcontacs.ui.list.ContactViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ContactListViewModel -> injector.inject(this)
            is ContactViewModel -> injector.inject(this)
        }
    }
}