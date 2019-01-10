package com.mobile.vision.data.events

/**
 * @author lusinabrian on 08/02/18.
 * @Notes Image Upload event posted to EventBus for subscribers to listen to
 */
data class ImageUploadEvent(
        var uploadSuccess: Boolean = false,
        var uploadProgress: Int = 1,
        var isUploading: Boolean = false
)