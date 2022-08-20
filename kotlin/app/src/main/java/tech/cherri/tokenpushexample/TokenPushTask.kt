package tech.cherri.tokenpushexample

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class TokenPushTask(private val tokenPushParam: TokenPushParam) :
    AsyncTask<String?, Void?, JSONObject>() {
    private val jsonRequest: JSONObject
    private val partnerKey: String
    private val targetUrl: String

    override fun doInBackground(vararg p0: String?): JSONObject {
        var outputJSONObj = JSONObject()
        try {
            val connection = prepareHttpURLConnection()
            writeRequestTo(connection)
            Log.d(TAG, "doInBackground responseCode: " + connection.responseCode)
            Log.d(TAG, "doInBackground responseMessage(): " + connection.responseMessage)
            val responseStr = getResponseString(connection)
            Log.d(TAG, "doInBackground responseStr: $responseStr")
            outputJSONObj = JSONObject(responseStr)
        } catch (e: Exception) {
            Log.e(TAG, "doInBackground error: " + Log.getStackTraceString(e))
            outputJSONObj = JSONObject()
            try {
                outputJSONObj.put("status", -1)
                outputJSONObj.put("exception", e.toString())
            } catch (jsonException: JSONException) {
                Log.e(TAG, "doInBackground error: " + Log.getStackTraceString(e))
            }
        }
        return outputJSONObj
    }

    override fun onPostExecute(jsonObject: JSONObject) {
        try {
            Log.d(TAG, "onPostExecute, jsonObject: $jsonObject")
            tokenPushParam?.tokenPushCallback?.getTaskResult(jsonObject)
        } catch (e: Exception) {
            Log.e(TAG, "onPostExecute error: " + Log.getStackTraceString(e))
        }
    }

    @Throws(IOException::class)
    private fun prepareHttpURLConnection(): HttpURLConnection {
        val url = URL(targetUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 40000
        connection.readTimeout = 30000
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("x-api-key", partnerKey)
        connection.requestMethod = "POST"
        return connection
    }

    @Throws(IOException::class)
    private fun writeRequestTo(connection: HttpURLConnection) {
        val wr = OutputStreamWriter(connection.outputStream)
        wr.write(jsonRequest.toString())
        Log.d(TAG, "writeRequestTo request: $jsonRequest")
        wr.flush()
        wr.close()
    }

    @Throws(IOException::class)
    private fun getResponseString(connection: HttpURLConnection): String {
        val br = BufferedReader(
            InputStreamReader(connection.inputStream, "utf-8")
        )
        val responseStr = StringBuilder("")
        var line: String? = ""
        while (br.readLine().also { line = it } != null) {
            responseStr.append(line)
        }
        br.close()
        return responseStr.toString()
    }

    companion object {
        private val TAG = TokenPushTask::class.java.simpleName
    }

    init {
        partnerKey = tokenPushParam.context!!.getString(R.string.partner_key)
        targetUrl = tokenPushParam.context!!.getString(R.string.token_push_url)
        jsonRequest = JSONObject()
        try {
            jsonRequest.put("partner_key", partnerKey)
            jsonRequest.put("tsp_push_token", tokenPushParam.tspToken)
        } catch (e: JSONException) {
            Log.e(TAG, "init TokenPushTask error: " + Log.getStackTraceString(e))
        }
    }

}