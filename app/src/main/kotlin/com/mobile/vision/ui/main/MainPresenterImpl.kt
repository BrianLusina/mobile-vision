package com.mobile.vision.ui.main

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
class MainPresenterImpl<V : MainView>
@Inject
constructor(mDataManager: DataManager,
            mSchedulerProvider: SchedulerProvider,
            mCompositeDisposable: CompositeDisposable)
    : MainPresenter<V>, BasePresenterImpl<V>(mDataManager, mSchedulerProvider,
        mCompositeDisposable) {

    override fun onAttach(mBaseView: V) {
        super.onAttach(mBaseView)
        baseView.requestAudioPermission()
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        baseView.setupAdapterAndRecycler()
    }

    override fun onSendMessageClicked(message: String) {
        dataManager.postUserMessage(message, "user")
        dataManager.postMessageQueryToAI(message)
        compositeDisposable.addAll(
                dataManager.makeAIRequest()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({
                            // on next, post bot message to db ref
                            val result = it.result.fulfillment.speech
                            dataManager.postBotMessage(result)
                        }, {
                            // on error
                            error("Error getting AI response ${it.message} => $it")
                        }, {
                            // on complete
                        })
        )
    }


    override fun onAudioButtonClicked() {

    }

    override fun onDetach() {
        super.onDetach()
        dataManager.stopAiService()
        compositeDisposable.dispose()
    }
}