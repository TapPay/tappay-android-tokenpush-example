package tech.cherri.tokenpushexample.callback

import org.json.JSONObject

interface TokenPushCallback {
    fun getTaskResult(jsonObject: JSONObject?)
}