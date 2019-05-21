package ru.n00ner.testcontacs.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.n00ner.testcontacs.base.BaseApplication
import ru.n00ner.testcontacs.model.Contact

class Storage() {
    private val APP_PREFERENCES = BaseApplication.applicationContext().packageName
    private val CONTACTS_STORAGE = "storage"
    private val LAST_LOAD = "last_load"

    val preferences: SharedPreferences = BaseApplication.applicationContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    var contacts: ArrayList<Contact>?
        get() = Gson().fromJson(preferences.getString(CONTACTS_STORAGE, "[]"), object : TypeToken<ArrayList<Contact>?>() {}.type)
        set(value) = preferences.edit().putString(CONTACTS_STORAGE, Gson().toJson(value)).apply()

    var lastLoad: Long
        get() = preferences.getLong(LAST_LOAD, 0L)
        set(value) = preferences.edit().putLong(LAST_LOAD, value).apply()

    fun isLoadingAllowed(): Boolean{
        if( (contacts?.size == 0 || lastLoad == 0L) || (contacts?.size == 0 && lastLoad == 0L) ){
            lastLoad = System.currentTimeMillis()
            return true
        }
        return System.currentTimeMillis() - lastLoad >= 60000
    }
}