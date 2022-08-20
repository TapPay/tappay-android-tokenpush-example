import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import tech.cherri.tokenpushexample.R
import tech.cherri.tokenpushexample.TokenPushParam
import tech.cherri.tokenpushexample.fragment.LoginFragment

class AddCardUserCancelFragment : Fragment() {
    private var tokenPushParam: TokenPushParam? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_card_user_cancel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var backToLoginBtn: ImageButton = view.findViewById(R.id.back_to_login_btn)
        backToLoginBtn.setOnClickListener(View.OnClickListener { v: View? ->
            val loginFragment = LoginFragment()
            loginFragment.setTokenPushParam(tokenPushParam)
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(this@AddCardUserCancelFragment)
            fragmentTransaction.replace(
                R.id.fragment_container_view,
                loginFragment,
                LoginFragment::class.java.getSimpleName()
            )
            fragmentTransaction.commit()
        })
        var backToBankBtn: Button = view.findViewById(R.id.back_to_bank_button)
        if (tokenPushParam != null && tokenPushParam?.cancelUrl != null) {
            backToBankBtn.setOnClickListener(View.OnClickListener { v: View? ->
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(tokenPushParam?.cancelUrl))
                startActivity(browserIntent)
            })
        } else {
            backToBankBtn.setVisibility(View.GONE)
        }
    }

    fun getTokenPushParam(): TokenPushParam? {
        return tokenPushParam
    }

    fun setTokenPushParam(tokenPushParam: TokenPushParam?) {
        this.tokenPushParam = tokenPushParam
    }
}