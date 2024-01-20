package com.karimali.baseapp.common.extensions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


fun String.toStringRequestBody(): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
}

fun Array<String>.toStringListRequestBody(): List<RequestBody> {
    val list = ArrayList<RequestBody>()
    this.forEach {
        list.add(RequestBody.create("text/plain".toMediaTypeOrNull(), it))
    }

    return list
}

fun List<String>.toStringListRequestBody(): List<RequestBody> {
    val list = ArrayList<RequestBody>()
    this.forEach {
        list.add(RequestBody.create("text/plain".toMediaTypeOrNull(), it))
    }

    return list
}


fun File.toImagePart(name: String): MultipartBody.Part {
    val imageBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), this)
    return MultipartBody.Part.createFormData(name, this.name, imageBody)
}

fun List<File>.toImagePart(name: String): Array<MultipartBody.Part> {
    val imagesMultiPart = ArrayList<MultipartBody.Part>()
    this.forEach { file ->
        val imageBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        imagesMultiPart.add(MultipartBody.Part.createFormData(name, file.name, imageBody))
    }
    return imagesMultiPart.toTypedArray()
}

fun Array<File>.toImagePart(name: String): Array<MultipartBody.Part> {
    val imagesMultiPart = ArrayList<MultipartBody.Part>()
    this.forEach { file ->
        val imageBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        imagesMultiPart.add(MultipartBody.Part.createFormData(name, file.name, imageBody))
    }
    return imagesMultiPart.toTypedArray()
}

fun Array<File>.toTextPart(name: String): Array<MultipartBody.Part> {
    val imagesMultiPart = ArrayList<MultipartBody.Part>()
    this.forEach { file ->
        val imageBody: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), file)
        imagesMultiPart.add(MultipartBody.Part.createFormData(name, file.name, imageBody))
    }
    return imagesMultiPart.toTypedArray()
}


fun File.toVideoPart(name: String): MultipartBody.Part {
    val videoBody: RequestBody = RequestBody.create("video/*".toMediaTypeOrNull(), this)
    return MultipartBody.Part.createFormData(name, this.name, videoBody)
}


fun Array<File>.toVideoPart(name: String): Array<MultipartBody.Part> {
    val videosMultiPart = ArrayList<MultipartBody.Part>()
    this.forEach { file ->
        val videoBody: RequestBody = RequestBody.create("video/*".toMediaTypeOrNull(), file)
        videosMultiPart.add(MultipartBody.Part.createFormData(name, file.name, videoBody))
    }
    return videosMultiPart.toTypedArray()
}

fun List<File>.toVideoPart(name: String): Array<MultipartBody.Part> {
    val videosMultiPart = ArrayList<MultipartBody.Part>()
    this.forEach { file ->
        val videoBody: RequestBody = RequestBody.create("video/*".toMediaTypeOrNull(), file)
        videosMultiPart.add(MultipartBody.Part.createFormData(name, file.name, videoBody))
    }
    return videosMultiPart.toTypedArray()
}