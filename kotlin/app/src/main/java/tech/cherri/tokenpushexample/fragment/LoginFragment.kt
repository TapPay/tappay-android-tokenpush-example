package tech.cherri.tokenpushexample.fragment

import AddCardUserCancelFragment
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import tech.cherri.tokenpushexample.R
import tech.cherri.tokenpushexample.TokenPushParam

class LoginFragment : Fragment() {
    private var tokenPushParam: TokenPushParam? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var loginBtn: Button = view.findViewById(R.id.login_button)
        loginBtn.setOnClickListener(View.OnClickListener { v: View? ->
            if (tokenPushParam == null) {
                val addCardUserCancelFragment = AddCardUserCancelFragment()
                addCardUserCancelFragment.setTokenPushParam(tokenPushParam)
                val fragmentManager = fragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.remove(this@LoginFragment)
                fragmentTransaction.replace(
                    R.id.fragment_container_view,
                    addCardUserCancelFragment,
                    AddCardUserCancelFragment::class.java.getSimpleName()
                )
                fragmentTransaction.commit()
            } else {
                val termsFragment = TermsFragment()
                termsFragment.setTokenPushParam(tokenPushParam)
                val fragmentManager = fragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.remove(this@LoginFragment)
                fragmentTransaction.replace(
                    R.id.fragment_container_view,
                    termsFragment,
                    TermsFragment::class.java.simpleName
                )
                fragmentTransaction.commit()
            }
        })
    }

    fun getTokenPushParam(): TokenPushParam? {
        return tokenPushParam
    }

    fun setTokenPushParam(tokenPushParam: TokenPushParam?) {
        this.tokenPushParam = tokenPushParam
    }
}