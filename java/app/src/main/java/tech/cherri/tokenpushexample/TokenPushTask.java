package tech.cherri.tokenpushexample;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import tech.cherri.tokenpushexample.enumeration.Env;

public class TokenPushTask extends AsyncTask<String, Void, JSONObject> {
    private static final String TAG = TokenPushTask.class.getSimpleName();
    private JSONObject jsonRequest;
    private String partnerKey;
    private String targetUrl;
    private Env env;
    private TokenPushParam tokenPushParam;

    public TokenPushTask(TokenPushParam tokenPushParam) {
        this.tokenPushParam = tokenPushParam;
        env = Env.Sandbox;
        preparePartnerKey();
        prepareTargetUrl();
        jsonRequest = new JSONObject();
        try {
            jsonRequest.put("partner_key", partnerKey);
            jsonRequest.put("tsp_push_token", tokenPushParam.getTspToken());
        } catch (JSONException e) {
            Log.e(TAG, "init TokenPushTask error: " + Log.getStackTraceString(e));
        }
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject outputJSONObj = new JSONObject();

        try {
            HttpURLConnection connection = prepareHttpURLConnection();
            writeRequestTo(connection);

            Log.d(TAG, "doInBackground responseCode: " + connection.getResponseCode());
            Log.d(TAG, "doInBackground responseMessage(): " + connection.getResponseMessage());

            String responseStr = getResponseString(connection);
            Log.d(TAG, "doInBackground responseStr: " + responseStr);

            outputJSONObj = new JSONObject(responseStr);
        } catch (Exception e) {
            Log.e(TAG, "doInBackground error: " + Log.getStackTraceString(e));
            outputJSONObj = new JSONObject();
            try {
                outputJSONObj.put("status", -1);
                outputJSONObj.put("exception", e.toString());
            } catch (JSONException jsonException) {
                Log.e(TAG, "doInBackground error: " + Log.getStackTraceString(e));
            }
        }
        return outputJSONObj;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            Log.d(TAG, "onPostExecute, jsonObject: " + jsonObject.toString());
            tokenPushParam.getTokenPushCallback().getTaskResult(jsonObject);
        } catch (Exception e) {
            Log.e(TAG, "onPostExecute error: " + Log.getStackTraceString(e));
        }
    }

    @NonNull
    private HttpURLConnection prepareHttpURLConnection() throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(40000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("x-api-key", partnerKey);
        connection.setRequestMethod("POST");
        return connection;
    }

    private void writeRequestTo(HttpURLConnection connection) throws IOException {
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(jsonRequest.toString());
        Log.d(TAG, "writeRequestTo request: " + jsonRequest.toString());
        wr.flush();
        wr.close();
    }

    @NonNull
    private String getResponseString(HttpURLConnection connection) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"));
        StringBuilder responseStr = new StringBuilder("");
        String line = "";

        while ((line = br.readLine()) != null) {
            responseStr.append(line);
        }
        br.close();
        return responseStr.toString();
    }

    private void preparePartnerKey(){
        switch (env){
            case Sandbox:
                partnerKey = tokenPushParam.getContext().getString(R.string.sandbox_partner_key);
                break;
            case Prod:
                partnerKey = tokenPushParam.getContext().getString(R.string.prod_partner_key);
                break;
        }
    }

    private void prepareTargetUrl(){
        switch (env){
            case Sandbox:
                targetUrl = tokenPushParam.getContext().getString(R.string.sandbox_token_push_url);
                break;
            case Prod:
                targetUrl = tokenPushParam.getContext().getString(R.string.prod_token_push_url);
                break;
        }
    }
}