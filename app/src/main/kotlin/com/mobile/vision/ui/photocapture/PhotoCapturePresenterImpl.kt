package com.mobile.vision.ui.photocapture

import android.os.Bundle
import com.chatbot.data.io.SchedulerProvider
import com.mobile.vision.ui.base.BasePresenterImpl
import com.mobile.vision.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author lusinabrian on 20/09/17.
 * @Notes presenter implementation
 */
class PhotoCapturePresenterImpl<V : PhotoCaptureView>
@Inject
constructor(mDataManager: DataManager,
            mSchedulerProvider: SchedulerProvider,
            mCompositeDisposable: CompositeDisposable)
    : PhotoCapturePresenter<V>, BasePresenterImpl<V>(mDataManager, mSchedulerProvider,
        mCompositeDisposable) {

    override fun onAttach(mBaseView: V) {
        super.onAttach(mBaseView)
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {

    }

    override fun onSendMessageClicked(message: String) {

    }


    override fun onAudioButtonClicked() {

    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.dispose()
    }
}