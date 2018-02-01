package com.mobile.vision.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.mobile.vision.R
import com.mobile.vision.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView, View.OnClickListener {

    var flagFab: Boolean = true
    private val audioRequestPermissionCode = 1

    @Inject
    lateinit var mainPresenter: MainPresenter<MainView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent.injectMainActivity(this)

        mainPresenter.onAttach(this)

        mainPresenter.onViewCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.onResume()
    }

    override fun onStop() {
        mainPresenter.onDetach()
        super.onStop()
    }

    override fun onDestroy() {
        mainPresenter.onDetach()
        super.onDestroy()
    }

    override fun setupListeners() {
        addBtn.setOnClickListener(this)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val sendImg = BitmapFactory.decodeResource(resources,
                        R.drawable.ic_send_white_24dp)
                val micImage = BitmapFactory.decodeResource(resources,
                        R.drawable.ic_mic_white_24dp)

                if (s.toString().trim { it <= ' ' }.isNotEmpty() && flagFab) {
                    imageViewAnimatedChange(fabImgView, sendImg)
                    flagFab = false
                } else if (s.toString().trim { it <= ' ' }.isEmpty()) {
                    imageViewAnimatedChange(fabImgView, micImage)
                    flagFab = true
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.addBtn -> {
                val message = editText.text.toString().trim { it <= ' ' }
                if (!message.isEmpty()) {
                    mainPresenter.onSendMessageClicked(message)
                    editText.setText("")
                }
            }
        }
    }

    /**
     * Animation change handler for the send button
     * */
    private fun imageViewAnimatedChange(v: ImageView, new_image: Bitmap) {
        val animOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
        val animIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        animOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                v.setImageBitmap(new_image)
                animIn.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationRepeat(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {}
                })
                v.startAnimation(animIn)
            }
        })
        v.startAnimation(animOut)
    }

    /**
     * What should we do with the permissions we now have?
     * */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == audioRequestPermissionCode) {

        }
    }
}
