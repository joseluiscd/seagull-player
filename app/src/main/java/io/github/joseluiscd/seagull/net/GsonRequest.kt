package io.github.joseluiscd.seagull.net

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException

/**
 * Created by joseluis on 11/01/18.
 */

class GsonRequest<T>(
            method: Int,
            endpoint: String,
            private val clazz: Class<T>,
            private val listener: Response.Listener<T>,
            errorListener: Response.ErrorListener)
    : Request<T>(method, endpoint, errorListener) {

    override fun deliverResponse(response: T) {
        listener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<T> {
        return try{
            val json = String(response.data, Charset.forName(HttpHeaderParser.parseCharset(response.headers)))
            Response.success(Gson().fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException){
            Response.error(ParseError(e))
        }
    }

}