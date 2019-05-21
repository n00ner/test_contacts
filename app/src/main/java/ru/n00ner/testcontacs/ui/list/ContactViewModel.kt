package ru.n00ner.testcontacs.ui.list

import android.arch.lifecycle.MutableLiveData
import ru.n00ner.testcontacs.base.BaseViewModel
import ru.n00ner.testcontacs.model.Contact

class ContactViewModel: BaseViewModel() {
    private val contactName = MutableLiveData<String>()
    private val contactPhone = MutableLiveData<String>()
    private val contactHeight = MutableLiveData<String>()

    fun bind(contact: Contact){
        contactName.value = contact.name
        contactPhone.value = contact.phone
        contactHeight.value = contact.height.toString()
    }


    fun getContactName(): MutableLiveData<String>{
        return contactName
    }

    fun getContactPhone(): MutableLiveData<String>{
        return contactPhone
    }

    fun getContactHeight(): MutableLiveData<String>{
        return contactHeight
    }
}