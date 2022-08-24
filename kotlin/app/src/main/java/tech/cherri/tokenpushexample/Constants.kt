package tech.cherri.tokenpushexample

object Constants {
    //server response status code
    const val STATUS_SUCCESS = 0
    const val STATUS_REQUIRE_ADDITIONAL_AUTHENTICATION = 19004

    //server response
    const val RESPONSE_CALLBACK_URL_KEY = "callback_url"

    //intent data key from issuer app
    const val ISSUER_APP_INTENT_DATA_CANCEL_URL = "cancelUrl"
    const val ISSUER_APP_INTENT_DATA_TSP_PUSH_TOKEN = "tspPushToken"
}