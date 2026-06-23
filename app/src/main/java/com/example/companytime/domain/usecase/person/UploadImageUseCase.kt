package com.example.companytime.domain.usecase.person

import android.content.Context
import android.graphics.BitmapFactory
import com.example.companytime.data.TokenStorage
import com.example.companytime.domain.model.Person
import com.example.companytime.domain.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.net.Uri
import android.webkit.MimeTypeMap
import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.source
import java.io.ByteArrayOutputStream
import java.io.IOException
import androidx.core.graphics.scale

class UploadImageUseCase(
    private val personRepository: PersonRepository,
    private val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(context: Context, uri: Uri): Result<Person> = withContext(Dispatchers.IO) {
        runCatching {
            val id = tokenStorage.getUserId()

            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            val original = BitmapFactory.decodeStream(inputStream)

            val max = 1080
            val w = original.width
            val h = original.height

            val (nW, nH) = if (w > h) {
                max to (h * max / w)
            } else {
                (w * max / h) to max
            }

            val scaledBitmap = original.scale(nW, nH)
            val outputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            val bytes = outputStream.toByteArray()

            original.recycle()
            scaledBitmap.recycle()


            val mime = contentResolver.getType(uri) ?: "application/octet-stream"
            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime)
            val fileName = if(extension != null) {
                "file_${System.currentTimeMillis()}." + extension
            } else {
                ""
            }

            val body = bytes.toRequestBody(mime.toMediaTypeOrNull(), 0, bytes.size)
            val multipart = MultipartBody.Part.createFormData("multipartFile", fileName, body)
            personRepository.uploadAvatar(id, multipart)
        }
    }
}