// package com.sagara.klipz.network.messaging
//
// import android.app.NotificationManager
// import androidx.core.content.ContextCompat
// import com.google.firebase.messaging.FirebaseMessaging
// import com.google.firebase.messaging.FirebaseMessagingService
// import com.google.firebase.messaging.RemoteMessage
// import com.google.gson.Gson
// import com.sagara.klipz.database.room.KlipzDB
// import com.sagara.klipz.database.room.entity.Message
// import com.sagara.klipz.database.sharedpref.PreferenceManager
// import com.sagara.klipz.utils.NotificationUtil.showNotification
//
// class KlipzMessagingService : FirebaseMessagingService() {
//
// override fun onNewToken(token: String) {
// super.onNewToken(token)
// PreferenceManager.instance.putString(KEY_FCM_TOKEN, token)
// TODO register the token to server when the new one generated
// }
//
// override fun onMessageReceived(message: RemoteMessage) {
// super.onMessageReceived(message)
// val title = message.notification?.title
// val body = message.notification?.body
// val message = Gson().fromJson(body, Message::class.java)
//
// showNotification(title, body)
// KlipzDB.invoke(this).messageDao().insert(message)
// }
//
// private fun showNotification(title: String?, content: String?) {
// val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
// notificationManager.showNotification(title, content)
// }
//
// companion object {
// const val KEY_FCM_TOKEN = "KEY_FCM_TOKEN"
//
// fun refreshToken() {
// var token = PreferenceManager.instance.getString(KEY_FCM_TOKEN, null)
// if (!token.isNullOrEmpty()) return
//
// FirebaseMessaging.getInstance().token.addOnCompleteListener {
// token = it.result
// PreferenceManager.instance.putString(KEY_FCM_TOKEN, token!!)
// }
// }
// }
// }
