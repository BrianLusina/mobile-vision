package com.mobile.vision.data.api.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author lusinabrian on 03/02/18.
 * @Notes Web entity
 */

data class Web(
        @SerializedName("webEntities")
        @Expose
        var webEntities: List<WebEntity>)

data class WebEntity(
        @SerializedName("score")
        @Expose
        var score: Double,

        @SerializedName("entityId")
        @Expose
        var entityId: String,

        @SerializedName("description")
        @Expose
        var description: String)