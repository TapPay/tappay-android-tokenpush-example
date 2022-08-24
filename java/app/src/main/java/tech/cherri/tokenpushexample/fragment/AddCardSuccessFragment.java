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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import tech.cherri.tokenpushexample.Constants;
import tech.cherri.tokenpushexample.JSONUtils;
import tech.cherri.tokenpushexample.R;

public class AddCardSuccessFragment extends Fragment {
    private ImageButton backToLoginBtn;
    private TextView resultTextView;
    private Button backToBankBtn;
    private JSONObject jSONObjectResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_card_success, container, false);
    }

    @Nullable
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backToLoginBtn = view.findViewById(R.id.back_to_login_btn);
        backToLoginBtn.setOnClickListener(v -> {
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(AddCardSuccessFragment.this);
            fragmentTransaction.replace(R.id.fragment_container_view, loginFragment, LoginFragment.class.getSimpleName());
            fragmentTransaction.commit();
        });
        backToBankBtn = view.findViewById(R.id.back_to_bank_button);
        resultTextView = view.findViewById(R.id.result);
        resultTextView.setText(JSONUtils.pretty(jSONObjectResult.toString()));
        if (jSONObjectResult != null && jSONObjectResult.optString(Constants.RESPONSE_CALLBACK_URL_KEY) != null && jSONObjectResult.optString(Constants.RESPONSE_CALLBACK_URL_KEY) != "") {
            backToBankBtn.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jSONObjectResult.optString(Constants.RESPONSE_CALLBACK_URL_KEY)));
                startActivity(browserIntent);
            });
        } else {
            backToBankBtn.setVisibility(View.GONE);
        }

    }

    public JSONObject getJSONObjectResult() {
        return jSONObjectResult;
    }

    public void setJSONObjectResult(JSONObject jsonObjectResult) {
        this.jSONObjectResult = jsonObjectResult;
    }

}