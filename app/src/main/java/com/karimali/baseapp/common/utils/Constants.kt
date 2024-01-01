package com.karimali.baseapp.common.utils

import android.Manifest
import com.karimali.baseapp.R


object Constants {

    object Route {
        const val BASE_URL = ""

    }
    object DeepLinks {

    }

    object Keys {
        const val USER_KEY: String = "UserKey"
    }

    object Links {

    }

    object Ads {
    }

    object COLOR {
    }

    object Preferences {
        // Preference Comment
        // You can use MODE_NIGHT_FOLLOW_SYSTEM or MODE_NIGHT_NO or MODE_NIGHT_YES as default value for dark mode.
        const val DARK_MODE_KEY_DEFAULT_VALUE = "MODE_NIGHT_NO"
        const val LANGUAGE_KEY = "language"
        const val DARK_MODE_KEY = "dark_mode"
        const val RATE_KEY = "rate"
        const val SHARE_APP_KEY = "share_app"
        const val DISABLE_ADS_KEY = "disable_ads"
        const val VERSION_KEY = "version"
        const val LAUNCH_COUNT_KEY = "launch_ad_count"
    }

    object Tags {
        const val LANGUAGE_CHANGED_BY_USER_FLAG_TAG = "LANGUAGE_CHANGED_BY_USER_FLAG"
        const val SHOULD_SHOW_ON_BOARDING_TO_USER_FOR_FIRST_TIME = "SHOULD_SHOW_ON_BOARDING_TO_USER_FOR_FIRST_TIME"
        const val SUBSCRIBE_KEY = "subscribe"
        const val MAIN_SELECTED_TAB_TAG_ID = "selectedTab"
        const val SELECTED_CONTENT_ID = "selectedContent"
        const val TRACK_ID = "8"
        const val SETTING_TAB = 1
        const val AD_TYPE = 2
        const val CONTENT_TYPE = 1
    }

    object Actions {

    }

    object FilePickerConst {
        const val REQUEST_CODE_PROFILE_PHOTO = 231
        const val REQUEST_CODE_CAVER_PHOTO = 232
        const val REQUEST_CODE_PHOTO = 233
        const val REQUEST_CODE_DOC = 234


        const val REQUEST_CODE_MEDIA_DETAIL = 235
        const val REQUEST_CODE_PERMISSION = 988

        const val DEFAULT_MAX_COUNT = -1
        const val DEFAULT_COLUMN_NUMBER = 3
        const val DEFAULT_FILE_SIZE = Int.MAX_VALUE

        const val MEDIA_PICKER = 0x11
        const val DOC_PICKER = 0x12

        const val KEY_SELECTED_MEDIA = "SELECTED_PHOTOS"
        const val KEY_SELECTED_DOCS = "SELECTED_DOCS"

        const val EXTRA_IMAGE_FILE_SIZE = "EXTRA_IMAGE_FILE_SIZE"
        const val EXTRA_VIDEO_FILE_SIZE = "EXTRA__VIDEO_FILE_SIZE"
        const val EXTRA_DOC_FILE_SIZE = "EXTRA_DOC_FILE_SIZE"
        const val EXTRA_PICKER_TYPE = "EXTRA_PICKER_TYPE"
        const val EXTRA_SHOW_GIF = "SHOW_GIF"
        const val EXTRA_FILE_TYPE = "EXTRA_FILE_TYPE"
        const val EXTRA_BUCKET_ID = "EXTRA_BUCKET_ID"
        const val ALL_PHOTOS_BUCKET_ID = "ALL_PHOTOS_BUCKET_ID"
        const val PPT_MIME_TYPE = "application/mspowerpoint"

        const val FILE_TYPE_MEDIA = 1
        const val FILE_TYPE_DOCUMENT = 2

        const val MEDIA_TYPE_IMAGE = 1
        const val MEDIA_TYPE_VIDEO = 3

        const val PERMISSIONS_FILE_PICKER = Manifest.permission.READ_EXTERNAL_STORAGE

        val docExtensions = arrayOf("ppt", "pptx", "xls", "xlsx", "doc", "docx", "dot", "dotx")

        const val PDF = "PDF"
        const val PPT = "PPT"
        const val DOC = "DOC"
        const val XLS = "XLS"
        const val TXT = "TXT"

        enum class FILE_TYPE {
            PDF, WORD, EXCEL, PPT, TXT, UNKNOWN
        }

        enum class SPAN_TYPE{
            FOLDER_SPAN, DETAIL_SPAN
        }
    }


    var states = arrayOf(
        intArrayOf(android.R.attr.state_enabled)
    )

    val destinationWithNoBackButton = arrayOf(
        R.id.navigation_home
    )

    var txtFieldStates = intArrayOf(android.R.attr.state_enabled,android.R.attr.state_focused)

}