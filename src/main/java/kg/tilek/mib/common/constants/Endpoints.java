package kg.tilek.mib.common.constants;


public final class Endpoints {

    private static final String API = "/api";
    private static final String V1 = "/v1";
    private static final String MIB_V1 = API + V1;
    public static final String USER_URL = MIB_V1 + "/user";
    public static final String SEARCH_URL = MIB_V1 + "/search";
    public static final String TRANSACTION_URL = MIB_V1 + "/transaction";

    private Endpoints() {
    }
}
