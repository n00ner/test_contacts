package ru.n00ner.testcontacs.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.n00ner.testcontacs.R
import ru.n00ner.testcontacs.ui.list.ContactListFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = ""
        supportFragmentManager
                .beginTransaction()
                .add(R.id.main_container, ContactListFragment())
                .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun showSearchView(show: Boolean) {
        if(show){
            search_layout.visibility = View.VISIBLE
        }else{
            search_layout.visibility = View.GONE
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(!show)
        supportActionBar?.setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back))
    }
}
