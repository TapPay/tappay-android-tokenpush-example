package tech.cherri.tokenpushexample;


import android.content.Context;

import tech.cherri.tokenpushexample.callback.TokenPushCallback;

public class TokenPushParam {
    private Context context;
    private TokenPushCallback tokenPushCallback;
    private String tspToken;
    private String cancelUrl;

    public TokenPushParam(TokenPushParamBuilder tokenPushParamBuilder) {
        tokenPushCallback = tokenPushParamBuilder.tokenPushCallback;
        context = tokenPushParamBuilder.context;
        tspToken = tokenPushParamBuilder.tspToken;
        cancelUrl = tokenPushParamBuilder.cancelUrl;
    }

    public static class TokenPushParamBuilder {
        private Context context;
        private TokenPushCallback tokenPushCallback;
        private String tspToken;
        private String cancelUrl;

        public TokenPushParamBuilder context(Context context) {
            this.context = context;
            return this;
        }

        public TokenPushParamBuilder tokenPushCallback(TokenPushCallback tokenPushCallback) {
            this.tokenPushCallback = tokenPushCallback;
            return this;
        }

        public TokenPushParamBuilder tspToken(String tspToken) {
            this.tspToken = tspToken;
            return this;
        }

        public TokenPushParamBuilder cancelUrl(String cancelUrl) {
            this.cancelUrl = cancelUrl;
            return this;
        }

        public TokenPushParam build() {
            return new TokenPushParam(this);
        }
    }


    public Context getContext() {
        return context;
    }

    public TokenPushCallback getTokenPushCallback() {
        return tokenPushCallback;
    }

    public String getTspToken() {
        return tspToken;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

}
