package com.karimali.baseapp.common.extensions

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import com.google.android.gms.common.util.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream



fun Uri.copyFileToInternalStorage(context: Context, newDirName: String): String {
    val returnUri = this

    val returnCursor: Cursor? = context.contentResolver?.query(
        returnUri,
        arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE),
        null,
        null,
        null
    )

    val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor?.moveToFirst()
    val name = (returnCursor?.getString(nameIndex!!))

    val output: File = if (!newDirName.equals("")) {
        val dir = File("${context.filesDir}/${newDirName}")
        if (!dir.exists()) {
            dir.mkdir()
        }
        File("${context.filesDir}/${newDirName}/${name}")
    } else {
        File("${context.filesDir}/${name}")
    }
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(this)
        val outputStream: FileOutputStream = FileOutputStream(output)
        var read = 0
        val bufferSize = 1024
        val byte = Array<Byte>(bufferSize, { Byte.MIN_VALUE })
        IOUtils.copyStream(inputStream!!, outputStream)
        inputStream.close()
        outputStream.close()
    } catch (e: Exception) {
    }


    return output.path
}

fun getItemViewType(extension: String): MediaType? {

    Log.i("MimeType", "getItemViewType: " + extension)
    if (extension == "jpg" ||
        extension == "jpeg" ||
        extension == "png"
    ) {
        return MediaType.IMAGE
    } else if (extension == "mp4" ||
        extension == "webm" ||
        extension == "m4p" ||
        extension == "m4v" ||
        extension == "avi" ||
        extension == "wmv" ||
        extension == "quicktime"
    ) {
        return MediaType.VIDEO
    }
    return null
}

fun File.getMimeType(): String? {
    var type: String? = null
    val extension = MimeTypeMap.getFileExtensionFromUrl(this.path)
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    return type
}

fun String.getUrlFileType(): MediaType? {
    val extension = try {
        this.split(".").last()
    }catch (e:java.lang.Exception){
        ""
    }
    Log.i("MimeType", "getItemViewType: $extension")
    if (extension.contains( "jpg" , true)||
        extension.contains( "jpeg",true) ||
        extension.contains( "png" , true)
    ) {
        return MediaType.IMAGE
    } else if (
        extension.contains( "mp4" , true)||
        extension.contains( "webm" , true)||
        extension.contains( "m4p" , true)||
        extension.contains( "m4v" , true)||
        extension.contains( "avi" , true)||
        extension.contains( "wmv" , true)||
        extension.contains( "mov" , true)||
        extension.contains( "quicktime" , true)
    ) {
        return MediaType.VIDEO
    }
    else if (
        extension.contains( "pdf" , true)||
        extension.contains( "txt" , true)||
        extension.contains( "ppt" , true)||
        extension.contains( "xls" , true)||
        extension.contains( "doc" , true)
    ) {
        return MediaType.PDF
    }
    else if (
        extension.contains( "zip" , true)
    ) {
        return MediaType.ZIP
    }
    else {
        return MediaType.OTHER
    }
    return null
}

enum class MediaType {
    IMAGE,
    VIDEO,
    PDF,
    ZIP,
    OTHER
}

public interface CompressCallBack {
    fun start()
    fun success(newPath: String)
    fun error()
}