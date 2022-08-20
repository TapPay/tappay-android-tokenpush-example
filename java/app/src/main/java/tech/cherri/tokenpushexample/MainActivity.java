package tech.cherri.tokenpushexample;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import tech.cherri.tokenpushexample.callback.TokenPushCallback;
import tech.cherri.tokenpushexample.fragment.AddCardFailFragment;
import tech.cherri.tokenpushexample.fragment.AddCardSuccessFragment;
import tech.cherri.tokenpushexample.fragment.LoginFragment;
import tech.cherri.tokenpushexample.fragment.TermsFragment;

public class MainActivity extends Activity implements TokenPushCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        setupUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        String tspToken = getTSPPushToken();
        String cancelUrl = getCancelUrl();
        Log.d(TAG, "tspToken: " + tspToken + " , cancelUrl: " + cancelUrl);
        if (tspToken != null && !tspToken.equals("")) {
            TokenPushParam tokenPushParam =
                    new TokenPushParam.TokenPushParamBuilder()
                            .tokenPushCallback(MainActivity.this)
                            .context(MainActivity.this)
                            .tspToken(tspToken)
                            .cancelUrl(cancelUrl)
                            .build();

            LoginFragment loginFragment = new LoginFragment();
            loginFragment.setTokenPushParam(tokenPushParam);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_view, loginFragment, LoginFragment.class.getSimpleName());
            fragmentTransaction.commit();
        } else {
            LoginFragment loginFragment = new LoginFragment();
            loginFragment.setTokenPushParam(null);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_view, loginFragment, LoginFragment.class.getSimpleName());
            fragmentTransaction.commit();
        }
    }

    private void setupUI() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
    }

    private String getTSPPushToken() {
        String tspPushToken = "";
        if (getIntent().getData() != null && getIntent().getData().getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_TSP_PUSH_TOKEN) != null) {
            Log.d(TAG, "getTSPPushToken: " + getIntent().getData().getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_TSP_PUSH_TOKEN));
            tspPushToken = getIntent().getData().getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_TSP_PUSH_TOKEN);
        }
        return tspPushToken;
    }

    private String getCancelUrl() {
        String cancelUrl = "";
        if (getIntent().getData() != null && getIntent().getData().getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_CANCEL_URL) != null) {
            Log.d(TAG, "getCancelUrl: " + getIntent().getData().getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_CANCEL_URL));
            cancelUrl = getIntent().getData().getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_CANCEL_URL);
        }
        return cancelUrl;
    }

    public void showProgressing() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    public void dismissProgressing() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getTaskResult(JSONObject jsonObject) {
        dismissProgressing();
        try {
            int status = jsonObject.getInt("status");
            Log.d(TAG, "getTaskResult status = " + status);
            //we take 19004 to be success case for master card test case
            if (status == Constants.STATUS_SUCCESS || status == Constants.STATUS_REQUIRE_ADDITIONAL_AUTHENTICATION) {
                AddCardSuccessFragment addCardSuccessFragment = new AddCardSuccessFragment();
                addCardSuccessFragment.setJSONObjectResult(jsonObject);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, addCardSuccessFragment, TermsFragment.class.getSimpleName());
                fragmentTransaction.commit();
            } else {
                AddCardFailFragment addCardFailFragment = new AddCardFailFragment();
                addCardFailFragment.setJSONObjectResult(jsonObject);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, addCardFailFragment, TermsFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            Log.e(TAG, "getTaskResult, error: " + Log.getStackTraceString(e));
        }
    }
}