package com.example.basicnotepad.commons.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun ConstraintSet.match(view: View, parentView: View) {
    this.connect(view.id, ConstraintSet.TOP, parentView.id, ConstraintSet.TOP)
    this.connect(view.id, ConstraintSet.START, parentView.id, ConstraintSet.START)
    this.connect(view.id, ConstraintSet.END, parentView.id, ConstraintSet.END)
    this.connect(view.id, ConstraintSet.BOTTOM, parentView.id, ConstraintSet.BOTTOM)
}

fun Activity.lockScreen(lockScreen: Boolean) {
    if (lockScreen) {
        val window: Window = this.window
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }
}

inline fun <T> Fragment.observe(liveData: LiveData<T>, crossinline onChanged: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { onChanged(it) })
}

fun Fragment.lockScreen(lockScreen: Boolean) {
    val window: Window = this.requireActivity().window
    if (lockScreen) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }
}

fun Fragment.hideKeyboard() {
    val inputManager =
        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = activity?.currentFocus ?: View(activity)
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}