package com.sagara.klipz.network

import com.google.gson.JsonObject
import com.sagara.klipz.audio.model.TokenModel
import com.sagara.klipz.auth.model.BaseModel
import com.sagara.klipz.auth.model.ErrorModel
import com.sagara.klipz.auth.model.User
import com.sagara.klipz.home.model.CategoryModel
import com.sagara.klipz.home.model.HomePromoModel
import com.sagara.klipz.home.model.OpenTalkModel
import com.sagara.klipz.order.model.Invoices
import com.sagara.klipz.order.model.Order
import com.sagara.klipz.order.model.PaymentMethod
import com.sagara.klipz.personalroom.model.PersonalModel
import com.sagara.klipz.profile.model.AddBankModel
import com.sagara.klipz.profile.model.BankListModel
import com.sagara.klipz.profile.model.BankUserHistoryListModel
import com.sagara.klipz.profile.model.BankUserListModel
import com.sagara.klipz.profile.model.CategoryListModel
import com.sagara.klipz.profile.model.CategoryUserListModel
import com.sagara.klipz.profile.model.ExperienceModel
import com.sagara.klipz.profile.model.ProfileModel
import com.sagara.klipz.profile.model.UserFollowListModel
import com.sagara.klipz.profile.model.UserImage
import com.sagara.klipz.schedule.model.CodeVoucherDetailModel
import com.sagara.klipz.schedule.model.CodeVoucherModel
import com.sagara.klipz.schedule.model.PlatformFee
import com.sagara.klipz.schedule.model.PrivateProduct
import com.sagara.klipz.schedule.model.ProductModel
import com.sagara.klipz.schedule.model.ProductTimeModel
import com.sagara.klipz.schedule.model.PromoListModel
import com.sagara.klipz.schedule.model.StoreDetailModel
import com.sagara.klipz.schedule.model.StoreListModel
import com.sagara.klipz.schedule.model.VideoClips
import com.sagara.klipz.transaction.model.DownloadAgreementModel
import com.sagara.klipz.transaction.model.MakeKlipzStatusModel
import com.sagara.klipz.transaction.model.ParticipantModel
import com.sagara.klipz.transaction.model.PaymentStatusDetail
import com.sagara.klipz.transaction.model.TransactionDetailModel
import com.sagara.klipz.transaction.model.TransactionOnGoingDetailModel
import com.sagara.klipz.transaction.model.WaitPaymentModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    /** Auth **/

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.REGISTER)
    suspend fun register(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.REGISTER_GET_OTP)
    suspend fun registerGetOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.REGISTER_RESEND_OTP)
    suspend fun registerResendOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.REGISTER_VERIFY_OTP)
    suspend fun registerVerifyOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.GET_OTP)
    suspend fun getOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.RESEND_OTP)
    suspend fun resendOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.VERIFY_OTP)
    suspend fun verifyOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.LOGIN)
    suspend fun login(@Body body: JsonObject): Response<User>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.RESET_PASSWORD_GET_OTP)
    suspend fun resetPasswordGetOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.RESET_PASSWORD_VERIFY_OTP)
    suspend fun resetPasswordVerifyOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.RESET_PASSWORD)
    suspend fun resetPassword(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.CHANGE_EMAIL_RESEND_OTP)
    suspend fun changeEmailResendOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.CHANGE_EMAIL_VERIFY_OTP)
    suspend fun changeEmailVerifyOtp(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.CHANGE_PHONE_RESEND_OTP)
    suspend fun changePhoneResendOtp(@Body body: JsonObject): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.CHANGE_PHONE_VERIFY_OTP)
    suspend fun changePhoneVerifyOtp(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<ErrorModel>

    /** User **/

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.PROFILE)
    suspend fun profile(
        @Header("access_token") bearer: String,
        @Query("uuid") uuid: String?
    ): Response<ProfileModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.USER + "/{uuid}")
    suspend fun updateProfile(
        @Header("access_token") bearer: String,
        @Path("uuid") id: String,
        @Body body: JsonObject
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.EMAIL + "/{uuid}")
    suspend fun updateEmail(
        @Header("access_token") bearer: String,
        @Path("uuid") id: String,
        @Body body: JsonObject
    ): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.PHONE + "/{uuid}")
    suspend fun updatePhone(
        @Header("access_token") bearer: String,
        @Path("uuid") id: String,
        @Body body: JsonObject
    ): Response<ErrorModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.FOLLOWING)
    suspend fun following(
        @Header("access_token") bearer: String,
        @Query("uuid") uuid: String?,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<UserFollowListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.FOLLOWER)
    suspend fun follower(
        @Header("access_token") bearer: String,
        @Query("uuid") uuid: String?,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<UserFollowListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.FOLLOW + "/{uuid}")
    suspend fun follow(
        @Header("access_token") bearer: String,
        @Path("uuid") id: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.UNFOLLOW + "/{uuid}")
    suspend fun unFollow(
        @Header("access_token") bearer: String,
        @Path("uuid") id: String
    ): Response<BaseModel>

    @Multipart
    @POST(Api.USER_IMAGE)
    suspend fun userImage(
        @Part image: MultipartBody.Part?,
        @Header("access_token") bearer: String
    ): Response<UserImage>

    /** Bank **/
    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.BANK_USER)
    suspend fun addBank(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<AddBankModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.BANK_USER + "/{uuid}")
    suspend fun updateBank(
        @Header("access_token") bearer: String,
        @Path("uuid") id: String,
        @Body body: JsonObject
    ): Response<AddBankModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @DELETE(Api.BANK_USER + "/{uuid}")
    suspend fun deleteBank(
        @Header("access_token") bearer: String,
        @Path("uuid") id: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.BANK)
    suspend fun getBankAll(
        @Header("access_token") bearer: String,
        @Query("limit") limit: Int
    ): Response<BankListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.BANK_USER)
    suspend fun getBankUser(
        @Header("access_token") bearer: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("s") s: String?
    ): Response<BankUserListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.BANK_USER_HISTORY)
    suspend fun getBankUserHistory(
        @Header("access_token") bearer: String,
        @Query("user_uuid") userId: String
    ): Response<BankUserHistoryListModel>

    @POST(Api.EXPERIENCE)
    suspend fun createExperience(
        @Body body: JsonObject,
        @Header("access_token") bearer: String
    ): Response<ExperienceModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.EXPERIENCE)
    suspend fun getExperience(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("access_token") bearer: String
    ): Response<ExperienceModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.EXPERIENCE + "/{uuid}")
    suspend fun editExperience(
        @Path("uuid") uuid: String,
        @Body body: JsonObject,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @DELETE(Api.EXPERIENCE + "/{uuid}")
    suspend fun deleteExperience(
        @Path("uuid") uuid: String,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.CATEGORY)
    suspend fun getCategoryList(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("access_token") bearer: String
    ): Response<CategoryListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.CATEGORY)
    suspend fun getFilterCategoryList(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Header("access_token") bearer: String
    ): Response<CategoryModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.CATEGORY_USER + "/{user_uuid}")
    suspend fun getCategoryUserList(
        @Path("user_uuid") user_uuid: String,
        @Header("access_token") bearer: String
    ): Response<CategoryUserListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.CATEGORY_USER + "/{user_uuid}")
    suspend fun addCategoryUser(
        @Path("user_uuid") user_uuid: String,
        @Body body: JsonObject,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.PRIVATE_PRODUCT)
    suspend fun getPrivateProductList(
        @Header("access_token") bearer: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<PrivateProduct>

    @POST(Api.SCHEDULE_PRIVATE_TALK)
    suspend fun addPrivateTalk(
        @Body body: JsonObject,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @POST(Api.SCHEDULE_PRIVATE_VIDEO)
    suspend fun addPrivateVideo(
        @Body body: JsonObject,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.SCHEDULE_PRIVATE_VIDEO + "/{tps_uuid}")
    suspend fun updatePrivateVideo(
        @Body body: JsonObject,
        @Path("tps_uuid") tps_uuid: String,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Multipart
    @POST(Api.SCHEDULE_OPEN_TALK)
    suspend fun addOpenTalk(
        @Part("date") date: RequestBody,
        @Part("event_name") eventName: RequestBody,
        @Part("is_podcast") isPodcast: RequestBody,
        @Part("price") price: RequestBody,
        @Part("time") time: RequestBody,
        @Part("topic") topic: RequestBody,
        @Part("event_participant_uuid") event_participant_uuid: RequestBody,
        @Part thumbnail: MultipartBody.Part?,
        @Part attachment: MultipartBody.Part?,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Multipart
    @PUT(Api.SCHEDULE_OPEN_TALK + "/{tps_uuid}")
    suspend fun updateOpenTalk(
        @Path("tps_uuid") tps_uuid: String,
        @Part("date") date: RequestBody,
        @Part("event_name") eventName: RequestBody,
        @Part("is_podcast") isPodcast: RequestBody,
        @Part("price") price: RequestBody,
        @Part("time") time: RequestBody,
        @Part("topic") topic: RequestBody,
        @Part("event_participant_uuid") event_participant_uuid: RequestBody,
        @Part thumbnail: MultipartBody.Part?,
        @Part attachment: MultipartBody.Part?,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @DELETE(Api.SCHEDULE_OPEN_TALK + "/{tps_uuid}")
    suspend fun deleteOpenTalk(
        @Path("tps_uuid") tps_uuid: String,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @DELETE(Api.SCHEDULE_PRIVATE_VIDEO + "/{tps_uuid}")
    suspend fun deleteVideoTalk(
        @Path("tps_uuid") tps_uuid: String,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.TALENT_VIDEO_CLIPS)
    suspend fun updateTalentVideoClips(
        @Body body: JsonObject,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.TALENT_VIDEO_CLIPS)
    suspend fun getTalentVideoClipList(
        @Header("access_token") bearer: String,
        @Query("name") name: String?
    ): Response<VideoClips>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.USER + "/{name}")
    suspend fun searchUser(
        @Header("access_token") bearer: String,
        @Path("name") id: String
    ): Response<UserFollowListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.HOME_SEARCH_USER + "/{name}")
    suspend fun homeSearchUser(
        @Header("access_token") bearer: String,
        @Path("name") id: String
    ): Response<UserFollowListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.PROMO)
    suspend fun getPromoList(
        @Header("access_token") bearer: String
    ): Response<PromoListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.PROMO)
    suspend fun createPromo(
        @Body body: JsonObject,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @DELETE(Api.PROMO + "/{uuid}")
    suspend fun deletePromo(
        @Path("uuid") id: String,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.STORE)
    suspend fun getStoreList(
        @Query("uuid") userId: String?,
        @Header("access_token") bearer: String
    ): Response<StoreListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.STORE_DETAIL)
    suspend fun getStoreDetail(
        @Query("uuid") productId: String?,
        @Query("productType") productType: String?,
        @Header("access_token") bearer: String
    ): Response<StoreDetailModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.CODE_PROMO)
    suspend fun getCodePromo(
        @Header("access_token") bearer: String
    ): Response<CodeVoucherModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.CODE_PROMO_DETAIL + "/{code_voucher}")
    suspend fun getCodePromoDetail(
        @Path("code_voucher") codePromo: String,
        @Header("access_token") bearer: String
    ): Response<CodeVoucherDetailModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.SCHEDULE_DATE)
    suspend fun getScheduleDate(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<ProductModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.SCHEDULE_DATE_TIME)
    suspend fun getScheduleDateTime(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<ProductTimeModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.ORDER_OPEN_TALK)
    suspend fun createOrderOpenTalk(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<BaseModel>

    @Multipart
    @POST(Api.ORDER_VIDEO_TALK)
    suspend fun createOrderVIdeoTalk(
        @Part("user_uuid") buyerUuid: RequestBody,
        @Part("talent_uuid") talentUuid: RequestBody,
        @Part("date") date: RequestBody,
        @Part("time") time: RequestBody,
        @Part("topic") topic: RequestBody,
        @Part("code_voucher") codeVoucher: RequestBody,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Multipart
    @POST(Api.ORDER_PRIVATE_TALK)
    suspend fun createOrderPrivateTalk(
        @Part("user_uuid") buyerUuid: RequestBody,
        @Part("talent_uuid") talentUuid: RequestBody,
        @Part("date") date: RequestBody,
        @Part("time") time: RequestBody,
        @Part("topic") topic: RequestBody,
        @Part("code_voucher") codeVoucher: RequestBody,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.ORDER_VIDEO_KLIPZ)
    suspend fun createOrderVideKlipz(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.PLATFORM_FEE)
    suspend fun getPlatformFee(
        @Header("access_token") bearer: String
    ): Response<PlatformFee>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.ORDER)
    suspend fun getOrder(
        @Header("access_token") bearer: String
    ): Response<Order>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @DELETE(Api.ORDER + "/{uuid}")
    suspend fun deleteOrder(
        @Path("uuid") id: String,
        @Header("access_token") bearer: String
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.GET_PAYMENT_METHOD)
    suspend fun getPaymentMethod(): Response<PaymentMethod>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.ORDER_PAY)
    suspend fun orderPay(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<Invoices>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.GET_PAYMENT_STATUS)
    suspend fun getPaymentStatus(
        @Header("access_token") bearer: String
    ): Response<WaitPaymentModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.GET_PAYMENT_STATUS + "/{order_uuid}")
    suspend fun getPaymentStatusDetail(
        @Path("order_uuid") orderUuid: String?,
        @Header("access_token") bearer: String
    ): Response<PaymentStatusDetail>

    @Multipart
    @POST(Api.TRANSACTION_MAKE_KLIPZ)
    suspend fun makeKlipz(
        @Header("access_token") bearer: String,
        @Part("user_bank_uuid") userBankId: RequestBody,
        @Part("manager_name") managerName: RequestBody,
        @Part("manager_phone") managerPhone: RequestBody,
        @Part("manager_email") managerEmail: RequestBody,
        @Part("address") address: RequestBody,
        @Part("idcard_number") idCardNumber: RequestBody,
        @Part identificationCard: MultipartBody.Part?
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.TRANSACTION_MAKE_KLIPZ_AGREEMENT_DOWNLOAD)
    suspend fun downloadAgreement(
        @Header("access_token") bearer: String,
        @Query("lang") lang: String?
    ): Response<DownloadAgreementModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.TRANSACTION_ONGOING_REQUEST_KLIPZ)
    suspend fun getRequestKlipzTransactionOnGoing(
        @Header("access_token") bearer: String,
        @Query("type") type: String
    ): Response<TransactionDetailModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.TRANSACTION_ONGOING_REQUEST_KLIPZ + "/{order_uuid}")
    suspend fun getRequestKlipzTransactionOnGoingDetail(
        @Header("access_token") bearer: String,
        @Path("order_uuid") orderUuid: String?
    ): Response<TransactionOnGoingDetailModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.TRANSACTION_ONGOING_MAKE_KLIPZ)
    suspend fun getMakeKlipzTransactionOnGoing(
        @Header("access_token") bearer: String,
        @Query("type") type: String
    ): Response<TransactionDetailModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.TRANSACTION_ONGOING_MAKE_KLIPZ + "/{order_uuid}")
    suspend fun getMakeKlipzTransactionOnGoingDetail(
        @Header("access_token") bearer: String,
        @Path("order_uuid") orderUuid: String?
    ): Response<TransactionOnGoingDetailModel>

    @Multipart
    @POST(Api.TRANSACTION_MAKE_KLIPZ_AGREEMENT_UPLOAD)
    suspend fun uploadAgreement(
        @Header("access_token") bearer: String,
        @Part contractFile: MultipartBody.Part?
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.TRANSACTION_MAKE_KLIPZ_STATUS)
    suspend fun getMakeKlipzStatus(
        @Header("access_token") bearer: String
    ): Response<MakeKlipzStatusModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.GET_TOKEN_GENERATE + "/{userUUID}/{liveEventUUID}")
    suspend fun getTokenGenerate(
        @Path("userUUID") userId: String,
        @Path("liveEventUUID") liveEventId: String,
        @Header("access_token") bearer: String,
        @Query("lang") lang: String
    ): Response<TokenModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.GET_EVENT_PARTICIPANT)
    suspend fun getEventParticipant(
        @Header("access_token") bearer: String,
        @Query("lang") lang: String,
        @Query("live_event_uuid") liveEventId: String
    ): Response<ParticipantModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.UPDATE_PARTICIPANT_ROLE)
    suspend fun updateParticipantRole(
        @Header("access_token") bearer: String,
        @Query("user_uuid") userUUID: String,
        @Query("event_role") eventRole: String
    ): Response<ParticipantModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.SEARCH_GLOBAL_USER + "/{username}")
    suspend fun searchGlobalUser(
        @Path("username") username: String,
        @Header("access_token") bearer: String,
        @Query("lang") lang: String
    ): Response<ParticipantModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @POST(Api.INVITE_EVENT_PARTICIPANT)
    suspend fun addEventParticipant(
        @Header("access_token") bearer: String,
        @Query("user_uuid") userUUID: String,
        @Query("live_event_uuid") liveEventId: String,
        @Query("event_role") eventRole: String
    ): Response<ParticipantModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @PUT(Api.CANCEL_EVENT)
    suspend fun cancelEvent(
        @Header("access_token") bearer: String,
        @Query("lang") lang: String,
        @Query("live_event_uuid") liveEventId: String,
        @Body body: JsonObject
    ): Response<ParticipantModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)

    @POST(Api.FAVORITE)
    suspend fun addFavorite(
        @Header("access_token") bearer: String,
        @Body body: JsonObject
    ): Response<BaseModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.MYFAVORITE)
    suspend fun getMyFavoriteList(
        @Header("access_token") bearer: String,
        @Query("type") type: String?
    ): Response<StoreListModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @DELETE(Api.UNFAVORITE)
    suspend fun deleteFavorite(
        @Header("access_token") bearer: String,
        @Query("talent_product_uuid") talentProductId: String,
        @Query("talent_product_schedule_uuid") talentProductScheduleId: String?
    ): Response<BaseModel>

    @GET(Api.HOME_PROMO)
    suspend fun getHomePromo(
        @Header("access_token") bearer: String,
        @Query("type") type: String?,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<HomePromoModel>

    @GET(Api.HOME_UPCOMING_EVENT)
    suspend fun getHomeUpcomingEvent(
        @Header("access_token") bearer: String,
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?,
        @Query("category") category: String?,
        @Query("type") type: String?,
        @Query("s") s: String?
    ): Response<OpenTalkModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.TRENDING_EVENT)
    suspend fun getTrendingEvents(
        @Header("access_token") bearer: String,
        @Query("lang") lang: String?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<OpenTalkModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.HOME_TRENDING_PRIVATE_EVENT)
    suspend fun getTrendingPrivateEvents(
        @Header("access_token") bearer: String,
        @Query("lang") lang: String?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<PersonalModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.PERSONAL_TALK_TALENTS)
    suspend fun getPersonalTalkTalents(
        @Header("access_token") bearer: String,
        @Query("lang") lang: String?,
        @Query("date") date: String?,
        @Query("types") types: String?,
        @Query("category") category: String?,
        @Query("search") search: String?
    ): Response<PersonalModel>

    @Headers(Api.ACCEPT, Api.CONTENT_TYPE)
    @GET(Api.PERSONAL_TALK_EVENTS + "/{talent_uuid}")
    suspend fun getPersonalTalkEvents(
        @Header("access_token") bearer: String,
        @Path("talent_uuid") talentUuid: String,
        @Query("lang") lang: String?
    ): Response<PersonalModel>
}
