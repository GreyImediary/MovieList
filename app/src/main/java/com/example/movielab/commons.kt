package com.example.movielab

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

typealias AnyMap= Map<String, Any>

const val posterDefaultPath = "https://image.tmdb.org/t/p/w1280"
const val movieIdKey = "movieId"
const val password = "password"
const val passPref = "PASSWORD"

fun ViewGroup.inflate(layout: Int): View =
        LayoutInflater.from(context).inflate(layout, this, false)

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun RecyclerView.onEndScroll(f: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (!recyclerView.canScrollVertically(1)) {
                f()
            }
        }
    })
}

fun TextInputEditText.onTextChange(f: (text: CharSequence?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            f(s)
        }
    })
}