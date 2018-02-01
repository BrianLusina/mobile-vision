package com.mobile.vision.data

import com.mobile.vision.data.api.ApiHelper
import com.mobile.vision.data.files.FileHelper
import com.mobile.vision.data.prefs.SharedPrefsHelper

/**
 * @author lusinabrian on 23/09/17.
 * @Notes interface for model layer to delegate model functions
 */
interface DataManager : ApiHelper, SharedPrefsHelper, FileHelper{
}