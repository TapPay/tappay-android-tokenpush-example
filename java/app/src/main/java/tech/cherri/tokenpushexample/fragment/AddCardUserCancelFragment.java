package tech.cherri.tokenpushexample.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tech.cherri.tokenpushexample.R;
import tech.cherri.tokenpushexample.TokenPushParam;

public class AddCardUserCancelFragment extends Fragment {
    private ImageButton backToLoginBtn;
    private Button backToBankBtn;
    private TokenPushParam tokenPushParam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_card_user_cancel, container, false);
    }

    @Nullable
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backToLoginBtn = view.findViewById(R.id.back_to_login_btn);
        backToLoginBtn.setOnClickListener(v -> {
            LoginFragment loginFragment = new LoginFragment();
            loginFragment.setTokenPushParam(tokenPushParam);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(AddCardUserCancelFragment.this);
            fragmentTransaction.replace(R.id.fragment_container_view, loginFragment, LoginFragment.class.getSimpleName());
            fragmentTransaction.commit();
        });
        backToBankBtn = view.findViewById(R.id.back_to_bank_button);
        if (tokenPushParam != null && tokenPushParam.getCancelUrl() != null) {
            backToBankBtn.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tokenPushParam.getCancelUrl()));
                startActivity(browserIntent);
            });
        } else {
            backToBankBtn.setVisibility(View.GONE);
        }
    }

    public TokenPushParam getTokenPushParam() {
        return tokenPushParam;
    }

    public void setTokenPushParam(TokenPushParam tokenPushParam) {
        this.tokenPushParam = tokenPushParam;
    }
}