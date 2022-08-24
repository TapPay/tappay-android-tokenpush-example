package tech.cherri.tokenpushexample.fragment

import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.json.JSONObject
import tech.cherri.tokenpushexample.Constants
import tech.cherri.tokenpushexample.JSONUtils
import tech.cherri.tokenpushexample.R

class AddCardSuccessFragment : Fragment() {
    private var jSONObjectResult: JSONObject? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_card_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var backToLoginBtn: ImageButton = view.findViewById(R.id.back_to_login_btn)
        backToLoginBtn.setOnClickListener(View.OnClickListener { v: View? ->
            val loginFragment = LoginFragment()
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(this@AddCardSuccessFragment)
            fragmentTransaction.replace(
                R.id.fragment_container_view,
                loginFragment,
                LoginFragment::class.java.simpleName
            )
            fragmentTransaction.commit()
        })
        var backToBankBtn: Button = view.findViewById(R.id.back_to_bank_button)
        var resultTextView: TextView = view.findViewById<TextView>(R.id.result)
        resultTextView.setText(JSONUtils.pretty(jSONObjectResult.toString()))
        if (jSONObjectResult?.optString(Constants.RESPONSE_CALLBACK_URL_KEY) != null && jSONObjectResult?.optString(
                Constants.RESPONSE_CALLBACK_URL_KEY
            ) !== ""
        ) {
            backToBankBtn.setOnClickListener(View.OnClickListener { v: View? ->
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(jSONObjectResult?.optString(Constants.RESPONSE_CALLBACK_URL_KEY))
                )
                startActivity(browserIntent)
            })
        } else {
            backToBankBtn.setVisibility(View.GONE)
        }
    }

    fun getJSONObjectResult(): JSONObject? {
        return jSONObjectResult
    }

    fun setJSONObjectResult(jsonObjectResult: JSONObject?) {
        jSONObjectResult = jsonObjectResult
    }
}