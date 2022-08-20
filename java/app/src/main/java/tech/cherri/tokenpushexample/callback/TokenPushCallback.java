package tech.cherri.tokenpushexample.callback;

import org.json.JSONObject;

public interface TokenPushCallback {
    void getTaskResult(JSONObject jsonObject);
}
