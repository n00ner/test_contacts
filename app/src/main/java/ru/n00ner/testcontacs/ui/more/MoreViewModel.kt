package ru.n00ner.testcontacs.ui.more

import android.arch.lifecycle.MutableLiveData
import ru.n00ner.testcontacs.base.BaseViewModel
import ru.n00ner.testcontacs.model.Contact

class MoreViewModel: BaseViewModel() {
    private val contactName = MutableLiveData<String>()
    private val contactPhone = MutableLiveData<String>()
    private val contactPeriod = MutableLiveData<String>()
    private val contactBio  = MutableLiveData<String>()
    private val contactTemp = MutableLiveData<String>()

    fun bind(contact: Contact){
        contactName.value = contact.name
        contactPhone.value = contact.phone
        contactPeriod.value = contact.educationPeriod.toString()
        contactBio.value = contact.biography
        contactTemp.value = contact.temperament
    }


    fun getContactName(): MutableLiveData<String> {
        return contactName
    }

    fun getContactPhone(): MutableLiveData<String> {
        return contactPhone
    }

    fun getContactPeriod(): MutableLiveData<String> {
        return contactPeriod
    }

    fun getContactTemp(): MutableLiveData<String>{
        return contactTemp
    }

    fun getContactBio(): MutableLiveData<String>{
        return contactBio
    }
}