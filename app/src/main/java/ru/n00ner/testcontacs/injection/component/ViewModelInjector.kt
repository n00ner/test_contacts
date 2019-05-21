package ru.n00ner.testcontacs.injection.component

import dagger.Component
import ru.n00ner.testcontacs.injection.module.NetworkModule
import ru.n00ner.testcontacs.ui.list.ContactListViewModel
import ru.n00ner.testcontacs.ui.list.ContactViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    fun inject(contactViewModel: ContactViewModel)
    fun inject(contactListViewModel: ContactListViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}