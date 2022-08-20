package tech.cherri.tokenpushexample.fragment

import AddCardUserCancelFragment
import android.app.Fragment
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import tech.cherri.tokenpushexample.MainActivity
import tech.cherri.tokenpushexample.R
import tech.cherri.tokenpushexample.TokenPushParam
import tech.cherri.tokenpushexample.TokenPushTask

class TermsFragment : Fragment() {
    private var tokenPushParam: TokenPushParam? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_card_terms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userAgreeCheckBox: CheckBox = view.findViewById(R.id.user_agree_checkbox)
        var agreeBtn: Button = view.findViewById(R.id.agree_button)
        userAgreeCheckBox.setOnClickListener(View.OnClickListener { v: View? ->
            agreeBtn.isEnabled = userAgreeCheckBox!!.isChecked()
        })
        var cancelBtn: Button = view.findViewById(R.id.cancel_button)
        cancelBtn.setOnClickListener(View.OnClickListener { v: View? ->
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(this@TermsFragment)
            val addCardUserCancelFragment = AddCardUserCancelFragment()
            addCardUserCancelFragment.setTokenPushParam(tokenPushParam)
            fragmentTransaction.replace(
                R.id.fragment_container_view,
                addCardUserCancelFragment,
                AddCardUserCancelFragment::class.java.getSimpleName()
            )
            fragmentTransaction.commit()
        })
        agreeBtn = view.findViewById(R.id.agree_button)
        agreeBtn.setOnClickListener(View.OnClickListener { v: View? ->
            (activity as MainActivity).showProgressing()
            val tokenPushTask = tokenPushParam?.let { TokenPushTask(it) }
            tokenPushTask?.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(this@TermsFragment)
            fragmentTransaction.commit()
        })
    }

    fun getTokenPushParam(): TokenPushParam? {
        return tokenPushParam
    }

    fun setTokenPushParam(tokenPushParam: TokenPushParam?) {
        this.tokenPushParam = tokenPushParam
    }
}