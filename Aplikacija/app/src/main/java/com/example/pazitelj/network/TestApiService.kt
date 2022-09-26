package com.example.pazitelj.network

import com.example.pazitelj.models.*
import com.example.pazitelj.models.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "http://kristijanpapic3-001-site1.atempurl.com/api/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logging = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    }
}
private val client = OkHttpClient.Builder().addInterceptor(logging).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface TestApiService{
    @GET("User")
    suspend fun getUsers(): List<User>

    @GET("User/{Id}")
    suspend fun getUser(@Path("Id") Id: String): User

    @PUT("User")
    suspend fun updateUser(@Body user: User)

    @DELETE("User/{Id}")
    suspend fun deleteUser(@Path("Id")Id: String)

    @POST("Pet")
    suspend fun addPet(@Body pet: Pet)

    @DELETE("Pet/{Id}")
    suspend fun deletePet(@Path("Id")Id: String)

    @POST("Ad")
    suspend fun PostAd(@Body ad: AdInput)

    @GET("Ad")
    suspend fun GetAds(@Query("Type") Type: Int,@Query("CurrentUser") CurrentUser: String,@Query("UserId") UserId: String,@Query("ShowAppliedCount") ShowAppliedCount: Boolean,@Query("ShowStatus") ShowStatus: Boolean,@Query("PetType") PetType: String,@Query("JobType") JobType: String,@Query("Rating") Rating: Int): List<Ad>

    @GET("Ad/{Id}")
    suspend fun GetAd(@Path("Id") Id: String,@Query("userId") userId: String): Ad

    @PUT("Ad")
    suspend fun PutAd(@Body ad: Ad)

    @DELETE("Ad/{Id}")
    suspend fun DeleteAd(@Path("Id") Id: String)

    @POST("AppliedUser")
    suspend fun PostAppliedUser(@Body appliedUser: AppliedUserInput)

    @DELETE("AppliedUser")
    suspend fun DeleteAppliedUser (@Query("userId") userId: String,@Query("adId") adId: String)

    @GET("AppliedUser")
    suspend fun GetAppliedUsers(@Query("AdId") adId: String,@Query("Type") type : Int): List<AppliedUser>

    @PUT("AppliedUser")
    suspend fun PutAppliedUser (@Query("userId") userId: String,@Query("adId") adId: String,@Query("status") status: String)

    @GET("Login")
    suspend fun login(@Query("idToken") idToken: String): LoginResponse

    @POST("CarerReview")
    suspend fun addCarerReview(@Body carerRating: CarerRating)

    @POST("OwnerReview")
    suspend fun addOwnerReview(@Body ownerRating: OwnerRating)


}

object TestApi {
    val retrofitService : TestApiService by lazy {
        retrofit.create(TestApiService::class.java)
    }
}


