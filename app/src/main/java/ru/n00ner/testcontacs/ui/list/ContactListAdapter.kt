package ru.n00ner.testcontacs.ui.list

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import ru.n00ner.testcontacs.R
import ru.n00ner.testcontacs.model.Contact

class ContactListAdapter(private var clickListener: ContactPickListener): RecyclerView.Adapter<ContactListAdapter.ViewHolder>(),
    Filterable {

    private var contactList:List<Contact> = ArrayList<Contact>()
    private var pickedContacts = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListAdapter.ViewHolder {
        val binding: ru.n00ner.testcontacs.databinding.ItemContactBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_contact, parent, false)
        return ViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ContactListAdapter.ViewHolder, position: Int) {
        holder.bind(pickedContacts[position])


    }

    override fun getItemCount(): Int {
        return pickedContacts.size
    }

    fun updateContactList(contactList:List<Contact>){
        this.contactList = contactList
        this.pickedContacts.addAll(contactList)
        notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    pickedContacts.clear()
                    pickedContacts.addAll(contactList)
                } else {
                    val matchesList = ArrayList<Contact>()
                    for (contact in contactList) {
                        if (contact.name.toLowerCase().contains(charString.toLowerCase()) || contact.phone.contains(charSequence)) {
                            matchesList.add(contact)
                        }
                    }
                     pickedContacts = matchesList
                }
                val filterResults = FilterResults()
                filterResults.values = pickedContacts
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                Log.d("Adapter", "Publishing results!")
                pickedContacts = filterResults.values as ArrayList<Contact>
                notifyDataSetChanged()
            }
        }
    }



    class ViewHolder(private val binding: ru.n00ner.testcontacs.databinding.ItemContactBinding, private val clickListener: ContactPickListener):RecyclerView.ViewHolder(binding.root){
        private val viewModel = ContactViewModel()

        fun bind(contact:Contact){
            viewModel.bind(contact)
            binding.viewModel = viewModel
            binding.contactLayout.setOnClickListener {
                clickListener.onContactPicked(contact)
            }
        }
    }
}