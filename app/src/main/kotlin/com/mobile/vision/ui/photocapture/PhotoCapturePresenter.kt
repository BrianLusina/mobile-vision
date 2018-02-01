package com.mobile.vision.ui.photocapture

import android.os.Bundle
import com.mobile.vision.ui.base.BasePresenter

/**
 * @author lusinabrian on 20/09/17.
 * @Notes Presenter layer
 */
interface PhotoCapturePresenter<V : PhotoCaptureView> : BasePresenter<V> {

    /**
     * on Start
     * */
    fun onStart()

    /**
     * ON Resume
     * */
    fun onResume()

    fun onViewCreated(savedInstanceState: Bundle?)

    /**
     * Callback for when the send message button is clicked
     * */
    fun onSendMessageClicked(message: String)

    /**
     * callback for when the audion button is clicked
     * */
    fun onAudioButtonClicked()
}