package tech.cherri.tokenpushexample

import android.app.Activity
import android.app.FragmentManager
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import tech.cherri.tokenpushexample.callback.TokenPushCallback
import tech.cherri.tokenpushexample.fragment.AddCardFailFragment
import tech.cherri.tokenpushexample.fragment.AddCardSuccessFragment
import tech.cherri.tokenpushexample.fragment.LoginFragment
import tech.cherri.tokenpushexample.fragment.TermsFragment
import org.json.JSONObject

class MainActivity : Activity(), TokenPushCallback {
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        setupUI()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        val tspToken = tSPPushToken
        val cancelUrl = cancelUrl
        Log.d(TAG, "tspToken: $tspToken , cancelUrl: $cancelUrl")
        if (tspToken != null && tspToken != "") {
            val tokenPushParam: TokenPushParam = TokenPushParam.TokenPushParamBuilder()
                .tokenPushCallback(this@MainActivity)
                .context(this@MainActivity)
                .tspToken(tspToken)
                .cancelUrl(cancelUrl)
                .build()
            val loginFragment = LoginFragment()
            loginFragment.setTokenPushParam(tokenPushParam)
            val fragmentManager: FragmentManager = getFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragment_container_view,
                loginFragment,
                LoginFragment::class.java.getSimpleName()
            )
            fragmentTransaction.commit()
        } else {
            val loginFragment = LoginFragment()
            loginFragment.setTokenPushParam(null)
            val fragmentManager: FragmentManager = getFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragment_container_view,
                loginFragment,
                LoginFragment::class.java.getSimpleName()
            )
            fragmentTransaction.commit()
        }
    }

    private fun setupUI() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
        }
    }

    private val tSPPushToken: String
        private get() {
            var tspPushToken = ""
            if (getIntent()?.getData() != null && getIntent()?.getData()
                    ?.getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_TSP_PUSH_TOKEN) != null
            ) {
                Log.d(
                    TAG,
                    "getTSPPushToken: " + getIntent()?.getData()
                        ?.getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_TSP_PUSH_TOKEN)
                )
                tspPushToken = getIntent()?.getData()
                    ?.getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_TSP_PUSH_TOKEN)!!
            }
            return tspPushToken
        }
    private val cancelUrl: String
        private get() {
            var cancelUrl = ""
            if (getIntent()?.getData() != null && getIntent().getData()
                    ?.getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_CANCEL_URL) != null
            ) {
                Log.d(
                    TAG,
                    "getCancelUrl: " + getIntent().getData()
                        ?.getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_CANCEL_URL)
                )
                cancelUrl = getIntent()?.getData()
                    ?.getQueryParameter(Constants.ISSUER_APP_INTENT_DATA_CANCEL_URL)!!
            }
            return cancelUrl
        }

    fun showProgressing() {
        progressDialog?.show()
    }

    fun dismissProgressing() {
        progressDialog?.dismiss()
    }

    protected override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun getTaskResult(jsonObject: JSONObject?) {
        dismissProgressing()
        try {
            val status: Int = jsonObject!!.getInt("status")
            Log.d(TAG, "getTaskResult status = $status")
            //take care 19004 to be success case for master card test case
            if (status == Constants.STATUS_SUCCESS || status == Constants.STATUS_REQUIRE_ADDITIONAL_AUTHENTICATION) {
                val addCardSuccessFragment = AddCardSuccessFragment()
                addCardSuccessFragment.setJSONObjectResult(jsonObject)
                val fragmentManager: FragmentManager = getFragmentManager()
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.fragment_container_view,
                    addCardSuccessFragment,
                    TermsFragment::class.java.getSimpleName()
                )
                fragmentTransaction.commit()
            } else {
                val addCardFailFragment = AddCardFailFragment()
                addCardFailFragment.setJSONObjectResult(jsonObject)
                val fragmentManager: FragmentManager = getFragmentManager()
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(
                    R.id.fragment_container_view,
                    addCardFailFragment,
                    TermsFragment::class.java.getSimpleName()
                )
                fragmentTransaction.commit()
            }
        } catch (e: Exception) {
            Log.e(TAG, "getTaskResult, error: " + Log.getStackTraceString(e))
        }
    }
}