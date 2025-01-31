package com.mordentech.chatapp.data.network

import com.mordentech.chatapp.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @Multipart
    @POST("profile/change-avatar")
    suspend fun changeAvatar(
        @Part image: MultipartBody.Part,
        @Part("data") data: String,
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("profile/change-password")
    suspend fun changePassword(
        @Field("token") token: String,
        @Field("old") oldPassword: String,
        @Field("new") newPassword: String
    ) : Response<DefaultResponse>

    @FormUrlEncoded
    @POST("user/login/")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("user/register/")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Response<AuthResponse>

    @GET("home/")
    suspend fun home() : Response<DefaultResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            authTokenInterceptor: AuthTokenInterceptor
        ) : MyApi{
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(authTokenInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.126.159/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}

