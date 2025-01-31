package com.mordentech.chatapp.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.GONE
}
fun View.snackbar(message: String){
    Snackbar.make(this,message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun ViewPager2.nextPage(smoothScroll: Boolean = true): Boolean {
    if ((currentItem + 1) < (adapter?.itemCount ?: 0)) {
        setCurrentItem(currentItem + 1, smoothScroll)
        if ((currentItem + 1) == (adapter?.itemCount ?: 0)) {
            return true
        }
        return false
    }
    //can't move to next page, maybe current page is last or adapter not set.
    return true
}

fun ViewPager2.previousPage(smoothScroll: Boolean = true): Boolean {
    if ((currentItem - 1) >= 0) {
        setCurrentItem(currentItem - 1, smoothScroll)
        if ((currentItem - 1) < 0){
            return true
        }
        return false
    }
    //can't move to previous page, maybe current page is first or adapter not set.
    return true
}

fun ViewPager2.scroll(smoothScroll: Boolean = true): Boolean {
    if ((currentItem + 1) < (adapter?.itemCount ?: 0)) {
        setCurrentItem(currentItem + 1, smoothScroll)
//        if ((currentItem + 1) == (adapter?.itemCount ?: 0)) {
//            return false
//        }
        return true
    }
    setCurrentItem(0, smoothScroll)
    //can't move to next page, maybe current page is last or adapter not set.
    return false
}

fun ViewPager2.scrollTo(position: Int ,smoothScroll: Boolean = true): Boolean {
    if ((position >= 0) and (position <= (adapter?.itemCount ?: 0))) {
        setCurrentItem(position, smoothScroll)
        return true
    }
    return false
}
