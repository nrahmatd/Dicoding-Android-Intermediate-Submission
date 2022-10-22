package com.nrahmatd.storyapp.utils

import android.content.Context
import android.graphics.Outline
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.nrahmatd.storyapp.app.GlobalApp
import com.nrahmatd.storyapp.network.exception.ApiException
import com.nrahmatd.storyapp.thirdparty.notify.Notify
import com.nrahmatd.storyapp.thirdparty.notify.NotifyProvider
import com.nrahmatd.storyapp.utils.general_model.GeneralModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.Date
import java.util.Locale
import okhttp3.ResponseBody
import retrofit2.Response

fun loadImage(url: String?, imageView: ImageView, placeholder: Int) {
    if (url != null)
        Glide.with(GlobalApp.getAppContext()).load(url).diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .dontAnimate()
            .let { request ->
                if (imageView.drawable != null) {
                    request.placeholder(imageView.drawable.constantState?.newDrawable()?.mutate())
                } else {
                    request
                }
            }
            .apply(RequestOptions.placeholderOf(placeholder).error(placeholder))
            .into(imageView)
    else
        Glide.with(GlobalApp.getAppContext()).load(placeholder).into(imageView)
}

fun setRoundedOnImageView(img: View, valueOfRound: Float): View {
    img.outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(
                0,
                0,
                view!!.width,
                view.height,
                valueOfRound
            )
        }
    }
    img.clipToOutline = true
    return img
}

fun log(tag: String, message: String) {
    Log.d(tag, message)
}

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun transitionFade(root: ViewGroup, target: View) {
    val transition: Transition = Fade()
    transition.duration = 600
    transition.addTarget(target)
    TransitionManager.beginDelayedTransition(root, transition)
//    target.visibility = if (target.isVisible) View.INVISIBLE else View.VISIBLE
}

fun setResponse(liveData: MutableLiveData<ResponseHelper>, code: Int, value: Any) {
    liveData.value = ResponseHelper(code, value)
}

fun setErrorBody(errorBody: ResponseBody): GeneralModel {
    return GsonBuilder().registerTypeAdapter(
        GeneralModel::class.java,
        ErrorDeserializer()
    ).create().fromJson(errorBody.string(), GeneralModel::class.java)
}

fun <T> setRequest(
    liveData: MutableLiveData<ResponseHelper>,
    request: Response<T>,
    code_request: Int
) {
    when (request.code()) {
        ResponseHelper.OK, ResponseHelper.Created -> {
            println("response body.. $liveData - ${request.body()}")
            setResponse(liveData, code_request, request.body()!!)
        }
        ResponseHelper.BadRequest -> {
            val error = setErrorBody(request.errorBody()!!)
            setResponse(liveData, ResponseHelper.BadRequest, error)
        }
        ResponseHelper.Unauthorized -> {
            val error = setErrorBody(request.errorBody()!!)
            setResponse(liveData, ResponseHelper.BadRequest, error)
        }
        ResponseHelper.NotFound -> {
            val error = setErrorBody(request.errorBody()!!)
            setResponse(liveData, ResponseHelper.NotFound, error)
        }
        ResponseHelper.Timeout -> {
            val error = setErrorBody(request.errorBody()!!)
            setResponse(liveData, ResponseHelper.Timeout, error)
        }
        else -> {
            throw ApiException(request.code().toString())
        }
    }
}

class ErrorDeserializer : JsonDeserializer<GeneralModel?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GeneralModel {
        return Gson().fromJson(json, GeneralModel::class.java)
    }
}

fun parseDate(times: String): String? {
    val inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val inputFormat = SimpleDateFormat(inputPattern, Locale("id", "ID"))
    var time: String? = null
    try {
        val cal = setDateWithTimeZone(inputFormat.parse(times)!!)
        val format = "dd MMMM yyyy - HH:mm"
        val sdf = SimpleDateFormat(format, Locale("id", "ID"))
        time = sdf.format(cal)
        return time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return time
}

fun setDateWithTimeZone(date: Date): Date {
    val dateFormatToISO8601 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("id", "ID"))
    val dateConvert = dateFormatToISO8601.format(date)
    val zp = ZonedDateTime.parse(dateConvert)
    return Date.from(zp.toInstant())
}

fun sendNotify(TAG: String, response: Any) {
    Notify.send(ResponseNotifyHelper(TAG, response))
}

fun getNotify(
    compositeDisposable: CompositeDisposable,
    notifyResponse: (notifyResponse: ResponseNotifyHelper) -> Unit
) {
    compositeDisposable.add(
        Notify.listen(ResponseNotifyHelper::class.java, NotifyProvider(), {
            notifyResponse(it)
        }, {
            log(GlobalVariable.NOTIFY_ERROR, it.message!!)
        })
    )
}
