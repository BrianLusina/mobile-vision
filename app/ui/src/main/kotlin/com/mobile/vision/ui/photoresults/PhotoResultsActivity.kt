package com.mobile.vision.ui.photoresults

import android.os.Bundle
import android.view.View
import com.mobile.vision.R
import com.mobile.vision.ui.base.BaseActivity


class PhotoResultsActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_capture)

        activityComponent.injectPhotoResultsActivity(this)
    }

}
