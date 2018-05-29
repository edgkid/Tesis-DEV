package tegdev.optotypes;

/**
 * Created by Edgar on 27/05/2018.
 */

public class ConfgConnect {

    private static String ipWebService;
    private static String ipShowTest;
    private static String  portConecction;


    public ConfgConnect(String ipWeb, String ipTest, String portConnection) {

        ipWebService = ipWeb;
        ipShowTest = ipTest;
        portConecction = portConnection;

    }

    public static String getIpWebService() {
        return ipWebService;
    }

    public static void setIpWebService(String ipWebService) {
        ConfgConnect.ipWebService = ipWebService;
    }

    public static String getIpShowTest() {
        return ipShowTest;
    }

    public static void setIpShowTest(String ipShowTest) {
        ConfgConnect.ipShowTest = ipShowTest;
    }

    public static String getPortConecction() {
        return portConecction;
    }

    public static void setPortConecction(String portConecction) {
        ConfgConnect.portConecction = portConecction;
    }

}
