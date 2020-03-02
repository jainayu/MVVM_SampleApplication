package com.example.mvvmsampleapplication.ui.home.quotes

import androidx.databinding.Bindable
import com.example.mvvmsampleapplication.R
import com.example.mvvmsampleapplication.data.db.entities.Quote
import com.example.mvvmsampleapplication.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
): BindableItem<ItemQuoteBinding>(){
    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }

}