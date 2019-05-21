package ru.n00ner.testcontacs.ui.list

import ru.n00ner.testcontacs.model.Contact

interface ContactPickListener{
    fun onContactPicked(contact: Contact)
}