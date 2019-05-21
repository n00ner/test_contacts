package ru.n00ner.testcontacs.ui.list

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.CheckResult
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.n00ner.testcontacs.R
import ru.n00ner.testcontacs.base.BaseViewModel
import ru.n00ner.testcontacs.model.Contact
import ru.n00ner.testcontacs.network.ContactsApi
import ru.n00ner.testcontacs.utils.Storage
import javax.inject.Inject

class ContactListViewModel(contactPickListener: ContactPickListener): BaseViewModel(){
    @Inject
    lateinit var contactsApi: ContactsApi

    val contactListAdapter: ContactListAdapter = ContactListAdapter(contactPickListener)

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable


    init {
        loadContacts()
    }

    fun updateFromSwipe(){
        loadContacts()
    }

    @SuppressWarnings("CheckResult")
    private fun loadContacts(){
        onRetrieveContactListStart()
        if(Storage().isLoadingAllowed()){
            fetchContacts()
        }else{
            onRetrieveContactListSuccess(Storage().contacts!!.toList())
            onRetrieveContactListFinish()
        }

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrieveContactListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveContactListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveContactListSuccess(contactList:List<Contact>){
        contactListAdapter.updateContactList(contactList)
    }
    private fun onRetrievePostListError(){
        errorMessage.value = R.string.network_error
        onRetrieveContactListSuccess(Storage().contacts!!.toList())
        onRetrieveContactListFinish()
    }

    private fun fetchContacts(){
        contactsApi.getContacts("generated-01")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Storage().contacts = ArrayList()
                val listOne = Storage().contacts
                listOne?.addAll(it)
                Storage().contacts = listOne
                contactsApi.getContacts("generated-02")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val listTwo = Storage().contacts
                        listTwo?.addAll(it)
                        Storage().contacts = listTwo
                        contactsApi.getContacts("generated-03")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                val listThree = Storage().contacts
                                listThree?.addAll(it)
                                Storage().contacts = listThree
                                onRetrieveContactListSuccess(Storage().contacts!!.toList())
                                onRetrieveContactListFinish()
                            }, {
                                onRetrievePostListError()
                            })
                    }, {
                        onRetrievePostListError()
                    })
            }, {
                onRetrievePostListError()
            })
    }
}