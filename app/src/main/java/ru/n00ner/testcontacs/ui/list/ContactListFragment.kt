package ru.n00ner.testcontacs.ui.list

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ImageView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_contacts.*
import ru.n00ner.testcontacs.R
import ru.n00ner.testcontacs.databinding.FragmentContactsBinding
import ru.n00ner.testcontacs.model.Contact
import ru.n00ner.testcontacs.ui.MainActivity
import ru.n00ner.testcontacs.ui.more.MoreFragment
import ru.n00ner.testcontacs.utils.BUNDLE_ARGS

class ContactListFragment : Fragment(), ContactPickListener{

    private lateinit var binding: FragmentContactsBinding
    private lateinit var viewModel: ContactListViewModel
    private var errorSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showSearchView(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactList.layoutManager = LinearLayoutManager(activity)
        binding.contactList
        viewModel = ContactListViewModel(this)
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        viewModel.loadingVisibility.observe(this, Observer {
                loadingVisibility->if(loadingVisibility == View.GONE) {
                    swipe_contact_list.isRefreshing = false
                    contact_list.visibility = View.VISIBLE
                }else{
                    contact_list.visibility = View.GONE
                }
        })
        binding.viewModel = viewModel

        swipe_contact_list.setOnRefreshListener {
            viewModel.updateFromSwipe()
        }
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    override fun onContactPicked(contact: Contact) {
        (activity as MainActivity).showSearchView(false)
        val fragment = MoreFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_ARGS, Gson().toJson(contact))
        fragment.arguments = bundle
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.main_container,fragment )
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = activity!!.findViewById<SearchView>(R.id.search_view)
        searchView.clearFocus()
        searchView.setSearchableInfo(searchManager
            .getSearchableInfo(activity!!.componentName))
        val queryTextWatcher = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.contactListAdapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                viewModel.contactListAdapter.filter.filter(query)
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextWatcher)
    }
}