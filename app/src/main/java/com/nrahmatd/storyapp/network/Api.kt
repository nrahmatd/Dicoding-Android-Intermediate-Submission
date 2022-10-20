package com.sagara.klipz.network

object Api {
    //    const val BASE_URL = "http://139.162.57.140:21061/api/v1/"
    const val BASE_URL = "http://156.67.218.2:21061/api/v1/"

    //    const val PAYMENT_BASE_URL = "http://139.162.57.140:5003/"
    const val PAYMENT_BASE_URL = "http://156.67.218.2:5003/"

    /** Header */
    /** content types */
    const val CONTENT_TYPE = "Content-Type: application/vnd.api+json"
    const val CONTENT_TYPE_UTF = "Content-Type: application/json; charset=utf-8"
    const val CONTENT_TYPE_IMAGE = "Content-Type: image/jpeg"
    const val CONTENT_TYPE_MULTIPART = "Content-Type: multipart/form-data"
    const val ACCEPT = "Accept: application/json"

    /** Auth */
    const val REGISTER = BASE_URL + "auth/register"
    const val REGISTER_GET_OTP = BASE_URL + "auth/register/get-otp"
    const val REGISTER_RESEND_OTP = BASE_URL + "auth/register/resend-otp"
    const val REGISTER_VERIFY_OTP = BASE_URL + "auth/register/verify-otp"
    const val GET_OTP = BASE_URL + "otp/get-otp"
    const val RESEND_OTP = BASE_URL + "otp/resend-otp"
    const val VERIFY_OTP = BASE_URL + "otp/verify-otp"
    const val LOGIN = BASE_URL + "auth/login"
    const val RESET_PASSWORD = BASE_URL + "reset-password"
    const val RESET_PASSWORD_GET_OTP = BASE_URL + "reset-password/get-otp"
    const val RESET_PASSWORD_VERIFY_OTP = BASE_URL + "reset-password/verify-otp"
    const val CHANGE_EMAIL_RESEND_OTP = BASE_URL + "otp/email/resend"
    const val CHANGE_EMAIL_VERIFY_OTP = BASE_URL + "otp/email/verify"
    const val CHANGE_PHONE_RESEND_OTP = BASE_URL + "otp/phone/resend"
    const val CHANGE_PHONE_VERIFY_OTP = BASE_URL + "otp/phone/verify"

    /** User */
    const val PROFILE = BASE_URL + "user/profile"
    const val FOLLOWING = BASE_URL + "user/following"
    const val FOLLOWER = BASE_URL + "user/follower"
    const val FOLLOW = BASE_URL + "user/follow"
    const val UNFOLLOW = BASE_URL + "user/unfollow"
    const val USER = BASE_URL + "user"
    const val EMAIL = BASE_URL + "user/email"
    const val PHONE = BASE_URL + "user/phone"
    const val EXPERIENCE = BASE_URL + "experience"
    const val CATEGORY = BASE_URL + "category"
    const val CATEGORY_USER = BASE_URL + "category/user"
    const val BANK = BASE_URL + "bank"
    const val BANK_USER = BASE_URL + "bank/user"
    const val BANK_USER_HISTORY = BASE_URL + "bank/user/history"
    const val USER_IMAGE = BASE_URL + "user/image"

    /** Product */
    const val PRIVATE_PRODUCT = BASE_URL + "private-product"
    const val OPEN_PRODUCT = BASE_URL + "open-product"
    const val TALENT_VIDEO_CLIPS = BASE_URL + "product/klipz"
    const val SCHEDULE_PRIVATE_TALK = BASE_URL + "product/schedule/private-talk"
    const val SCHEDULE_PRIVATE_VIDEO = BASE_URL + "product/schedule/private-video"
    const val SCHEDULE_OPEN_TALK = BASE_URL + "product/schedule/open-talk"
    const val SCHEDULE_DATE = BASE_URL + "product/schedule/date"
    const val SCHEDULE_DATE_TIME = BASE_URL + "product/schedule/detail-time"
    const val STORE = BASE_URL + "product/store"
    const val PLATFORM_FEE = BASE_URL + "config/platform-fee"
    const val FAVORITE = BASE_URL + "product/favorite"
    const val MYFAVORITE = BASE_URL + "product/my-favorite"
    const val UNFAVORITE = BASE_URL + "product/unfavorite"
    const val STORE_DETAIL = BASE_URL + "product/store/detail"

    /** Promo **/
    const val PROMO = BASE_URL + "promo"
    const val CODE_PROMO = BASE_URL + "promo/generate/code-voucher"
    const val CODE_PROMO_DETAIL = BASE_URL + "promo/check"

    /** Customer Oder **/
    const val ORDER_OPEN_TALK = "order/schedule/open-talk"
    const val ORDER_VIDEO_TALK = "order/schedule/private-video"
    const val ORDER_PRIVATE_TALK = "order/schedule/private-talk"
    const val ORDER_VIDEO_KLIPZ = "order/klipz"
    const val ORDER = "order"
    const val ORDER_PAY = "order/pay"

    /** Payment */
    const val GET_PAYMENT_METHOD = "payment/methods"

    /** Transaction **/
    const val GET_PAYMENT_STATUS = "transaction/req-klipz/wait-payment"
    const val TRANSACTION_MAKE_KLIPZ = "transaction/make-klipz"
    const val TRANSACTION_ONGOING_REQUEST_KLIPZ = "transaction/req-klipz/ongoing"
    const val TRANSACTION_ONGOING_MAKE_KLIPZ = "transaction/make-klipz/ongoing"
    const val TRANSACTION_MAKE_KLIPZ_AGREEMENT_DOWNLOAD =
        "transaction/make-klipz/contract-agreement/download"
    const val TRANSACTION_MAKE_KLIPZ_AGREEMENT_UPLOAD =
        "transaction/make-klipz/contract-agreement/upload"
    const val TRANSACTION_MAKE_KLIPZ_STATUS = "transaction/make-klipz/status"

    /** Event **/
    const val GET_TOKEN_GENERATE = "event/token"
    const val GET_EVENT_PARTICIPANT = "event/participant/list"
    const val UPDATE_PARTICIPANT_ROLE = "event/participant/update-role"
    const val SEARCH_GLOBAL_USER = "event/participant/search"
    const val INVITE_EVENT_PARTICIPANT = "event/participant/invite"

    /** HOME **/
    const val HOME_SEARCH_USER = "home/user/search"
    const val HOME_PROMO = "home/promo"
    const val HOME_UPCOMING_EVENT = "home/upcoming-event"
    const val HOME_TRENDING_PRIVATE_EVENT = "home/trending-private-event"

    const val CANCEL_EVENT = "event/cancel"

    const val TRENDING_EVENT = "event/trending"
    /** PERSONAL TALKS **/
    const val PERSONAL_TALK_TALENTS = "personal/trending-personal-event"
    const val PERSONAL_TALK_EVENTS = "personal/trending-personal-event"
}
