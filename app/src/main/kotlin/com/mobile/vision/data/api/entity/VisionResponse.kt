package com.mobile.vision.data.api.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author lusinabrian on 03/02/18.
 * @Notes Vision Response
 */
data class VisionResponse(
        @SerializedName("web")
        @Expose
        var web: Web)