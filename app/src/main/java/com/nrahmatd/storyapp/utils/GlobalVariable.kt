package com.nrahmatd.storyapp.utils

object GlobalVariable {
    const val EN_LANGUAGE = "en"
    const val ID_LANGUAGE = "in"

    /** Store Type **/
    const val AUDIO_TYPE = "audio"
    const val VIDEO_TYPE = "video"

    /**Shared Pref**/
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    const val USER_ID = "user_id"
    const val USER_NAME = "user_name"
    const val USER_EMAIL = "user_email"
    const val ROLE_NAME = "role_name"
    const val NAME = "name"

    /** Round ImageView **/
    const val ROUND_BOTTOM = 1
    const val ROUND_TOP = 2
    const val ROUND_TOP_BOTTOM = 3

    /** User Role**/
    const val CUSTOMER = "customer"
    const val TALENT = "talent"

    /** User Event Role **/
    const val GUEST = "guest"
    const val HOST = "host"
    const val CO_HOST = "co-host"
    const val MODERATOR = "moderator"
    const val SPEAKER = "speaker"

    /** RTM Message Tag **/
    const val RAISE_HAND = "raise_hand"
    const val OPEN_RAISE_HAND = "open_discuss"
    const val CLOSE_RAISE_HAND = "close_discuss"
    const val JOIN = "join"
    const val LEFT = "left"
    const val CHANGE_ROLE_SUCCESSFULLY = "change_role_success"
    const val CHANGE_ROLE_TO = "change_role_to"

    /** Notify Type **/
    const val NOTIFY_ERROR = "Notify Error:"
    const val UPDATE_PROFILE = "update_profile"
    const val UPDATE_EXPERIENCE = "update_experience"
    const val UPDATE_FAVORITE_FROM_PRODUCT = "update_favorite_from_product"
    const val UPDATE_FAVORITE_FROM_FAVORITE = "update_favorite_from_favorite"

    /** load from **/
    const val LOAD_FROM = "load_from"
    const val LOAD_CHANGE_EMAIL = "load_change_email"
    const val LOAD_CHANGE_PHONE_NUMBER = "load_change_phone_number"
    const val LOAD_ADD_ORDER = "load_add_order"
    const val LOAD_ADD_OPEN_TALK = "load_add_open_talk"

    /** Product Setting Menu **/
    const val SETUP_OPEN_TALK = "setup_open_talk"
    const val SETUP_PRIVATE_TALK = "setup_private_talk"
    const val SETUP_VIDEO_CLIP = "setup_video_clip"
    const val SETUP_PROMO = "setup_promo"
    const val SETUP_ACCOUNT_ASSISTANT = "setup_account_assistant"
    const val SETUP_VIDEO_PRIVATE = "setup_video_private"

    /** Platform Fee Type */
    const val PERSONAL_TALK = "personal_talk"
    const val PERSONAL_VIDEO = "personal_video"
    const val OPEN = "open"
    const val KLIPZ = "klipz"

    /** Otp Type **/
    const val PHONE_OTP_RESET_PASSWORD = "phone_otp_reset_password"
    const val EMAIL_OTP_RESET_PASSWORD = "email_otp_reset_password"
    const val PHONE_OTP_CREATE_USER_BANK = "phone_otp_create_user_bank"
    const val PHONE_OTP_UPDATE_PHONE = "phone_otp_update_phone"
    const val PHONE_OTP_UPDATE_EMAIL = "email_otp_update_email"

    /** webview settings **/
    const val WEBVIEW_ABOUTUS_ENG = "https://settings.klipz.id/aboutus/eng"
    const val WEBVIEW_ABOUTUS_IDN = "https://settings.klipz.id/aboutus/ind"
    const val WEBVIEW_FAQ_ENG = "https://settings.klipz.id/faq/eng"
    const val WEBVIEW_FAQ_IDN = "https://settings.klipz.id/faq/ind"
    const val WEBVIEW_PRIVACY_ENG = "https://settings.klipz.id/privacy/eng"
    const val WEBVIEW_PRIVACY_IND = "https://settings.klipz.id/privacy/ind"
    const val WEBVIEW_TNC_ENG = "https://settings.klipz.id/tnc/eng"
    const val WEBVIEW_TNC_IND = "https://settings.klipz.id/tnc/ind"
    const val WEBVIEW_CONTRACT_IND = "https://settings.klipz.id/contract/ind"
    const val WEBVIEW_CONTRACT_ENG = "https://settings.klipz.id/contract/eng"

    /** product schedule */
    const val DELETE_PRODUCT_SCHEDULE_VIDEO_TALK = "delete_product_schedule_video_talk"
    const val DELETE_PRODUCT_SCHEDULE = "delete_product_schedule"
    const val ADD_FAVORITE_PRODUCT_SCHEDULE_AUDIO = "add_favorite_product_schedule_audio"
    const val DELETE_FAVORITE_PRODUCT_SCHEDULE_AUDIO = "delete_favorite_product_schedule_audio"
    const val ADD_FAVORITE_PRODUCT_SCHEDULE_VIDEO = "add_favorite_product_schedule_video"
    const val DELETE_FAVORITE_PRODUCT_SCHEDULE_VIDEO = "delete_favorite_product_schedule_video"

    /** cart */
    const val GET_ORDER = "get_order"

    /** Type Of Input Date **/
    const val INPUT_DATE_TYPE_1 = 1
    const val INPUT_DATE_TYPE_2 = 2
    const val INPUT_DATE_TYPE_3 = 3
    const val INPUT_DATE_TYPE_4 = 4
    const val INPUT_DATE_TYPE_5 = 5
    const val INPUT_DATE_TYPE_6 = 6
    const val INPUT_DATE_TYPE_7 = 7
    const val INPUT_DATE_TYPE_8 = 8
    const val INPUT_DATE_TYPE_9 = 9
    const val INPUT_DATE_TYPE_10 = 10
    const val INPUT_DATE_TYPE_11 = 11
    const val INPUT_DATE_TYPE_12 = 12
    const val INPUT_DATE_TYPE_13 = 13
    const val INPUT_DATE_TYPE_14 = 14
    const val INPUT_DATE_TYPE_15 = 15

    /** Type Of Output Date **/
    const val OUTPUT_DATE_TYPE_1 = 1
    const val OUTPUT_DATE_TYPE_2 = 2
    const val OUTPUT_DATE_TYPE_3 = 3
    const val OUTPUT_DATE_TYPE_4 = 4
    const val OUTPUT_DATE_TYPE_5 = 5
    const val OUTPUT_DATE_TYPE_6 = 6
    const val OUTPUT_DATE_TYPE_7 = 7
    const val OUTPUT_DATE_TYPE_8 = 8
    const val OUTPUT_DATE_TYPE_9 = 9
    const val OUTPUT_DATE_TYPE_10 = 10

    /** File Upload Limiter < 5 MB **/
    const val FILE_SIZE_LIMITER = 5

    /** Transaction type **/
    const val TRANSACTION_REQUEST_KLIPZ_TYPE = 101
    const val TRANSACTION_MAKE_KLIPZ_TYPE = 201
}
