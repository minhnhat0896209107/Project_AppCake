package com.nguyenvanminhnhat.projectcakeapp.itf

import com.nguyenvanminhnhat.projectcakeapp.utils.Constant.Companion.CONTENT_TYPE
import com.nguyenvanminhnhat.projectcakeapp.utils.Constant.Companion.SERVER_KEY
import com.nguyenvanminhnhat.projectcakeapp.pojo.model.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {

    @Headers("Authorization: key=$SERVER_KEY","Content-type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}