package tech.cherri.tokenpushexample;

public class Constants {
    //server response status code
    public static final Integer STATUS_SUCCESS = 0;
    public static final Integer STATUS_REQUIRE_ADDITIONAL_AUTHENTICATION = 19004;
    //server response
    public static final String RESPONSE_CALLBACK_URL_KEY = "callback_url";
    //intent data key from issuer app
    public static final String ISSUER_APP_INTENT_DATA_CANCEL_URL = "cancelUrl";
    public static final String ISSUER_APP_INTENT_DATA_TSP_PUSH_TOKEN = "tspPushToken";
}
