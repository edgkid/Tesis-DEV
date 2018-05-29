package tegdev.optotypes;

/**
 * Created by Edgar on 27/05/2018.
 */

public class ServerPath {

    private String ipAdddress;
    private String pathAddress;
    private String http;

    public ServerPath() {
        this.ipAdddress = ConfgConnect.getIpWebService();
        this.pathAddress = "/WSOptotype/";
        this.http = "http://";
    }

    public ServerPath(String ipAdddress, String pathAddress, String http) {
        this.ipAdddress = ipAdddress;
        this.pathAddress = pathAddress;
        this.http = http;
    }

    public String getIpAdddress() {
        return ipAdddress;
    }

    public void setIpAdddress(String ipAdddress) {
        this.ipAdddress = ipAdddress;
    }

    public String getPathAddress() {
        return pathAddress;
    }

    public void setPathAddress(String pathAddress) {
        this.pathAddress = pathAddress;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String hhtp) {
        this.http = hhtp;
    }


}
