package ru.n00ner.testcontacs.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import ru.n00ner.testcontacs.model.Contact

interface ContactsApi {
    @GET("{name}.json")
    fun getContacts(@Path("name") name: String): Observable<List<Contact>>
}