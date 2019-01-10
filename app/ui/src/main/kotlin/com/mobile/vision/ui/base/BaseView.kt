package com.mobile.vision.ui.base

import android.support.annotation.StringRes

/**
 * @author lusinabrian on 01/04/17
 * * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * * pattern must implement. Generally this interface will be extended by a more specific interface
 * * that then usually will be implemented by an Activity or Fragment.
 */

interface BaseView {

    /**
     * Hides keyboard */
    fun hideKeyboard()

    /**
     * Displays a snack bar
     * @param message Message to display on the snackbar
     * @param length for how long to display the snackbar
     * @param rootLayout Root Layout to use for the snackbar
     * @param networkError Whether this is a network error to use
     * */
    fun displaySnackbar(message : String, length : Int, rootLayout : Int, networkError : Boolean)

    /**
     * Override method when using String resource
     * */
    fun displaySnackbar(@StringRes message: Int, length : Int, rootLayout : Int, networkError: Boolean)
}
