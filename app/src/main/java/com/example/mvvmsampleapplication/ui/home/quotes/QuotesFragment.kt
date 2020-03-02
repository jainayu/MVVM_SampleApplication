package com.example.mvvmsampleapplication.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmsampleapplication.R
import com.example.mvvmsampleapplication.data.db.entities.Quote
import com.example.mvvmsampleapplication.util.Coroutines
import com.example.mvvmsampleapplication.util.hide
import com.example.mvvmsampleapplication.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance()

    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(QuotesViewModel::class.java)

        bindUI()
        /*
        Coroutines.main{
            val quotes = viewModel.quotes.await()
            quotes.observe(viewLifecycleOwner, Observer {
                context?.toast(it.size.toString())
            })
        }
        */

    }

    private fun bindUI() = Coroutines.main{
        progress_bar.show()
        viewModel.quotes.await().observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {

        val rvAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(quoteItem)
        }
        recyclerview.apply{
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = rvAdapter
        }
    }

    private fun List<Quote>.toQuoteItem(): List<QuoteItem>{
        return this.map{
            QuoteItem(it)
        }
    }

}
