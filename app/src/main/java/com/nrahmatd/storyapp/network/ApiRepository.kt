package com.sagara.klipz.network

import com.google.gson.JsonObject
import com.sagara.klipz.GlobalApp
import com.sagara.klipz.database.sharedpref.PreferenceManager
import com.sagara.klipz.network.interceptor.NetworkConnectionInterceptor
import com.sagara.klipz.network.retrofit.RetrofitHelper
import com.sagara.klipz.utils.GlobalVariable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ApiRepository {
    private var apiServices: ApiServices =
        RetrofitHelper.invoke(
            Api.BASE_URL,
            NetworkConnectionInterceptor(GlobalApp.getAppContext()),
            false
        )
            .create(ApiServices::class.java)

    private var apiPaymentServices: ApiServices =
        RetrofitHelper.invoke(
            Api.PAYMENT_BASE_URL,
            NetworkConnectionInterceptor(GlobalApp.getAppContext()),
            false
        )
            .create(ApiServices::class.java)

    private val accessToken = PreferenceManager.instance.getString(GlobalVariable.ACCESS_TOKEN, "")

    /** Auth **/
    suspend fun register(body: JsonObject) = apiServices.register(body)
    suspend fun registerGetOtp(body: JsonObject) = apiServices.registerGetOtp(body)
    suspend fun registerResendOtp(body: JsonObject) = apiServices.registerResendOtp(body)
    suspend fun registerVerifyOtp(body: JsonObject) = apiServices.registerVerifyOtp(body)

    suspend fun getOtp(body: JsonObject) = apiServices.getOtp(body)
    suspend fun resendOtp(body: JsonObject) = apiServices.resendOtp(body)
    suspend fun verifyOtp(body: JsonObject) = apiServices.verifyOtp(body)
    suspend fun login(body: JsonObject) = apiServices.login(body)
    suspend fun resetPasswordGetOtp(body: JsonObject) = apiServices.resetPasswordGetOtp(body)
    suspend fun resetPasswordVerifyOtp(body: JsonObject) = apiServices.resetPasswordVerifyOtp(body)
    suspend fun resetPassword(body: JsonObject) = apiServices.resetPassword(body)
    suspend fun changeEmailResendOtp(body: JsonObject) = apiServices.changeEmailResendOtp(body)
    suspend fun changeEmailVerifyOtp(body: JsonObject) =
        apiServices.changeEmailVerifyOtp(accessToken!!, body)

    suspend fun changePhoneResendOtp(body: JsonObject) = apiServices.changePhoneResendOtp(body)
    suspend fun changePhoneVerifyOtp(body: JsonObject) =
        apiServices.changePhoneVerifyOtp(accessToken!!, body)

    /** User **/
    suspend fun profile(userId: String?) = apiServices.profile(accessToken!!, userId)

    suspend fun updateProfile(body: JsonObject, uuid: String) =
        apiServices.updateProfile(accessToken!!, uuid, body)

    suspend fun updateEmail(body: JsonObject, uuid: String) =
        apiServices.updateEmail(accessToken!!, uuid, body)

    suspend fun updatePhone(body: JsonObject, uuid: String) =
        apiServices.updatePhone(accessToken!!, uuid, body)

    suspend fun following(userId: String?, limit: Int, page: Int) =
        apiServices.following(accessToken!!, userId, limit, page)

    suspend fun follower(userId: String?, limit: Int, page: Int) =
        apiServices.follower(accessToken!!, userId, limit, page)

    suspend fun follow(uuid: String) = apiServices.follow(accessToken!!, uuid)
    suspend fun unFollow(uuid: String) = apiServices.unFollow(accessToken!!, uuid)

    suspend fun userImage(image: MultipartBody.Part?) = apiServices.userImage(image, accessToken!!)

    /** Bank **/
    suspend fun addBank(body: JsonObject) = apiServices.addBank(accessToken!!, body)
    suspend fun updateBank(body: JsonObject, uuid: String) =
        apiServices.updateBank(accessToken!!, uuid, body)

    suspend fun deleteBank(uuid: String) = apiServices.deleteBank(accessToken!!, uuid)

    suspend fun getBankAll(limit: Int) =
        apiServices.getBankAll(accessToken!!, limit)

    suspend fun getBankUser(limit: Int, page: Int, s: String?) =
        apiServices.getBankUser(accessToken!!, limit, page, s)

    suspend fun getBankUserHistory(userId: String) =
        apiServices.getBankUserHistory(accessToken!!, userId)

    suspend fun createExperience(body: JsonObject) =
        apiServices.createExperience(body, accessToken!!)

    suspend fun editExperience(uuid: String, body: JsonObject) =
        apiServices.editExperience(uuid, body, accessToken!!)

    suspend fun deleteExperience(uuid: String) = apiServices.deleteExperience(uuid, accessToken!!)

    suspend fun getExperience(limit: Int, page: Int) =
        apiServices.getExperience(limit, page, accessToken!!)

    suspend fun getCategoryList(limit: Int, page: Int) =
        apiServices.getCategoryList(limit, page, accessToken!!)

    suspend fun getFilterCategoryList(limit: Int, page: Int) =
        apiServices.getFilterCategoryList(limit, page, accessToken!!)

    suspend fun getCategoryUserList(userId: String) =
        apiServices.getCategoryUserList(userId, accessToken!!)

    suspend fun addCategoryUser(userId: String, body: JsonObject) =
        apiServices.addCategoryUser(userId, body, accessToken!!)

    suspend fun getPrivateProductList(type: String, limit: Int, page: Int) =
        apiServices.getPrivateProductList(accessToken!!, type, limit, page)

    suspend fun addPrivateTalk(body: JsonObject) =
        apiServices.addPrivateTalk(body, accessToken!!)

    suspend fun addPrivateVideo(body: JsonObject) =
        apiServices.addPrivateVideo(body, accessToken!!)

    suspend fun updatePrivateVideo(body: JsonObject, tpsUuid: String) =
        apiServices.updatePrivateVideo(body, tpsUuid, accessToken!!)

    suspend fun addOpenTalk(
        date: RequestBody,
        eventName: RequestBody,
        isPodcast: RequestBody,
        price: RequestBody,
        time: RequestBody,
        topic: RequestBody,
        participants: RequestBody,
        thumbnail: MultipartBody.Part?,
        attachment: MultipartBody.Part?
    ) =
        apiServices.addOpenTalk(
            date,
            eventName,
            isPodcast,
            price,
            time,
            topic,
            participants,
            thumbnail,
            attachment,
            accessToken!!
        )

    suspend fun updateOpenTalk(
        tpsUuid: String,
        date: RequestBody,
        eventName: RequestBody,
        isPodcast: RequestBody,
        price: RequestBody,
        time: RequestBody,
        topic: RequestBody,
        participants: RequestBody,
        thumbnail: MultipartBody.Part?,
        attachment: MultipartBody.Part?
    ) =
        apiServices.updateOpenTalk(
            tpsUuid,
            date,
            eventName,
            isPodcast,
            price,
            time,
            topic,
            participants,
            thumbnail,
            attachment,
            accessToken!!
        )

    suspend fun deleteVideoTalk(tpsUuid: String) = apiServices.deleteVideoTalk(tpsUuid, accessToken!!)

    suspend fun deleteOpenTalk(tpsUuid: String) = apiServices.deleteOpenTalk(tpsUuid, accessToken!!)

    suspend fun updateTalentVideoClips(body: JsonObject) =
        apiServices.updateTalentVideoClips(body, accessToken!!)

    suspend fun getTalentVideoClipList(name: String?) =
        apiServices.getTalentVideoClipList(accessToken!!, name)

    suspend fun searchUser(name: String) = apiServices.searchUser(accessToken!!, name)

    suspend fun homeSearchUser(name: String) = apiServices.homeSearchUser(accessToken!!, name)

    suspend fun getPromoList() = apiServices.getPromoList(accessToken!!)

    suspend fun createPromo(body: JsonObject) = apiServices.createPromo(body, accessToken!!)

    suspend fun deletePromo(idPromo: String) = apiServices.deletePromo(idPromo, accessToken!!)

    suspend fun getStoreList(userId: String?) = apiServices.getStoreList(userId, accessToken!!)

    suspend fun getStoreDetail(productId: String?, productType: String?) =
        apiServices.getStoreDetail(productId, productType, accessToken!!)

    suspend fun getCodePromo() = apiServices.getCodePromo(accessToken!!)

    suspend fun getCodePromoDetail(codePromo: String) =
        apiServices.getCodePromoDetail(codePromo, accessToken!!)

    suspend fun getScheduleDate(body: JsonObject) = apiServices.getScheduleDate(accessToken!!, body)

    suspend fun getScheduleDateTime(body: JsonObject) =
        apiServices.getScheduleDateTime(accessToken!!, body)

    suspend fun createOrderOpenTalk(body: JsonObject) =
        apiServices.createOrderOpenTalk(accessToken!!, body)

    suspend fun createOrderPrivateTalk(
        userUuid: RequestBody,
        talentUuid: RequestBody,
        date: RequestBody,
        time: RequestBody,
        topic: RequestBody,
        codeVoucher: RequestBody
    ) =
        apiServices.createOrderPrivateTalk(
            userUuid,
            talentUuid,
            date,
            time,
            topic,
            codeVoucher,
            accessToken!!
        )

    suspend fun createOrderVideoTalk(
        userUUID: RequestBody,
        talentUUID: RequestBody,
        date: RequestBody,
        time: RequestBody,
        topic: RequestBody,
        voucher: RequestBody
    ) =
        apiServices.createOrderVIdeoTalk(
            userUUID,
            talentUUID,
            date,
            time,
            topic,
            voucher,
            accessToken!!
        )

    suspend fun createOrderVideoKlipz(body: JsonObject) =
        apiServices.createOrderVideKlipz(accessToken!!, body)

    suspend fun getPlatformFee() = apiServices.getPlatformFee(accessToken!!)

    suspend fun getOrder() = apiServices.getOrder(accessToken!!)

    suspend fun deleteOrder(orderId: String) = apiServices.deleteOrder(orderId, accessToken!!)

    suspend fun getPaymentMethod() = apiPaymentServices.getPaymentMethod()

    suspend fun orderPay(body: JsonObject) = apiServices.orderPay(accessToken!!, body)

    suspend fun getPaymentStatus() = apiServices.getPaymentStatus(accessToken!!)

    suspend fun getPaymentStatusDetail(orderUuid: String) =
        apiServices.getPaymentStatusDetail(orderUuid, accessToken!!)

    suspend fun getRequestKlipzTransactionOnGoing(type: String) =
        apiServices.getRequestKlipzTransactionOnGoing(accessToken!!, type)

    suspend fun getRequestKlipzTransactionOnGoingDetail(orderUuid: String) =
        apiServices.getRequestKlipzTransactionOnGoingDetail(accessToken!!, orderUuid)

    suspend fun getMakeKlipzTransactionOnGoing(type: String) =
        apiServices.getMakeKlipzTransactionOnGoing(accessToken!!, type)

    suspend fun getMakeKlipzTransactionOnGoingDetail(orderUuid: String) =
        apiServices.getMakeKlipzTransactionOnGoingDetail(accessToken!!, orderUuid)

    suspend fun makeKlipz(
        userBankId: RequestBody,
        managerName: RequestBody,
        managerPhone: RequestBody,
        managerEmail: RequestBody,
        address: RequestBody,
        idCardNumber: RequestBody,
        identificationCard: MultipartBody.Part
    ) =
        apiServices.makeKlipz(
            accessToken!!,
            userBankId,
            managerName,
            managerPhone,
            managerEmail,
            address,
            idCardNumber,
            identificationCard
        )

    suspend fun downloadAgreement(lang: String) = apiServices.downloadAgreement(accessToken!!, lang)

    suspend fun uploadAgreement(contractFile: MultipartBody.Part) =
        apiServices.uploadAgreement(accessToken!!, contractFile)

    suspend fun getMakeKlipzStatus() = apiServices.getMakeKlipzStatus(accessToken!!)

    suspend fun getTokenGenerate(
        lang: String,
        userId: String,
        liveEventId: String
    ) = apiServices.getTokenGenerate(userId, liveEventId, accessToken!!, lang)

    suspend fun getEventParticipant(
        lang: String,
        liveEventId: String
    ) = apiServices.getEventParticipant(accessToken!!, lang, liveEventId)

    suspend fun updateParticipantRole(
        userId: String,
        eventRole: String
    ) = apiServices.updateParticipantRole(accessToken!!, userId, eventRole)

    suspend fun searchGlobalUser(
        lang: String,
        username: String
    ) = apiServices.searchGlobalUser(username, accessToken!!, lang)

    suspend fun addEventParticipant(
        userId: String,
        liveEventId: String,
        eventRole: String
    ) = apiServices.addEventParticipant(accessToken!!, userId, liveEventId, eventRole)

    suspend fun cancelEvent(
        lang: String,
        liveEventId: String,
        body: JsonObject
    ) = apiServices.cancelEvent(accessToken!!, lang, liveEventId, body)

    suspend fun addFavorite(
        body: JsonObject
    ) = apiServices.addFavorite(accessToken!!, body)

    suspend fun getMyFavorite(type: String?) = apiServices.getMyFavoriteList(accessToken!!, type)

    suspend fun deleteFavorite(
        talentProductId: String,
        talentProductScheduleId: String?
    ) = apiServices.deleteFavorite(accessToken!!, talentProductId, talentProductScheduleId)

    suspend fun getHomePromo(type: String?, limit: Int, page: Int) =
        apiServices.getHomePromo(accessToken!!, type, limit, page)

    suspend fun getHomeUpcomingEvent(
        startDate: String?,
        endDate: String?,
        category: String?,
        type: String?,
        s: String?
    ) =
        apiServices.getHomeUpcomingEvent(accessToken!!, startDate, endDate, category, type, s)

    suspend fun getTrendingEvents(
        lang: String?,
        page: Int,
        limit: Int
    ) = apiServices.getTrendingEvents(accessToken!!, lang, page, limit)

    suspend fun getTrendingPrivateEvents(
        lang: String?,
        page: Int,
        limit: Int
    ) = apiServices.getTrendingPrivateEvents(accessToken!!, lang, page, limit)

    suspend fun getPersonalTalkTalents(
        lang: String? = "en",
        date: String?,
        types: String?,
        category: String?,
        search: String?
    ) = apiServices.getPersonalTalkTalents(accessToken!!, lang, date, types, category, search)

    suspend fun getPersonalTalkEvents(
        talentUuid: String,
        lang: String?
    ) = apiServices.getPersonalTalkEvents(accessToken!!, talentUuid, lang)
}
