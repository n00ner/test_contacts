package ru.n00ner.testcontacs.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import ru.n00ner.testcontacs.R
import ru.n00ner.testcontacs.databinding.FragmentContactMoreBinding
import ru.n00ner.testcontacs.databinding.FragmentContactsBinding
import ru.n00ner.testcontacs.model.Contact
import ru.n00ner.testcontacs.ui.list.ContactListFragment
import ru.n00ner.testcontacs.utils.BUNDLE_ARGS

class MoreFragment : Fragment(){
    private lateinit var viewModel:MoreViewModel
    private lateinit var binding: FragmentContactMoreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContactMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments == null){
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.main_container,ContactListFragment() )
                ?.addToBackStack(null)
                ?.commit()
        }else{
            val contact = Gson().fromJson(arguments!!.getString(BUNDLE_ARGS), Contact::class.java)
            binding.textMoreBio.text = contact.biography
            binding.textMoreName.text = contact.name
            binding.textMoreTemp.text = contact.temperament
            binding.textMorePeriod.text = contact.educationPeriod.toString()
            binding.textMorePhone.text = contact.phone
            binding.textMorePhone.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${contact.phone}")
                startActivity(intent)
            }
        }

        viewModel = MoreViewModel()
        binding.viewModel = viewModel
    }

}