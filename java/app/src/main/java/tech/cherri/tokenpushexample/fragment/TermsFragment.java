package tech.cherri.tokenpushexample.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tech.cherri.tokenpushexample.MainActivity;
import tech.cherri.tokenpushexample.R;
import tech.cherri.tokenpushexample.TokenPushParam;
import tech.cherri.tokenpushexample.TokenPushTask;

public class TermsFragment extends Fragment {

    private CheckBox userAgreeCheckBox;
    private Button agreeBtn;
    private Button cancelBtn;
    private TokenPushParam tokenPushParam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_card_terms, container, false);
    }

    @Nullable
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userAgreeCheckBox = view.findViewById(R.id.user_agree_checkbox);
        userAgreeCheckBox.setOnClickListener(v -> {
            agreeBtn.setEnabled(userAgreeCheckBox.isChecked());
        });
        cancelBtn = view.findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(TermsFragment.this);

            AddCardUserCancelFragment addCardUserCancelFragment = new AddCardUserCancelFragment();
            addCardUserCancelFragment.setTokenPushParam(tokenPushParam);
            fragmentTransaction.replace(R.id.fragment_container_view, addCardUserCancelFragment, AddCardUserCancelFragment.class.getSimpleName());
            fragmentTransaction.commit();
        });
        agreeBtn = view.findViewById(R.id.agree_button);
        agreeBtn.setOnClickListener(v -> {
            ((MainActivity) getActivity()).showProgressing();
            TokenPushTask tokenPushTask = new TokenPushTask(tokenPushParam);
            tokenPushTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(TermsFragment.this);
            fragmentTransaction.commit();
        });
    }

    public TokenPushParam getTokenPushParam() {
        return tokenPushParam;
    }

    public void setTokenPushParam(TokenPushParam tokenPushParam) {
        this.tokenPushParam = tokenPushParam;
    }
}