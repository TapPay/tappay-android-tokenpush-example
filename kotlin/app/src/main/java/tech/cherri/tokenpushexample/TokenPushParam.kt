package tech.cherri.tokenpushexample

import android.content.Context
import tech.cherri.tokenpushexample.callback.TokenPushCallback

class TokenPushParam(tokenPushParamBuilder: TokenPushParamBuilder) {

    var context: Context?
    var tokenPushCallback: TokenPushCallback?
    var tspToken: String?
    var cancelUrl: String?

    class TokenPushParamBuilder {
        var context: Context? = null
        var tokenPushCallback: TokenPushCallback? = null
        var tspToken: String? = null
        var cancelUrl: String? = null
        fun context(context: Context?): TokenPushParamBuilder {
            this.context = context
            return this
        }

        fun tokenPushCallback(tokenPushCallback: TokenPushCallback?): TokenPushParamBuilder {
            this.tokenPushCallback = tokenPushCallback
            return this
        }

        fun tspToken(tspToken: String?): TokenPushParamBuilder {
            this.tspToken = tspToken
            return this
        }

        fun cancelUrl(cancelUrl: String?): TokenPushParamBuilder {
            this.cancelUrl = cancelUrl
            return this
        }

        fun build(): TokenPushParam {
            return TokenPushParam(this)
        }
    }

    init {
        tokenPushCallback = tokenPushParamBuilder.tokenPushCallback
        context = tokenPushParamBuilder.context
        tspToken = tokenPushParamBuilder.tspToken
        cancelUrl = tokenPushParamBuilder.cancelUrl
    }

}