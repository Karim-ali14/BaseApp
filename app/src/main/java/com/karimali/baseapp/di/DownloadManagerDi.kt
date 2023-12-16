package com.karimali.baseapp.di

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DownloadManagerDi {

    @Provides
    @Singleton
    fun providerDownloadManager(
        @ApplicationContext context: Context,
    ): DownloadManager {
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    fun  DownloadManager.makeRequest(
        @ApplicationContext context: Context,
        url:String,
        fileName:String ?= null,
        fileScope: FileScope,
    ): String? {
        val uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        val title = "File"
        var filePath : String ? = null
        request.setTitle("Fahimn-${title}")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        when(fileScope)
        {
            FileScope.APP_INTERNAL -> {
                request.setDestinationInExternalFilesDir(
                    context,
                    Environment.DIRECTORY_DOWNLOADS,
                    "$title"
                )
            }
            FileScope.EXTERNAL_DOWNLOADS -> {
                filePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/$title.pdf"
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title")
            }
        }
        this.enqueue(request)
        return filePath
    }

    enum class FileScope {
        APP_INTERNAL,
        EXTERNAL_DOWNLOADS,
    }
}
