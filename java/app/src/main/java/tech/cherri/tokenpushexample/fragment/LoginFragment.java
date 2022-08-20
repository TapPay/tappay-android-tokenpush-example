package tech.cherri.tokenpushexample.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tech.cherri.tokenpushexample.R;
import tech.cherri.tokenpushexample.TokenPushParam;

public class LoginFragment extends Fragment {

    private Button loginBtn;
    private TokenPushParam tokenPushParam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Nullable
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginBtn = view.findViewById(R.id.login_button);
        loginBtn.setOnClickListener(v -> {
            if (tokenPushParam == null) {
                AddCardUserCancelFragment addCardUserCancelFragment = new AddCardUserCancelFragment();
                addCardUserCancelFragment.setTokenPushParam(tokenPushParam);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(LoginFragment.this);
                fragmentTransaction.replace(R.id.fragment_container_view, addCardUserCancelFragment, AddCardUserCancelFragment.class.getSimpleName());
                fragmentTransaction.commit();
            } else {
                TermsFragment termsFragment = new TermsFragment();
                termsFragment.setTokenPushParam(tokenPushParam);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(LoginFragment.this);
                fragmentTransaction.replace(R.id.fragment_container_view, termsFragment, TermsFragment.class.getSimpleName());
                fragmentTransaction.commit();
            }
        });
    }

    public TokenPushParam getTokenPushParam() {
        return tokenPushParam;
    }

    public void setTokenPushParam(TokenPushParam tokenPushParam) {
        this.tokenPushParam = tokenPushParam;
    }
}